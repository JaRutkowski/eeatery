package com.javafee.hibernate.dto.eeatery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "invoice")
@SequenceGenerator(name = "seq_invoice", sequenceName = "seq_invoice", allocationSize = 1)
public class Invoice implements java.lang.Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_invoice")
	@Column(name = "id_invoice", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idInvoice;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_client", unique = false, nullable = true, insertable = true, updatable = true)
	private Client client = new Client();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "dish_invoice", //
			joinColumns = @JoinColumn(name = "id_dish"), //
			inverseJoinColumns = @JoinColumn(name = "id_invoice"))
	private Set<Dish> dish = new HashSet<Dish>(0);

	@Temporal(TemporalType.DATE)
	@Column(name = "realization_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date realizationDate;

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
		return "[" + idInvoice + " " + dish.toString() + " " + client.toString() + " " + realizationDate + "]";
	}
}
