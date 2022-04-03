package it.mfalzone.inventory.service.inputreader;

import com.sun.istack.NotNull;
import it.mfalzone.inventory.domain.model.Product;
import it.mfalzone.inventory.domain.model.Weight;
import it.mfalzone.inventory.service.exception.BusinessErrorCode;
import it.mfalzone.inventory.service.exception.InvalidInputBusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class InventoryExcelReaderImpl extends InventoryReader {

	private final Workbook workbook;
	private final Map<ExcelHeaders, Integer> columnMapping;
	private final Logger LOG = LogManager.getLogger(InventoryExcelReaderImpl.class);

	public InventoryExcelReaderImpl(File file) throws IOException {
		super(file);
		this.workbook = new XSSFWorkbook(fileInputStream);
		this.columnMapping = new HashMap<>();
	}

	@Override
	protected void parseInputFileStep() throws InvalidInputBusinessException {
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() == 0) {
				parseHeader(row);
			} else {
				try {
					Cell description = row.getCell(columnMapping.get(ExcelHeaders.DESCRIPTION));
					Cell quantity = row.getCell(columnMapping.get(ExcelHeaders.QUANTITY));
					Cell weight = row.getCell(columnMapping.get(ExcelHeaders.WEIGHT));
					Cell expiry = row.getCell(columnMapping.get(ExcelHeaders.EXPIRY));
					Cell category = row.getCell(columnMapping.get(ExcelHeaders.CATEGORY));
					Cell location = row.getCell(columnMapping.get(ExcelHeaders.LOCATION));

					if (description != null && quantity != null && weight != null && expiry != null) {
						importedProducts.add(parseLine(description, quantity, weight, expiry, category, location));
					} else {
						LOG.warn("[{}] Skip reading line, at least one of the required column is null", filename);
					}

				} catch (Exception e) {
					addValidationError(row.getRowNum(), e.getMessage());
				}
			}
		}
	}

	private Product parseLine(@NotNull Cell description,
							  @NotNull Cell quantity,
							  @NotNull Cell weight,
							  @NotNull Cell expiry,
							  Cell category,
							  Cell location) throws InvalidInputBusinessException {
		Product parsedProduct = Product.builder()
									   .description(description.getStringCellValue())
									   .quantity(Double.valueOf(quantity.getNumericCellValue()).intValue())
									   .weight(Weight.of(weight.getStringCellValue()))
									   .expiry(expiry.getLocalDateTimeCellValue())
									   .category(category != null ? category.getStringCellValue() : "")
									   .location(location != null ? location.getStringCellValue() : "")
									   .build();

		if (!Product.isValid(parsedProduct)) {
			throw new InvalidInputBusinessException(BusinessErrorCode.PRODUCT_IS_NOT_VALID, String.format("Parsed product isn't valid: %s ", parsedProduct));
		}
		return parsedProduct;
	}

	private void parseHeader(Row r) throws InvalidInputBusinessException {
		int columnNum = 0;
		Cell currentCell = r.getCell(columnNum);
		while (currentCell != null && currentCell.getCellType() == CellType.STRING) {
			String cellValue = r.getCell(columnNum).getStringCellValue();

			if (StringUtils.isNotBlank(cellValue)) {
				LOG.info("[{}] Parsing header '{}' at column num {}", filename, cellValue, columnNum);
				try {
					ExcelHeaders parsedHeader = ExcelHeaders.valueOf(cellValue);
					addHeaderMapping(parsedHeader, columnNum);
				} catch (IllegalArgumentException e) {
					// even though a header is invalid, we don't stop the execution, we log it though
					LOG.error("[{}] Found an invalid header '{}' at column '{}'", filename, cellValue, columnNum);
				}
			}

			currentCell = r.getCell(++columnNum);
		}

		// Ensure the required headers are present
		List<ExcelHeaders> requiredHeaders = ExcelHeaders.requiredHeaders();
		List<ExcelHeaders> headersFromFile = this.columnMapping.keySet().stream().filter(ExcelHeaders::isRequired).toList();
		if (!headersFromFile.containsAll(requiredHeaders)) {
			throw new InvalidInputBusinessException(BusinessErrorCode.INPUT_EXCEL_WRONG_HEADER, String.format("Provided file doesn't contain required headers: [%s] instead of [%s]", headersFromFile, requiredHeaders));
		}
	}

	private void addHeaderMapping(@NotNull ExcelHeaders headerName, Integer columnNumber) throws InvalidInputBusinessException {
		if (this.columnMapping.containsKey(headerName)) {
			throw new InvalidInputBusinessException(BusinessErrorCode.INPUT_EXCEL_WRONG_HEADER, "Provided file contains multiple occurrences of the same header");
		}
		this.columnMapping.put(headerName, columnNumber);
	}

	private enum ExcelHeaders {
		DESCRIPTION(true), QUANTITY(true), WEIGHT(true), EXPIRY(true), CATEGORY(false), LOCATION(false);

		private final Boolean required;

		ExcelHeaders(Boolean required) {
			this.required = required;
		}

		public boolean isRequired() {
			return this.required;
		}

		public static List<ExcelHeaders> requiredHeaders() {
			return Arrays.stream(ExcelHeaders.values()).filter(ExcelHeaders::isRequired).collect(Collectors.toList());
		}
	}

}
