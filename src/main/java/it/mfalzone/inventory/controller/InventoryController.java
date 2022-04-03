package it.mfalzone.inventory.controller;

import it.mfalzone.inventory.controller.mapping.ProductRestMapping;
import it.mfalzone.inventory.controller.model.InventoryRest;
import it.mfalzone.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static it.mfalzone.inventory.controller.ControllerUtils.API_PREFIX;

@RestController
@Validated
@RequestMapping(API_PREFIX + "/inventory")
public class InventoryController {

	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@PostMapping
	public void uploadInventory(@NonNull @RequestParam("file") MultipartFile file) {
		inventoryService.uploadInventory(file);
	}

	@GetMapping
	public ResponseEntity<InventoryRest> downloadInventory() {
		return ResponseEntity.ok(ProductRestMapping.map(inventoryService.getAllProducts()));
	}

}
