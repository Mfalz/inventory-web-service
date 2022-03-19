package it.mfalzone.inventory.service;

import it.mfalzone.inventory.service.inputreader.InventoryExcelReaderImpl;
import it.mfalzone.inventory.persistence.InventoryRepository;
import it.mfalzone.inventory.persistence.entity.ProductEntity;
import it.mfalzone.inventory.service.mapper.ProductMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	public InventoryService(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}

	public void uploadInventory(MultipartFile multipartFile) {
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("inventory_", "");
			tmpFile.deleteOnExit();
			multipartFile.transferTo(tmpFile);
			List<ProductEntity> products = new InventoryExcelReaderImpl(tmpFile)
					.readListProducts()
					.stream()
					.map(ProductMapper::mapToEntity)
					.collect(Collectors.toList());
			inventoryRepository.saveAll(products);
			tmpFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
