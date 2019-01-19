package com.javafee.hibernate.dto.eeatery;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.javafee.hibernate.dto.common.User;

@Entity
@NamedQueries({ @NamedQuery(name = "Worker.checkIfWorkerLoginExist", query = "from Worker where login = :login"),
		@NamedQuery(name = "Worker.checkWithComparingIdIfClientLoginExist", query = "from Worker where login = :login and id != :id") })
@Table(name = "worker")
@PrimaryKeyJoinColumn(name = "id_worker", referencedColumnName = "id_users")
public class Worker extends User implements Cloneable {

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
		return super.toString();
	}

}
