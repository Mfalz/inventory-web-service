package it.mfalzone.inventory.controller;

import it.mfalzone.inventory.service.InventoryService;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

}
