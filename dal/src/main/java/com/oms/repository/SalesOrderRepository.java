
/**
 * SalesOrderRepository is intended to be used only by OrderService.
 * Any usage of this repository outside of OrderService is disallowed.
 */
package com.oms.repository;

import com.oms.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {
}
