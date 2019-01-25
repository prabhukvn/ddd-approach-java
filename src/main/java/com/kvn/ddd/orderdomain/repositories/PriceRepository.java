package com.kvn.ddd.orderdomain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.ddd.orderdomain.aggregates.root.PriceRootAggregator;

@Repository
public interface PriceRepository extends JpaRepository<PriceRootAggregator, Long> {

}
