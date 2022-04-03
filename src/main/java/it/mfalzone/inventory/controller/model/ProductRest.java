package it.mfalzone.inventory.controller.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductRest {
	String description;
	Integer quantity;
	String weight;
	LocalDateTime expiry;
	String location;
	String category;
}
