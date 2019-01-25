package com.kvn.ddd.orderdomain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kvn.ddd.orderdomain.aggregates.root.OrderRootAggregate;
import com.kvn.ddd.orderdomain.aggregates.root.PriceRootAggregator;
import com.kvn.ddd.orderdomain.aggregates.root.ProductRootAggregator;
import com.kvn.ddd.orderdomain.repositories.OrderRepository;
import com.kvn.ddd.orderdomain.repositories.PriceRepository;
import com.kvn.ddd.orderdomain.repositories.ProductRepository;
import com.kvn.ddd.orderdomain.vo.OrderItem;
import com.kvn.ddd.orderdomain.vo.OrderItemPrice;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PriceRepository priceRepository;

	public void addItemToOrder(long orderId, long productId, long priceId,long quantity) {
		OrderRootAggregate orderRootAggregate = orderRepository.findById(orderId).get();
		ProductRootAggregator productRootAggregator = productRepository.findById(productId).get();
		PriceRootAggregator priceRootAggregator = priceRepository.findById(priceId).get();
		
		OrderItemPrice orderItemPrice = new OrderItemPrice();
		orderItemPrice.setPriceId(priceRootAggregator.getId());// get from price root aggregate
		orderItemPrice.setDiscount(10.00);
		
		OrderItem orderItem = new OrderItem();
		orderItem.addOrderItemPrice(orderItemPrice);
		
		orderItem.addProduct(productRootAggregator.getId());
	   	
		orderRootAggregate.addOrderItems(orderItem);
		
		orderRepository.save(orderRootAggregate);
		
		
		
	}
}
