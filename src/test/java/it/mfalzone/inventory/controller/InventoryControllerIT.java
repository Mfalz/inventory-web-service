package it.mfalzone.inventory.controller;

import it.mfalzone.inventory.controller.errorhandling.model.ApiError;
import it.mfalzone.inventory.controller.model.InventoryRest;
import it.mfalzone.inventory.domain.model.DomainUtils;
import it.mfalzone.inventory.persistence.InventoryRepository;
import it.mfalzone.inventory.service.exception.BusinessErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static it.mfalzone.inventory.controller.configuration.DummyAuthenticationProvider.DUMMY_USER_EMAIL;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class InventoryControllerIT extends ControllerIT {

	private final static String INVENTORY_ENDPOINT = "api/v1/inventory";

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
		assertThat(inventoryRepository.findAllByUserEmail(DUMMY_USER_EMAIL)).hasSize(16);
		assertThat(inventoryRepository.findAllByUserEmail("any")).hasSize(0);
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

	@Test
	void downloadInventory_inventoryIsReturned() {
		inventoryRepository.save(DomainUtils.buildProductEntity());
		var response = downloadInventoryRequest().expectStatus()
												 .isOk()
												 .expectBody(InventoryRest.class)
												 .returnResult()
												 .getResponseBody();

		assertThat(response).isNotNull();
		assertThat(response.getProducts()).hasSize(1);
		assertThat(response.getProducts().get(0).getDescription()).isEqualTo("description");
	}

	@Test
	void downloadInventory_noInventory_notFoundIsReturned() {
		downloadInventoryRequest().expectStatus().isNotFound();
	}

	@Test
	void downloadInventory_anonymousClient_forbiddenIsReturned() {
		webTestClient.get().uri(INVENTORY_ENDPOINT).exchange().expectStatus().isUnauthorized();
	}

	/* Request Builders */

	private WebTestClient.ResponseSpec uploadFileRequest(String filename) {
		var resource = loadFileInClasspath(filename);

		return authWebTestClient.post()
								.uri(INVENTORY_ENDPOINT)
								.contentType(MediaType.MULTIPART_FORM_DATA)
								.body(BodyInserters.fromMultipartData("file", resource))
								.exchange();
	}

	private WebTestClient.ResponseSpec downloadInventoryRequest() {
		return authWebTestClient.get()
								.uri(INVENTORY_ENDPOINT)
								.exchange();
	}
}
