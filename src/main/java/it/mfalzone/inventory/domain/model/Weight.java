package it.mfalzone.inventory.domain.model;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Weight {
	private Integer amount;
	private WeightMeasurement measurement;

	public Weight(Integer amount, WeightMeasurement measurement) {
		this.amount = amount;
		this.measurement = measurement;
	}

	public static Weight of(String weight) throws IllegalArgumentException {
		Pattern pattern = Pattern.compile("([0-9]+)\\s*([a-z]+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(weight);
		if (matcher.find()) {
			Integer unit = Integer.parseInt(matcher.group(1));
			WeightMeasurement wm = WeightMeasurement.parse(matcher.group(2));
			Weight parsedWeight = new Weight(unit, wm);

			if (!parsedWeight.isValid()) {
				throw new IllegalArgumentException(String.format("Failed to parse weight '%s'", weight));
			}
			return parsedWeight;
		} else {
			throw new IllegalArgumentException(String.format("Failed to parse weight '%s'", weight));
		}
	}

	private boolean isValid() {
		if (amount <= 0) {
			return false;
		}
		if (measurement == null) {
			return false;
		}
		return true;
	}

}
