package com.fdmgroup.TimiWarehouse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ItemDAO {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
	EntityManager entityManager = emf.createEntityManager();
	EntityTransaction entityTransaction = entityManager.getTransaction();

	public ItemDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addItem(Item item) {
		Item getName = entityManager.find(Item.class, item.getItemId());
		if (getName == null) {
			entityTransaction.begin();
			entityManager.persist(item);
			entityTransaction.commit();
		}

	}

	public Item getItem(int item) {
		Item item1 = entityManager.find(Item.class, item);
		return item1;

	}

	public void updateItem(Item item) {
		Item getName = entityManager.find(Item.class, item.getItemId());
		if (getName != null) {
			entityTransaction.begin();
			entityManager.merge(item);
			entityTransaction.commit();

		}
	}

	public void removeItem(int item) {
		Item getName = entityManager.find(Item.class, item);
		if (getName != null) {
			entityTransaction.begin();
			entityManager.remove(getName);
			entityTransaction.commit();
		}
	}

	public List<Item> listItems() {
		String jpql = "SELECT t FROM items t";
		TypedQuery<Item> userQuery = entityManager.createQuery(jpql, Item.class);
		List<Item> item = userQuery.getResultList();
		return item;
	}

}
