package it.mfalzone.inventory.controller;

import it.mfalzone.inventory.controller.errorhandling.model.ApiError;
import it.mfalzone.inventory.service.exception.BusinessErrorCode;
import it.mfalzone.inventory.persistence.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class InventoryControllerIntegrationTest extends ControllerIT {

	@Autowired
	private InventoryRepository inventoryRepository;

	@BeforeEach
	void cleanUp() {
		inventoryRepository.deleteAll();
	}

	@Test
	void uploadExcelFile_productsAreImported() {
		uploadFileRequest("input_excel/input_products.xlsx").expectStatus().isOk();
		assertThat(inventoryRepository.findAll()).hasSize(16);
	}

	@Test
	void uploadExcelFile_oneLineIsInvalid_noProductsAreStored() {
		uploadFileRequest("input_excel/only_oneline_is_invalid.xlsx").expectStatus().isBadRequest();
		assertThat(inventoryRepository.findAll()).isEmpty();
	}

	@Test
	void uploadExcelFile_businessExceptionOccurs_responseIsReturned() {
		var error = uploadFileRequest("input_excel/wrong_headers.xlsx").expectStatus()
																	   .isBadRequest()
																	   .expectBody(ApiError.class)
																	   .returnResult().getResponseBody();
		assertThat(error).isNotNull();
		assertThat(error.getTimestamp()).isNotNull();
		assertThat(error.getMessage()).startsWith("Provided file doesn't contain required headers");
		assertThat(error.getErrorCode()).isEqualTo(BusinessErrorCode.INPUT_EXCEL_WRONG_HEADER.name());
		assertThat(error.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	private WebTestClient.ResponseSpec uploadFileRequest(String filename) {
		var resource = loadFileInClasspath(filename);

		return webTestClient.post()
							.uri("/inventory")
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.body(BodyInserters.fromMultipartData("file", resource))
							.exchange();
	}

}
