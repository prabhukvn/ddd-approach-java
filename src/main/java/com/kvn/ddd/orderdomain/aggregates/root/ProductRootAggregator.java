/**
 * 
 */
package com.kvn.ddd.orderdomain.aggregates.root;

import javax.persistence.Entity;

import lombok.Data;

/**
 * @author prabhu
 *
 */
@Entity
@Data
public class ProductRootAggregator {
	
	private long id;

}
