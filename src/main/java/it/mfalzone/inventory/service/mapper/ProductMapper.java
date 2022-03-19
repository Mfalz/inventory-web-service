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
		pe.setWeight(mapWeight(p.getWeight()));

		return pe;
	}

	static ProductEntity.Weight mapWeight(Weight w) {
		ProductEntity.Weight weight = new ProductEntity.Weight();
		weight.setAmount(w.getAmount());
		weight.setMeasurement(mapWeightMeasurement(w.getMeasurement()));

		return weight;
	}

	static ProductEntity.Weight.WeightMeasurement mapWeightMeasurement(WeightMeasurement w) {
		if (w.name().equals(ProductEntity.Weight.WeightMeasurement.GRAMS.name())) {
			return ProductEntity.Weight.WeightMeasurement.GRAMS;
		}
		if (w.name().equals(ProductEntity.Weight.WeightMeasurement.LITERS.name())) {
			return ProductEntity.Weight.WeightMeasurement.LITERS;
		}
		return null;
	}
}
