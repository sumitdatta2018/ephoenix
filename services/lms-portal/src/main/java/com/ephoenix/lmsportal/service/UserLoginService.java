package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.ephoenix.lmsportal.client.KeycloakInvoker;
import com.ephoenix.lmsportal.dto.ForgotPasswordTO;
import com.ephoenix.lmsportal.dto.KeycloakTokenDto;
import com.ephoenix.lmsportal.dto.KeycloakTokenRequestDto;
import com.ephoenix.lmsportal.dto.MenuTo;
import com.ephoenix.lmsportal.dto.OtpTo;
import com.ephoenix.lmsportal.dto.RoleTo;
import com.ephoenix.lmsportal.dto.UserLoginTO;
import com.ephoenix.lmsportal.dto.UserTO;
import com.ephoenix.lmsportal.entities.MenuMaster;
import com.ephoenix.lmsportal.entities.RoleMaster;
import com.ephoenix.lmsportal.entities.RoleMenuMap;
import com.ephoenix.lmsportal.entities.RoleUserMap;
import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.generic.code.MessageCode;
import com.ephoenix.lmsportal.repository.MenuMasterRepository;
import com.ephoenix.lmsportal.repository.RoleMasterRepository;
import com.ephoenix.lmsportal.repository.RoleMenuMapRepository;
import com.ephoenix.lmsportal.repository.RoleUserMapRepository;
import com.ephoenix.lmsportal.repository.UserMasterRepository;
import com.ephoenix.lmsportal.util.LMSUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserLoginService {

	@Value("${otp.expiration.timeout:1}")
	private Integer timeout;

	@Value("${otp.retry.limit:5}")
	private Integer retryLimit;
	
	@Value("${mail.from:customersupport@ephoenix.org}")
	private String fromMail;

	@Autowired
	private HazelcastInstance hzInstance;

	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RoleUserMapRepository roleUserMapRepository;

	@Autowired
	private RoleMenuMapRepository roleMenuMapRepository;

	@Autowired
	private RoleMasterRepository roleMasterRepository;

	@Autowired
	private MenuMasterRepository menuMasterRepository;

	@Autowired
	private KeycloakInvoker keycloakInvoker;

	@Autowired
	private JavaMailSender emailSender;

	public KeycloakTokenDto gettoken(KeycloakTokenRequestDto keycloakTokenRequestDto) {
		KeycloakTokenDto keycloakTokenDto = null;
		Map<String, String> tokenRequest = new HashMap<>();
		tokenRequest.put("client_id", keycloakTokenRequestDto.getClient_id());
		tokenRequest.put("client_secret", keycloakTokenRequestDto.getClient_secret());
		tokenRequest.put("username", getUserLoginIDfromEmailOrMobile(keycloakTokenRequestDto.getUsername()));
		tokenRequest.put("password", keycloakTokenRequestDto.getPassword());
		tokenRequest.put("grant_type", "password");
		String tokenDetails = null;
		try {
			tokenDetails = this.keycloakInvoker.getToken(tokenRequest);
		} catch (Exception e) {
			if (e instanceof HttpClientErrorException) {
				HttpClientErrorException exp = (HttpClientErrorException) e;
				if (exp.getRawStatusCode() == 401) {
					throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE012.name());
				}
			}

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		keycloakTokenDto = LMSUtil.readValue(tokenDetails, KeycloakTokenDto.class);

		return keycloakTokenDto;
	}

	public UserTO login(UserLoginTO userLoginTO) {
		UserMaster exitingActiveUser = userMasterRepository.findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(
				userLoginTO.getUserLoginId(), userLoginTO.getUserLoginId(), userLoginTO.getUserLoginId(),
				ActiveConstants.ACTIVE.getIsActive());
		if (exitingActiveUser == null) {
			throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE002.name());
		}
		if (!exitingActiveUser.getPassword().equals(userLoginTO.getPassword()))
			throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE012.name());
		UserTO userTO = modelMapper.map(exitingActiveUser, UserTO.class);
		List<RoleUserMap> roleUserMapList = roleUserMapRepository.findByUserIdAndIsActive(exitingActiveUser.getId(),
				ActiveConstants.ACTIVE.getIsActive());
		List<Long> roleIds = roleUserMapList.stream().map(RoleUserMap::getRoleId).collect(Collectors.toList());

		List<RoleMaster> rolesMasterEnts = roleMasterRepository.findByRoleIdInAndIsActive(roleIds,
				ActiveConstants.ACTIVE.getIsActive());
		List<RoleTo> roles = new ArrayList<>();
		rolesMasterEnts.stream().forEach(rolesMasterEnt -> {
			RoleTo roleTo = modelMapper.map(rolesMasterEnt, RoleTo.class);
			roles.add(roleTo);
		});
		List<RoleMenuMap> roleMenuMapList = roleMenuMapRepository.findByRoleIdInAndIsActive(roleIds,
				ActiveConstants.ACTIVE.getIsActive());
		Set<Long> menuIds = roleMenuMapList.stream().map(RoleMenuMap::getMenuId).collect(Collectors.toSet());
		log.info("menuIds---->{}", menuIds);
		List<MenuMaster> menuMasterEntsInt = menuMasterRepository
				.findByIdInAndBrowserOrIsDefaultAndIsActiveOrderBySequenceAsc(menuIds, 1, 1,
						ActiveConstants.ACTIVE.getIsActive());
		List<MenuMaster> menuMasterEnts = menuMasterEntsInt.stream().filter(menuEnt -> {
			if (menuEnt.getName().equalsIgnoreCase("login")) {
				return false;
			}
			return true;
		}).collect(Collectors.toList());
		List<MenuTo> menus = new ArrayList<>();
		menuMasterEnts.stream().forEach(menuMasterEnt -> {
			MenuTo menuTo = modelMapper.map(menuMasterEnt, MenuTo.class);
			menus.add(menuTo);
		});
		userTO.setRoles(roles);
		userTO.setMenus(menus);
		return userTO;
	}
	/*
	*//**
		 * @param userLoginTO
		 * @return
		 */
	/*
	 * @RequestMapping(value = "/password-forgot", method = RequestMethod.POST,
	 * consumes = MediaType.APPLICATION_JSON_VALUE, produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
	 * forgotPassword(@RequestBody UserLoginTO userLoginTO) {
	 * 
	 * userLoginService.forgotPassword(userLoginTO); GenericResponse<String>
	 * response = new GenericResponse<>("OTP generated"); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 *//**
		 * @param passwordTO
		 * @return
		 */
	/*
	 * @RequestMapping(value = "/password-forgot/reset", method =
	 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
	 * forgotPasswordReset(@RequestBody ForgotPasswordTO passwordTO) {
	 * userLoginService.forgotPasswordReset(passwordTO); GenericResponse<String>
	 * response = new GenericResponse<>("Password changed"); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 *//**
		 * @param userLoginTO
		 * @return
		 */

	/*
	 * @RequestMapping(value = "/password-change", method = RequestMethod.POST,
	 * consumes = MediaType.APPLICATION_JSON_VALUE, produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
	 * changePassword(@RequestBody UserLoginTO userLoginTO) {
	 * userLoginService.changePassword(userLoginTO); GenericResponse<String>
	 * response = new GenericResponse<>("Password changed");
	 * 
	 * return new ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 *//**
		 * @param userLoginTO
		 * @return
		 *//*
			 * @RequestMapping(value = "/unblock", method = RequestMethod.POST, consumes =
			 * MediaType.APPLICATION_JSON_VALUE, produces =
			 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
			 * unblockUser(@RequestBody UserLoginTO userLoginTO) {
			 * userLoginService.unblockUser(userLoginTO); GenericResponse<String> response =
			 * new GenericResponse<>("OTP generated"); return new ResponseEntity<>(response,
			 * HttpStatus.OK); }
			 */

	public String logout(String token) {
		// TODO Auto-generated method stub
		keycloakInvoker.revokeToken(token);
		return MessageCode.UM_MESSAGE_CODE002.message();
	}

	@Transactional
	public String resetPassword(ForgotPasswordTO forgotPasswordTO) {
		UserMaster exitingActiveUser = null;
		if (!StringUtils.isEmpty(forgotPasswordTO.getUsername())) {
			exitingActiveUser = userMasterRepository.findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(
					forgotPasswordTO.getUsername(), forgotPasswordTO.getUsername(), forgotPasswordTO.getUsername(),
					ActiveConstants.ACTIVE.getIsActive());
			if (exitingActiveUser == null) {
				throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE002.name());
			}

		} else {
			throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE002.name());
		}
		if (!StringUtils.isEmpty(forgotPasswordTO.getOldPassword())) {
			if (exitingActiveUser.getPassword().equalsIgnoreCase(forgotPasswordTO.getOldPassword())) {
				keycloakInvoker.resetPassword(forgotPasswordTO.getUsername(), forgotPasswordTO.getNewPassword());
				return MessageCode.UM_MESSAGE_CODE003.message();
			}

		} else if (!StringUtils.isEmpty(forgotPasswordTO.getTrackId())) {
			String userId = exitingActiveUser.getId().toString();

			OtpTo otpTo = null;
			IMap<String, String> otpCache = hzInstance.getMap("otpCache");
			if (otpCache.get(userId) != null) {
				try {
					otpTo = new ObjectMapper().readValue(otpCache.get(userId), OtpTo.class);
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (otpTo != null && otpTo.getTrackId().equals(forgotPasswordTO.getTrackId())) {
					String keycloakUserId = keycloakInvoker.fetchKeycloakUserIdByUserLoginId(exitingActiveUser);
					keycloakInvoker.resetPassword(keycloakUserId, forgotPasswordTO.getNewPassword());
					exitingActiveUser.setPassword(forgotPasswordTO.getNewPassword());
					userMasterRepository.save(exitingActiveUser);

					return MessageCode.UM_MESSAGE_CODE003.message();
				} else {
					throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE013.name());
				}

			} else {
				throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE014.name());
			}

		} else {

		}
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserLoginIDfromEmailOrMobile(String userLoginIdReq) {
		String userLoginId = "";
		if (!StringUtils.isEmpty(userLoginIdReq)) {

			String regex = "\\d{10}";
			// Creating a pattern object
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(userLoginIdReq);
			if (matcher.matches()) {
				UserMaster exitingActiveUser = userMasterRepository
						.findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(userLoginIdReq, userLoginIdReq,
								userLoginIdReq, ActiveConstants.ACTIVE.getIsActive());
				if (exitingActiveUser == null) {
					throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE002.name());
				}
				userLoginId = exitingActiveUser.getUserLoginId();

			} else {
				userLoginId = userLoginIdReq;

			}
		}
		return userLoginId;
	}

	public String generateOtp(String userLoginId) {
		UserMaster exitingActiveUser = userMasterRepository.findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(
				userLoginId, userLoginId, userLoginId, ActiveConstants.ACTIVE.getIsActive());

		if (exitingActiveUser == null) {
			throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE002.name());
		}
		String userId = exitingActiveUser.getId().toString();

		OtpTo otpTo = null;
		IMap<String, String> otpCache = hzInstance.getMap("otpCache");
		if (otpCache.get(userId) != null) {
			try {
				otpTo = new ObjectMapper().readValue(otpCache.get(userId), OtpTo.class);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			otpTo = new OtpTo();
			otpTo.setOtp(new String(generatorOTP(4)));
			otpTo.setRetrycount(0);
			otpTo.setRetrycountLimit(retryLimit);
			otpTo.setUserLoginId(userLoginId);
			otpCache.lock(userId);
			otpCache.put(userId, otpTo.toString(), timeout, TimeUnit.MINUTES);
			otpCache.unlock(userId);

		}
		if (exitingActiveUser.getEmail() != null) {

			try {
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setFrom(fromMail);
				message.setTo(exitingActiveUser.getEmail());
				message.setSubject("Ephoenix Password Reset Assistance");
				message.setSentDate(new Date());
				String emailBodyPlain = String.format(
						"Dear %s,Greetings from Ephoenix portal service.We have recieved your request for password reset. Your one time password is: %s",
						exitingActiveUser.getName(), otpTo.getOtp());
				String emailBodyHtml = String.format(
						"<html>Dear %s,<br> Greetings from Ephoenix portal service.We have recieved your request for password reset. Your one time password is: %s</html>",
						exitingActiveUser.getName(), otpTo.getOtp());

				message.setText(emailBodyPlain, emailBodyHtml);
				emailSender.send(mimeMessage);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE015.name());
		}

		return "Your one time password has been sent successfully";

		// TODO Auto-generated method stub

	}

	public ForgotPasswordTO validateOtp(ForgotPasswordTO forgotPasswordTO) {
		String trackId = "";
		if (forgotPasswordTO != null && !StringUtils.isEmpty(forgotPasswordTO.getOtp())
				&& !StringUtils.isEmpty(forgotPasswordTO.getUsername())) {
			IMap<String, String> otpCache = hzInstance.getMap("otpCache");

			String userLoginId = forgotPasswordTO.getUsername();
			UserMaster exitingActiveUser = userMasterRepository.findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(
					userLoginId, userLoginId, userLoginId, ActiveConstants.ACTIVE.getIsActive());

			if (exitingActiveUser == null) {
				throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE002.name());
			}
			String userId = exitingActiveUser.getId().toString();
			try {
				if (otpCache.get(userId) == null) {
					throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE014.name());
				}
				OtpTo otpTo = new ObjectMapper().readValue(otpCache.get(userId), OtpTo.class);

				if (otpTo == null) {
					throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE014.name());
				}
				if (otpTo.getOtp().equals(forgotPasswordTO.getOtp())) {
					trackId = generateRandomAlphanumericString();
					otpTo.setTrackId(trackId);
					otpCache.lock(userId);
					otpCache.put(userId, otpTo.toString());
					otpCache.unlock(userId);

				} else {
					throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE013.name());
				}
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		forgotPasswordTO.setTrackId(trackId);
		return forgotPasswordTO;

	}

	public static char[] generatorOTP(int length) {
		log.info("Your OTP is : ");

		Random obj = new Random();
		char[] otp = new char[length];
		for (int i = 0; i < length; i++) {
			otp[i] = (char) (obj.nextInt(10) + 48);
		}
		return otp;
	}

	public static void main(String[] args) {
		System.out.println(generateRandomAlphanumericString());
	}

	public static String generateRandomAlphanumericString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		System.out.println(generatedString);
		return generatedString;
	}

}
