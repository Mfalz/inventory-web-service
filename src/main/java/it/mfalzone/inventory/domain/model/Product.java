package it.mfalzone.inventory.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class Product {
	private final String description;
	private final Integer quantity;
	private final Weight weight;
	private final LocalDateTime expiry;
	private final String location;
	private final String category;

	public static boolean isValid(Product p) {
		if (StringUtils.isBlank(p.getDescription())) {
			return false;
		}
		if (p.getQuantity() <= 0) {
			return false;
		}
		if (p.getWeight() == null) {
			return false;
		}
		if (p.getExpiry().toLocalDate().isBefore(LocalDate.now())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("description: ").append(description).append("\n");
		sb.append("quantity: ").append(quantity).append("\n");
		sb.append("weight: ").append(weight).append("\n");
		sb.append("expiry: ").append(expiry).append("\n");
		sb.append("location: ").append(location).append("\n");
		sb.append("category: ").append(category).append("\n");
		return sb.toString();
	}
}
