package it.mfalzone.inventory.persistence;

import it.mfalzone.inventory.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<ProductEntity, Long> {

//	List<ProductEntity> find

}
