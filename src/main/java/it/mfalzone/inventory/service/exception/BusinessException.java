package it.mfalzone.inventory.service.exception;

import com.sun.istack.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessException extends RuntimeException {
	private final HttpStatus status;
	private final BusinessErrorCode businessErrorCode;
	private final String message;
	private final List<String> validationMessages;

	public BusinessException(HttpStatus status, BusinessErrorCode businessErrorCode, String message) {
		super(message);
		this.status = status;
		this.businessErrorCode = businessErrorCode;
		this.message = message;
		this.validationMessages = new ArrayList<>();
	}

	public static BusinessException of(@NotNull HttpStatus status, @NotNull BusinessErrorCode businessErrorCode, @NotNull String message) {
		return new BusinessException(status, businessErrorCode, message);
	}

	public void addValidationMessages(List<String> messages) {
		this.validationMessages.addAll(messages);
	}
}
