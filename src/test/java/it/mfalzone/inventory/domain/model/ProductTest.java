package it.mfalzone.inventory.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static it.mfalzone.inventory.domain.model.DomainUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductTest {


	@Test
	void isValid_productIsValid_returnTrue() {
		assertThat(Product.isValid(buildProduct().build())).isTrue();
	}

	@Test
	void isValid_expiryInTheFuture_returnsTrue() {
		assertThat(Product.isValid(buildProduct().expiry(PAST_DATE).build())).isFalse();
		assertThat(Product.isValid(buildProduct().expiry(FUTURE_DATE).build())).isTrue();
	}

	@Test
	void isValid_quantityGreaterThanZero_returnsTrue() {
		assertThat(Product.isValid(buildProduct().quantity(-10).build())).isFalse();
		assertThat(Product.isValid(buildProduct().quantity(0).build())).isFalse();
		assertThat(Product.isValid(buildProduct().quantity(1).build())).isTrue();
	}

	@Test
	void isValid_descriptionIsNotBlank_returnsTrue() {
		assertThat(Product.isValid(buildProduct().description("").build())).isFalse();
		assertThat(Product.isValid(buildProduct().description(null).build())).isFalse();
		assertThat(Product.isValid(buildProduct().description("a").build())).isTrue();
	}

	@Test
	void isValid_categoryBlankOrNull_returnsTrue() {
		assertThat(Product.isValid(buildProduct().category("").build())).isTrue();
		assertThat(Product.isValid(buildProduct().category(null).build())).isTrue();
		assertThat(Product.isValid(buildProduct().category("a").build())).isTrue();
	}

	@Test
	void isValid_locationBlankOrNull_returnsTrue() {
		assertThat(Product.isValid(buildProduct().location("").build())).isTrue();
		assertThat(Product.isValid(buildProduct().location(null).build())).isTrue();
		assertThat(Product.isValid(buildProduct().location("a").build())).isTrue();
	}

}