package it.mfalzone.inventory.service;

import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderUser;
import it.mfalzone.inventory.domain.model.Product;
import it.mfalzone.inventory.persistence.InventoryRepository;
import it.mfalzone.inventory.persistence.entity.ProductEntity;
import it.mfalzone.inventory.service.exception.BusinessErrorCode;
import it.mfalzone.inventory.service.exception.NotFoundBusinessException;
import it.mfalzone.inventory.service.inputreader.InventoryExcelReaderImpl;
import it.mfalzone.inventory.service.mapper.ProductMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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

	@Transactional
	public void uploadInventory(MultipartFile multipartFile, IdentityProviderUser user) {
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("inventory_", "");
			tmpFile.deleteOnExit();
			multipartFile.transferTo(tmpFile);
			inventoryRepository.deleteAllByUserEmail(user.getEmail());
			List<ProductEntity> products = new InventoryExcelReaderImpl(tmpFile)
					.readListProducts()
					.stream()
					.map(p -> ProductMapper.mapToEntity(p, user))
					.collect(Collectors.toList());
			inventoryRepository.saveAll(products);
			tmpFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Product> getAllProducts(IdentityProviderUser user) {
		List<ProductEntity> productEntities = inventoryRepository.findAllByUserEmail(user.getEmail());
		if (productEntities.isEmpty()) {
			throw new NotFoundBusinessException(BusinessErrorCode.INVENTORY_EMPTY, "Inventory is empty");
		}
		return productEntities.stream().map(ProductMapper::mapToProduct).toList();
	}
}
