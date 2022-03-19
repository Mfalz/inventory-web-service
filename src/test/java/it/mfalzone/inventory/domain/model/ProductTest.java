package it.mfalzone.inventory.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductTest {

	final LocalDateTime FUTURE_DATE = LocalDateTime.of(2100, 1, 1, 0, 0, 0, 0);
	final LocalDateTime PAST_DATE = LocalDateTime.of(1900, 1, 1, 0, 0, 0, 0);

	@Test
	void isValid_productIsValid_returnTrue() {
		assertThat(buildProduct().build().isValid()).isTrue();
	}

	@Test
	void isValid_expiryInTheFuture_returnsTrue() {
		assertThat(buildProduct().expiry(PAST_DATE).build().isValid()).isFalse();
		assertThat(buildProduct().expiry(FUTURE_DATE).build().isValid()).isTrue();
	}

	@Test
	void isValid_quantityGreaterThanZero_returnsTrue() {
		assertThat(buildProduct().quantity(-10).build().isValid()).isFalse();
		assertThat(buildProduct().quantity(0).build().isValid()).isFalse();
		assertThat(buildProduct().quantity(1).build().isValid()).isTrue();
	}

	@Test
	void isValid_descriptionIsNotBlank_returnsTrue() {
		assertThat(buildProduct().description("").build().isValid()).isFalse();
		assertThat(buildProduct().description(null).build().isValid()).isFalse();
		assertThat(buildProduct().description("a").build().isValid()).isTrue();
	}

	@Test
	void isValid_categoryBlankOrNull_returnsTrue() {
		assertThat(buildProduct().category("").build().isValid()).isTrue();
		assertThat(buildProduct().category(null).build().isValid()).isTrue();
		assertThat(buildProduct().category("a").build().isValid()).isTrue();
	}

	@Test
	void isValid_locationBlankOrNull_returnsTrue() {
		assertThat(buildProduct().location("").build().isValid()).isTrue();
		assertThat(buildProduct().location(null).build().isValid()).isTrue();
		assertThat(buildProduct().location("a").build().isValid()).isTrue();
	}

	private Product.ProductBuilder buildProduct() {
		return Product.builder().weight(Weight.of("200 g"))
					  .quantity(1)
					  .description("Scatolette di tonno")
					  .expiry(FUTURE_DATE)
					  .category("category")
					  .location("location");
	}

}