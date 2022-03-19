package it.mfalzone.inventory.controller.errorhandling.model;

import com.sun.istack.NotNull;
import it.mfalzone.inventory.service.exception.BusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Model used for representing an internal exception to the external world
 */
@Getter
public class ApiError {

	private final LocalDateTime timestamp;
	private final HttpStatus status;
	private final String errorCode;
	private final String message;
	private final List<String> errorMessages;

	private ApiError(HttpStatus httpStatus, String message, String errorCode, List<String> errorMessages) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.status = httpStatus;
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
	}

	public static ApiError of(@NonNull HttpStatus httpStatus, BusinessException businessException) {
		return new ApiError(httpStatus, businessException.getMessage(), businessException.getBusinessErrorCode().name(), businessException.getValidationMessages());
	}

	public static ApiError of(@NonNull HttpStatus httpStatus, @NonNull String message, @NotNull String errorCode) {
		return new ApiError(httpStatus, message, errorCode, null);
	}
}
