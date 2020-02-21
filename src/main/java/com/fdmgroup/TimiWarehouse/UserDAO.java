package com.fdmgroup.TimiWarehouse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class UserDAO {
	
	User user;

	public UserDAO() {
		super();
	}

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
	EntityManager entityManager = emf.createEntityManager();
	EntityTransaction entityTransaction = entityManager.getTransaction();

	public void addUser(User user) {
		// TODO Auto-generated method stub
		User getName = entityManager.find(User.class, user.getUsername());
		if (getName == null) {
			entityTransaction.begin();
			entityManager.persist(user);
			entityTransaction.commit();
		}

	}

	public User getUser(String username) {
		// TODO Auto-generated method stub
		user = entityManager.find(User.class, username);
		if (user!=null) {
		return user;
		}
		else {
			return null;
		
		}
	}

	public void removeUser(String username) {
		// TODO Auto-generated method stub
		User getName = entityManager.find(User.class, username);
		if (getName != null) {
			entityTransaction.begin();
			entityManager.remove(getName);
			entityTransaction.commit();
		}

	}

	public void updateUser(User user) {

		User getName = entityManager.find(User.class, user.getUsername());
		if (getName != null) {
			entityTransaction.begin();
			entityManager.merge(user);
			entityTransaction.commit();
		}

	}

	public List<User> listUsers() {
		String jpql = "SELECT t FROM users t";
		TypedQuery<User> userQuery = entityManager.createQuery(jpql, User.class);
		List<User> users = userQuery.getResultList();
		return users;
	}

}
