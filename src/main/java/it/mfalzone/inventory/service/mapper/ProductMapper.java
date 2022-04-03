package it.mfalzone.inventory.service.mapper;

import it.mfalzone.inventory.domain.model.Product;
import it.mfalzone.inventory.domain.model.Weight;
import it.mfalzone.inventory.domain.model.WeightMeasurement;
import it.mfalzone.inventory.persistence.entity.ProductEntity;

import java.sql.Timestamp;

public interface ProductMapper {

	static ProductEntity mapToEntity(Product p) {
		ProductEntity pe = new ProductEntity();
		pe.setDescription(p.getDescription());
		pe.setExpiryDate(Timestamp.valueOf(p.getExpiry()));
		pe.setQuantity(p.getQuantity());
		pe.setWeight(mapToEntityWeight(p.getWeight()));

		return pe;
	}

	static ProductEntity.Weight mapToEntityWeight(Weight w) {
		return new ProductEntity.Weight(w.getAmount(), mapToEntityWeightMeasurement(w.getMeasurement()));
	}

	static Product mapToProduct(ProductEntity entity) {
		return Product.builder()
					  .description(entity.getDescription())
					  .quantity(entity.getQuantity())
					  .weight(mapToWeight(entity.getWeight()))
					  .expiry(entity.getExpiryDate().toLocalDateTime())
					  .build();
	}

	static Weight mapToWeight(ProductEntity.Weight weight) {
		return new Weight(weight.getAmount(), mapToWeightMeasurement(weight.getMeasurement()));
	}

	static ProductEntity.Weight.WeightMeasurement mapToEntityWeightMeasurement(WeightMeasurement w) {
		if (w.name().equals(ProductEntity.Weight.WeightMeasurement.GRAMS.name())) {
			return ProductEntity.Weight.WeightMeasurement.GRAMS;
		}
		if (w.name().equals(ProductEntity.Weight.WeightMeasurement.LITERS.name())) {
			return ProductEntity.Weight.WeightMeasurement.LITERS;
		}
		return null;
	}

	static WeightMeasurement mapToWeightMeasurement(ProductEntity.Weight.WeightMeasurement w) {
		return switch (w.name()) {
			case "LITERS" -> WeightMeasurement.LITERS;
			case "GRAMS" -> WeightMeasurement.GRAMS;
			default -> null;
		};
	}

}
