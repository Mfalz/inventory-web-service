package it.mfalzone.inventory.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Value;

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
	@Getter
	public static class Weight {
		private Integer amount;
		private WeightMeasurement measurement;

		public Weight(){}

		public Weight(int amount, WeightMeasurement measurement) {
			this.amount = amount;
			this.measurement = measurement;
		}

		public enum WeightMeasurement {
			LITERS, GRAMS
		}
	}

}
