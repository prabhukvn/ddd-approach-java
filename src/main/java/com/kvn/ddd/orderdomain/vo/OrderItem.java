package com.kvn.ddd.orderdomain.vo;
/**
 * This is a simple vo
 */
import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderItem {

	private Timestamp createdDate;
	private Timestamp updatedDate;
	private Long productId;
	private String skuId;
	private String quantity;
	private OrderItemPrice orderItemPrice;
	
	public void addOrderItemPrice(OrderItemPrice orderItemPrice) {
		this.orderItemPrice=orderItemPrice;
	}

	public void addProduct(long productId) {

		this.productId=productId;
	}
	

}
