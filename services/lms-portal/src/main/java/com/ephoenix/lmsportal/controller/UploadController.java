package com.ephoenix.lmsportal.controller;

import java.io.InputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.internet.MimeBodyPart;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ephoenix.lmsportal.dto.GenericResponse;
import com.ephoenix.lmsportal.dto.UploadTO;
import com.ephoenix.lmsportal.dto.UserTO;
import com.ephoenix.lmsportal.service.UploadMgmtService;
import com.ephoenix.lmsportal.util.LMSUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RestController
public class UploadController extends RouteBuilder {
	@Value("${upload.base.dir}")
	private String uploadBaseDir;

	@Autowired
	private UploadMgmtService uploadMgmtService;

	@RequestMapping(value = "/api/uploads", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchtAllUploadDocs(
			@RequestParam(value = "uploadTypeIds", required = false) List<Long> uploadTypeIds,
			@RequestParam(value = "userId", required = false) Long userId) {

		List<UploadTO> uploadTOs = uploadMgmtService.fetchtAllUploadDocs(uploadTypeIds, userId);
		GenericResponse<List<UploadTO>> response = new GenericResponse<>(uploadTOs);
		return new ResponseEntity<GenericResponse<List<UploadTO>>>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/api/uploads/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUploadedDoc(
			@PathVariable(value = "id", required = true) Long uploadDocId) {

		String deleteUploadedDocMsg = uploadMgmtService.deleteUploadedDoc(uploadDocId);
		GenericResponse<String> response = new GenericResponse<>(deleteUploadedDocMsg);
		return new ResponseEntity<GenericResponse<String>>(response, HttpStatus.OK);

	}

	@Override
	public void configure() throws Exception {
		restConfiguration().contextPath("/camel")// <-- Important
				.enableCORS(true).corsHeaderProperty("Access-Control-Allow-Origin", "*")
				.corsHeaderProperty("Access-Control-Allow-Headers",
						"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")

				.component("servlet");

		rest("/upload/{uploadType}").post().consumes(MediaType.MULTIPART_FORM_DATA_VALUE).param().name("uploadType")

				.type(RestParamType.path).description("uploadType").dataType("string").endParam().param()
				.name("studyPlanId").type(RestParamType.query).description("studyPlanId").dataType("string").endParam()
				.produces(MediaType.APPLICATION_JSON_VALUE).outType(UploadTO.class).to("direct:upload");

		from("direct:upload").setHeader("Access-Control-Allow-Origin", constant("*")).process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				// TODO Auto-generated method stub
				// log.info("headers---->{}",exchange.getIn().getHeaders());
				// System.out.println();
				// Attachment body = exchange.getIn().getBody(Attachment.class);
				// DataHandler dataHandler = exchange.getIn().getBody(InputStream.class);
				// exchange.getIn().setHeader(Exchange.FILE_NAME, dataHandler.getName());
				// exchange.getIn().setBody(dataHandler.getInputStream());
				InputStream is = exchange.getIn().getBody(InputStream.class);
				MimeBodyPart mimeMessage = new MimeBodyPart(is);
				// mimeMessage.
				
				DataHandler dh = mimeMessage.getDataHandler();
				exchange.getIn().setBody(dh.getInputStream());

				exchange.getIn().setHeader(Exchange.FILE_NAME, dh.getName());
				exchange.setProperty("fileName", dh.getName());
				
				log.info(exchange.getIn().getHeader("uploadType").toString());
				if (exchange.getIn().getHeader("uploadType").toString().equalsIgnoreCase("STUDY_MATERIAL")) {
					exchange.setProperty("upload-dir",
							LMSUtil.checkTrailingSlash(uploadBaseDir)
									+ exchange.getIn().getHeader("uploadType").toString() + "/"
									+ LMSUtil.checkTrailingSlash(exchange.getIn().getHeader("studyPlanId").toString()));
				} 
				else if(exchange.getIn().getHeader("uploadType").toString().equalsIgnoreCase("GALLERY")){
					Tika tika = new Tika();
					String fileFormat = tika.detect(dh.getInputStream()).split("/")[1];
					exchange.setProperty("fileFormat",fileFormat);
					exchange.setProperty("thumbnail-content", LMSUtil.createThumbnail(dh.getInputStream(), 360));
					exchange.setProperty("upload-dir", LMSUtil.checkTrailingSlash(uploadBaseDir)
							+ exchange.getIn().getHeader("uploadType").toString());
				}
				else {
					exchange.setProperty("upload-dir", LMSUtil.checkTrailingSlash(uploadBaseDir)
							+ exchange.getIn().getHeader("uploadType").toString());
				}

			}
		})
				/*
				 * .recipientList(simple("file://${property.upload-dir}"))
				 * .unmarshal().mimeMultipart().split().attachments() .process(new Processor() {
				 * 
				 * @Override public void process(Exchange exchange) throws Exception {
				 * DataHandler dataHandler =
				 * exchange.getIn().getBody(Attachment.class).getDataHandler();
				 * exchange.getIn().setHeader(Exchange.FILE_NAME, dataHandler.getName());
				 * exchange.getIn().setBody(dataHandler.getInputStream());
				 * 
				 * } })
				 */
				.recipientList(simple("file://${property.upload-dir}"))
				.bean(UploadMgmtService.class, "handleUploadContent");
		// .toD("file://C:/RestTest/$simple{upload.dir}");

	}

	/*
	 * @SuppressWarnings("unused") private static Logger log =
	 * LoggerFactory.getLogger(UploadController.class);
	 * 
	 * @Autowired private UserMgmtService userMgmtService;
	 * 
	 * @RequestMapping(value = "/upload/StudyMaterial", method =
	 * RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
	 * uploadStudyMaterial(@RequestBody UploadTO uploadTO) { String upload =
	 * userMgmtService.uploadStudyMaterial(uploadTO); GenericResponse<String>
	 * response = new GenericResponse<>(upload); return new
	 * ResponseEntity<GenericResponse<String>>(response, HttpStatus.OK); }
	 */

}
