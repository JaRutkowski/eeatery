package com.javafee.hibernate.dto.common;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "login") })
@SequenceGenerator(name = "seq_users", sequenceName = "seq_users", allocationSize = 1)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
	@Column(name = "id_users", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idUsers;

	@Column(name = "login", unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	private String login;

	@Column(name = "e_mail", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String eMail;

	@Column(name = "password", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String password;

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	private String name;

	@Column(name = "surname", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	private String surname;

	@Temporal(TemporalType.DATE)
	@Column(name = "register_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date registerDate;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_address", unique = false, nullable = true, insertable = true, updatable = true)
	private Address address;

	@Column(name = "phone_number", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	private String phoneNumber;

	@Override
	public String toString() {
		return "[" + idUsers + " " + login + " " + eMail + " " + password + " " + name + " " + surname + " "
				+ registerDate + " " + address + " " + phoneNumber + "]";
	}

}
