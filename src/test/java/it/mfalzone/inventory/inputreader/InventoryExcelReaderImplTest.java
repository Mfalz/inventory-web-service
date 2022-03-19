package it.mfalzone.inventory.inputreader;

import it.mfalzone.inventory.domain.model.Product;
import it.mfalzone.inventory.service.exception.InvalidInputBusinessException;
import it.mfalzone.inventory.service.inputreader.InventoryExcelReaderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class InventoryExcelReaderImplTest {

	private final File validInputFile;
	private final File invalidInputFile;

	InventoryExcelReaderImplTest() throws IOException {
		this.validInputFile = new ClassPathResource("input_excel/input_products.xlsx", this.getClass().getClassLoader()).getFile();
		this.invalidInputFile = new ClassPathResource("input_excel/wrong_input.xlsx", this.getClass().getClassLoader()).getFile();
	}

	@Test
	void validInputFile_productsAreParsable() throws IOException {
		InventoryExcelReaderImpl inventoryExcelReader = new InventoryExcelReaderImpl(validInputFile);
		List<Product> productList = inventoryExcelReader.readListProducts();
		assertThat(productList).isNotEmpty().hasSize(16);
	}

	@Test
	void invalidInputFile_exceptionIsThrown() throws IOException {
		InventoryExcelReaderImpl inventoryExcelReader = new InventoryExcelReaderImpl(invalidInputFile);
		Assertions.assertThrows(InvalidInputBusinessException.class, () -> inventoryExcelReader.readListProducts());
	}

}