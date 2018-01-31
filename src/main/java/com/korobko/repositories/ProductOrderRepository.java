package com.korobko.repositories;

import com.korobko.models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Vova Korobko
 */
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    /**
     * Should query the entity manually due to {@code JpaRepository} issue
     *
     * @param id the {@code ProductOrder} id
     */
    @Override
    @Modifying
    @Query("delete from ProductOrder o where o.orderId = ?1")
    void delete(Long id);

    /**
     * Should query the entity manually due to {@code JpaRepository} issue
     */
    @Override
    @Modifying
    @Query("delete from ProductOrder")
    void deleteAll();
}
