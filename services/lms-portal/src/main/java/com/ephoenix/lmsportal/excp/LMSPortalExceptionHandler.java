package com.ephoenix.lmsportal.excp;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ephoenix.lmsportal.dto.ErrorResponse;
import com.ephoenix.lmsportal.dto.GenericResponse;
import com.ephoenix.lmsportal.generic.code.ErrorCode;


@ControllerAdvice
public class LMSPortalExceptionHandler extends ResponseEntityExceptionHandler {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(LMSPortalExceptionHandler.class);
	/**
	 * Handle MissingServletRequestParameterException. Triggered when a
	 * 'required' request parameter is missing.
	 *
	 * @param ex
	 *            MissingServletRequestParameterException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ResponseEntity object
	 */
	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = String.format("%s parameter is missing", ex.getParameterName());
		GenericResponse<Object> response = new GenericResponse<Object>();
		response.setError(new ErrorResponse(BAD_REQUEST, error, ErrorCode.UNKNOWN.name(), ex));
		return buildResponseEntity(response);
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
	 * invalid as well.
	 *
	 * @param ex
	 *            HttpMediaTypeNotSupportedException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ResponseEntity object
	 */
	@Override
	public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,	HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		
		GenericResponse<Object> response = new GenericResponse<Object>();
		response.setError(new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2),ErrorCode.UNKNOWN.name(), ex));
		return buildResponseEntity(response);
	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object
	 * fails @Valid validation.
	 *
	 * @param ex
	 *            the MethodArgumentNotValidException that is thrown when @Valid
	 *            validation fails
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ResponseEntity object
	 */
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse apiError = new ErrorResponse(BAD_REQUEST);
		apiError.setMessage(ErrorCode.UNKNOWN.message());
		apiError.setCode(ErrorCode.UNKNOWN.name());
		//apiError.setDebugMessage(ex.getBindingResult().getFieldErrors().parallelStream().map(p -> p.getDefaultMessage()).collect(Collectors.toList()).toString()); 
		GenericResponse<Object> response = new GenericResponse<Object>();
		response.setError(apiError);
		return buildResponseEntity(response);
	}

	/**
	 * Handles EntityNotFoundException. Created to encapsulate errors with more
	 * detail than javax.persistence.EntityNotFoundException.
	 *
	 * @param ex
	 *            the EntityNotFoundException
	 * @return the ResponseEntity object
	 */
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Object> handleEntityNotFound(Exception ex) {
//		GenericResponse<Object> response = new GenericResponse<Object>();
//		ErrorResponse apiError = new ErrorResponse(OK);
//		String message = null;
//		String code = null;
//		if (StringUtils.isEmpty(ex.getMessage()) || ex.getMessage().contains("java") || ex.getMessage().contains("org")) {
//			message = "Error! Something went wrong.";
//			code = ErrorCode.UNKNOWN.name();
//			logger.error("Error code : {}, message : {}", code, ex.getMessage());
//		} else if(ex.getMessage().startsWith("URH")){
//			code = ex.getMessage();
//			message = getErrorMessage(code);
//			this.getResponseVars(code, message, response);
//			logger.error("Error code : {}, message : {}", code, message);
//		} else {
//			message = ex.getMessage();
//			code = ErrorCode.UNKNOWN.name();
//			logger.error("Error code : {}, message : {}", code, message);
//		}
//		apiError.setMessage(message);
//		String debug = stackTraceToString(ex);
//		// apiError.setDebugMessage(debug);
//		apiError.setCode(code);
//		response.setError(apiError);
//		
//		logger.error("Stactrace : {}", debug);
//		return buildResponseEntity(response);
//	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleEntityNotFound(Exception ex) {
		GenericResponse<Object> response = new GenericResponse<Object>();
		ErrorResponse apiError = new ErrorResponse(OK);
		String message = null;
		String code = null;
		logger.info("lo", ex.getMessage());
		try {
			message = ErrorCode.valueOf(ex.getMessage()).message();
			code = ex.getMessage();
		} catch (Exception e) {
			code = ErrorCode.UNKNOWN.name();
			if (StringUtils.isEmpty(ex.getMessage()) || ex.getMessage().contains("java") || ex.getMessage().contains("org")) {
				message = "Error! Something went wrong.";
			} else {
				message = ex.getMessage();
			}
		}

		apiError.setMessage(message);
		String debug = stackTraceToString(ex);
		apiError.setDebugMessage(debug);
		apiError.setCode(code);
		response.setError(apiError);

		logger.error("Stactrace : {}", debug);
		return buildResponseEntity(response);
	}

	/**
	 * Gets the error message.
	 *
	 * @param code the code
	 * @return the error message
	 */
	public static String getErrorMessage(String code) {
		String message = "";
		if (StringUtils.isEmpty(code)) {
			message = code;
		} else {
			try {
				message = ErrorCode.valueOf(code).message();
			} catch (Exception e) {
				logger.error("Error getErrorMessage() :", e);
			}
		}
		return message;
	}

	
	/**
	 * Gets the response vars.
	 *
	 * @param code the code
	 * @param message the message
	 * @param response the response
	 * @return the response vars
	 */
	private void getResponseVars(String code, String message, GenericResponse<?> response) {
		String varKey = null;
		String regex = null;
		switch (code) {
		case "SCE040":
			varKey = "__min__";
			regex = "One payment is already in progress. Please try after ([0-9]+) minutes.";
			this.getVars(regex, varKey, message, response);
			break;
		case "SCE090":
			varKey = "__pwd__";
			regex = "Password matches with \\(last ([0-9]+)\\) password history. Please provide a different password";
			this.getVars(regex, varKey, message, response);
			break;
		}
	}

	/**
	 * Gets the vars.
	 *
	 * @param regex the regex
	 * @param varKey the var key
	 * @param message the message
	 * @param response the response
	 * @return the vars
	 */
	private void getVars(String regex, String varKey, String message, GenericResponse<?> response) {
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(message);
		if (m.find()) {
			String min = m.group(1);
			Map<Object, Object> vars = new HashMap<>(1);
			vars.put(varKey, min);
			response.setVars(vars);
		}
	}

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex
	 *            HttpMessageNotReadableException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ResponseEntity object
	 */
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		GenericResponse<Object> response = new GenericResponse<Object>();
		response.setError(new ErrorResponse(BAD_REQUEST, ErrorCode.UNKNOWN.message(), ErrorCode.UNKNOWN.name(), ex));
		return buildResponseEntity(response);
	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex
	 *            HttpMessageNotWritableException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ResponseEntity object
	 */
	@Override
	public ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		GenericResponse<Object> response = new GenericResponse<Object>();
		response.setError(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKNOWN.message(), ErrorCode.UNKNOWN.name(), ex));
		return buildResponseEntity(response);
	}

	/**
	 * Builds the response entity.
	 *
	 * @param response the response
	 * @return the response entity
	 */
	private ResponseEntity<Object> buildResponseEntity(final GenericResponse<Object> response) {
		logger.error(response.getError().toString()); 
		return new ResponseEntity<>(response, response.getError().getStatus());
	}
	
	/**
	 * Stack trace to string.
	 *
	 * @param exception the exception
	 * @return the string
	 */
	public String stackTraceToString(final Throwable exception) {
	    StringBuilder stringBuilder = new StringBuilder();
	    Arrays.stream(exception.getStackTrace()).forEach(t->stringBuilder.append(t).append("\n")); 
	    return stringBuilder.toString();
	}

}
