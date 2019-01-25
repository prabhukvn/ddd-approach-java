/**
 * 
 */
package com.kvn.ddd.orderdomain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.ddd.orderdomain.aggregates.root.ProductRootAggregator;

/**
 * @author prabhu
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductRootAggregator, Long> {

}
