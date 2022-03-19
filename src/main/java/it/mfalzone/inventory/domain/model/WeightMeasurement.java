package it.mfalzone.inventory.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum WeightMeasurement {
	LITERS("L", "LITERS", "LITER", "LITRI"),
	GRAMS("G", "GRAMS", "GRAM", "GRAMMI");

	public List<String> readableNames;

	WeightMeasurement(String... values) {
		this.readableNames = List.of(values);
	}

	public static WeightMeasurement parse(String s) {
		return Arrays.stream(WeightMeasurement.values())
					 .filter(v -> v.readableNames.contains(s.toUpperCase(Locale.ROOT)))
					 .findFirst()
					 .orElseThrow(() -> new IllegalArgumentException(String.format("Can't parse WeightMeasurement '%s'", s)));
	}

}