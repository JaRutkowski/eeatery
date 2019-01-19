package com.javafee.hibernate.dto.eeatery;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dish")
@SequenceGenerator(name = "seq_dish", sequenceName = "seq_dish", allocationSize = 1)
public class Dish implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dish")
	@Column(name = "id_dish", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idDish;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String name;

	@Column(name = "price", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 2)
	private BigDecimal price;

	@Column(name = "max_invoice_number", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 2)
	private Integer maxInvoiceNumber;

	@Column(name = "vegetarian", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean vegetarian = false;

	@Override
	public Object clone() {
		Object result = null;
		try {
			result = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String toString() {
		return "[" + idDish + " " + name + " " + price + " " + maxInvoiceNumber + " " + vegetarian + "]";
	}

}
