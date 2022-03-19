package it.mfalzone.inventory.domain.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeightTest {

	@ParameterizedTest
	@ValueSource(strings = {"2g", "2 g", "2 gRaMs", "2 gram", "2 grammi", "100 grams"})
	void createWeightGrams_supportedMeasurements_returnsTrue(String measurement) {
		assertThat(Weight.of(measurement)).isNotNull();
	}

	@ParameterizedTest
	@ValueSource(strings = {"2l", "2 l", "2 lItErS", "2 liter", "2 litri", "100 liters"})
	void createWeightLiters_supportedMeasurements_returnsTrue(String measurement) {
		assertThat(Weight.of(measurement)).isNotNull();
	}

	@ParameterizedTest
	@ValueSource(strings = {"g", "L", "2 graMMs"})
	void createWeight_unsupportedMeasurements_returnsFalse(String measurement) {
		assertThrows(IllegalArgumentException.class, () -> Weight.of(measurement));
	}
}