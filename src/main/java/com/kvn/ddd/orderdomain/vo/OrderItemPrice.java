package com.kvn.ddd.orderdomain.vo;
/**
 * This is a simple vo.
 */
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class OrderItemPrice {

	private long priceId;
	private double discount;
	
}
