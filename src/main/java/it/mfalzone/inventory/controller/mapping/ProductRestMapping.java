package it.mfalzone.inventory.controller.mapping;

import it.mfalzone.inventory.controller.model.InventoryRest;
import it.mfalzone.inventory.controller.model.ProductRest;
import it.mfalzone.inventory.domain.model.Product;

import java.util.List;

public class ProductRestMapping {

	public static InventoryRest map(List<Product> products) {
		List<ProductRest> productRests = products.stream().map(ProductRestMapping::map).toList();
		return new InventoryRest(productRests);
	}

	public static ProductRest map(Product product) {
		return new ProductRest(product.getDescription(),
							   product.getQuantity(),
							   product.getWeight().getAmount() + " " + product.getWeight().getMeasurement().toString(),
							   product.getExpiry(),
							   product.getLocation(),
							   product.getCategory());
	}

}
