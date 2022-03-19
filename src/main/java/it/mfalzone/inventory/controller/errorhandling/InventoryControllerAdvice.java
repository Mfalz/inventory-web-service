package it.mfalzone.inventory.controller.errorhandling;

import it.mfalzone.inventory.service.exception.BusinessException;
import it.mfalzone.inventory.controller.errorhandling.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InventoryControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {BusinessException.class})
	protected ResponseEntity<ApiError> handleBusinessExceptions(BusinessException ex) {
		var error = ApiError.of(HttpStatus.BAD_REQUEST, ex);

		return buildApiResponse(HttpStatus.BAD_REQUEST, error);
	}

	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<ApiError> handleGenericException(Exception ex) {
		var error = ApiError.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name());

		return buildApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, error);
	}

	private ResponseEntity<ApiError> buildApiResponse(HttpStatus status, ApiError body) {
		return ResponseEntity.status(status)
							 .contentType(MediaType.APPLICATION_JSON)
							 .body(body);
	}

}
