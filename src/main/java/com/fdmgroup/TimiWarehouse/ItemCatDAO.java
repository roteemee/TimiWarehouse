package com.fdmgroup.TimiWarehouse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ItemCatDAO {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
	EntityManager entityManager = emf.createEntityManager();
	EntityTransaction entityTransaction = entityManager.getTransaction();

	public ItemCatDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addItemCat(ItemCat itemCat) {
		ItemCat getName = entityManager.find(ItemCat.class, itemCat.getItemCatId());
		if (getName == null) {
			entityTransaction.begin();
			entityManager.persist(itemCat);
			entityTransaction.commit();
		}

	}

	public ItemCat getItemCat(int itemCat) {
ItemCat itemCat1 = entityManager.find(ItemCat.class, itemCat);
		
		return itemCat1;

	}

	public void updateItemCat(ItemCat itemCat) {
		ItemCat getName = entityManager.find(ItemCat.class, itemCat.getItemCatId());
		if (getName != null) {
			entityTransaction.begin();
			entityManager.merge(itemCat);
			entityTransaction.commit();
		}

	}

	public void removeItemCat(int itemCat) {
		ItemCat getName = entityManager.find(ItemCat.class, itemCat);
		if (getName != null) {
			entityTransaction.begin();
			entityManager.remove(getName);
			entityTransaction.commit();
		}

	}

	public List<ItemCat> listItemCats() {
		String jpql = "SELECT t FROM Item_Categories t";
		TypedQuery<ItemCat> userQuery = entityManager.createQuery(jpql, ItemCat.class);
		List<ItemCat> itemCat = userQuery.getResultList();
		return itemCat;
	}
		

	
	
	
}
