
package com.oms.repository;

import com.oms.entity.Inventory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    @Query("SELECT i FROM Inventory i WHERE i.storeId = :storeId")
    List<Inventory> findByStoreId(@Param("storeId") String storeId);
}