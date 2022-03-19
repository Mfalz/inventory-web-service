package it.mfalzone.inventory.persistence;

import it.mfalzone.inventory.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<ProductEntity, Long> {

}
