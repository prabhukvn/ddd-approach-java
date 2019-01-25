package com.kvn.ddd.orderdomain.aggregates.root;

/**
 * This is the root aggregate order domain.
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.kvn.ddd.orderdomain.exceptions.BusinessException;
import com.kvn.ddd.orderdomain.vo.OrderItem;

import lombok.Data;

@Entity
@Data
public class OrderRootAggregate {

	private Long id;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private List<OrderItem> orderItems = new ArrayList<>();

	public void addOrderItems(OrderItem orderItem) {

		if (orderItems.size() > 100) {
			throw new BusinessException("More than the expected Orders");
		} else {
			orderItems.add(orderItem);
		}

	}

}
