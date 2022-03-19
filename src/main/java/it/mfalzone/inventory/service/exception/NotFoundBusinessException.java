package it.mfalzone.inventory.service.exception;

import org.springframework.http.HttpStatus;

public class NotFoundBusinessException extends BusinessException {
	public NotFoundBusinessException(BusinessErrorCode businessErrorCode, String message) {
		super(HttpStatus.BAD_REQUEST, businessErrorCode, message);
	}
}