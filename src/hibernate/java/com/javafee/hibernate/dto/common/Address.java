package com.javafee.hibernate.dto.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.javafee.hibernate.dto.association.City;

import lombok.Data;

@Data
@Entity
@Table(name = "address")
//@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_address", sequenceName = "seq_address", allocationSize = 1)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
	@Column(name = "id_address", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idAddress;

	@Column(name = "street", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String street;

	@Column(name = "house_number", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 2)
	private Integer houseNumber;

	@Column(name = "postal_code", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String postal_code;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_city", unique = false, nullable = true, insertable = true, updatable = true)
	private City city;

}
