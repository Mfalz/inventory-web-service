package it.mfalzone.inventory.service.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputBusinessException extends BusinessException {

	public InvalidInputBusinessException(BusinessErrorCode businessErrorCode, String message) {
		super(HttpStatus.BAD_REQUEST, businessErrorCode, message);
	}
}
