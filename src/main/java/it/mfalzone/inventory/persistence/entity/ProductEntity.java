package it.mfalzone.inventory.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PRODUCTS")
@Data
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String description;

	private Integer quantity;

	private Weight weight;

	private Timestamp expiryDate;

	@Embeddable
	@Data
	public static class Weight {
		private Integer amount;
		private WeightMeasurement measurement;

		public static enum WeightMeasurement {
			LITERS, GRAMS
		}
	}

}
