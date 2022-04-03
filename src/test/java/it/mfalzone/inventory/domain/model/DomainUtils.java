package it.mfalzone.inventory.domain.model;

import it.mfalzone.inventory.persistence.entity.ProductEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DomainUtils {

	public final static LocalDateTime FUTURE_DATE = LocalDateTime.of(2100, 1, 1, 0, 0, 0, 0);
	public final static LocalDateTime PAST_DATE = LocalDateTime.of(1900, 1, 1, 0, 0, 0, 0);

	public static Product.ProductBuilder buildProduct() {
		return Product.builder().weight(Weight.of("200 g"))
					  .quantity(1)
					  .description("Scatolette di tonno")
					  .expiry(FUTURE_DATE)
					  .category("category")
					  .location("location");
	}

	public static ProductEntity buildProductEntity() {
		ProductEntity pe = new ProductEntity();
		pe.setDescription("description");
		pe.setQuantity(2);
		pe.setWeight(new ProductEntity.Weight(200, ProductEntity.Weight.WeightMeasurement.GRAMS));
		pe.setExpiryDate(Timestamp.valueOf(FUTURE_DATE));

		return pe;
	}

}
