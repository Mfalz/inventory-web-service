package it.mfalzone.inventory.service.inputreader;

import it.mfalzone.inventory.domain.model.Product;
import it.mfalzone.inventory.service.exception.BusinessErrorCode;
import it.mfalzone.inventory.service.exception.InvalidInputBusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class InventoryReader {

	protected FileInputStream fileInputStream;
	protected File file;
	protected String filename;
	protected List<String> validationErrors = new ArrayList<>();
	protected List<Product> importedProducts;
	protected Logger LOG = LogManager.getLogger(InventoryReader.class);

	public InventoryReader(File file) throws FileNotFoundException {
		this.file = file;
		this.filename = file.getName();
		this.fileInputStream = new FileInputStream(file);
		this.importedProducts = new ArrayList<>();
	}

	/**
	 * Parse all the lines from the input file, checks the validity
	 *
	 * @return a list of parsed products
	 * @throws IOException, InvalidInputBusinessException if any parsing error occurs
	 */
	public List<Product> readListProducts() throws IOException, InvalidInputBusinessException {
		parseInputFileStep();
		checkValidationErrorsStep();
		return importedProducts;
	}

	protected abstract void parseInputFileStep() throws InvalidInputBusinessException;

	protected void checkValidationErrorsStep() throws InvalidInputBusinessException {
		if (!validationErrors.isEmpty()) {
			var validationException = new InvalidInputBusinessException(BusinessErrorCode.INPUT_EXCEL_INVALID_WEIGHT_FORMAT,
																		String.format("Failed to parse all the lines from file '%s', please check your input file", file.toString()));
			validationException.addValidationMessages(validationErrors);
			LOG.warn("Validation errors while parsing input file: " + validationException.getValidationMessages());
			throw validationException;
		}
	}

	protected void addValidationError(Integer lineNumber, String validationMessage) {
		LOG.warn("[{}] Validation error at line {}: {}", filename, lineNumber, validationMessage);
		validationErrors.add(String.format("[at line %d]: %s", lineNumber, validationMessage));
	}

}
