package com.kvn.ddd.orderdomain.aggregates.root;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class PriceRootAggregator {
	private long id;

	private double salePrice;
	private double discount;
	private double listPrice;
	private double basePrice;

}
