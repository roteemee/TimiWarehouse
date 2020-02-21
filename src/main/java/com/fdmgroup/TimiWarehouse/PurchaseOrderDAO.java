package com.fdmgroup.TimiWarehouse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PurchaseOrderDAO {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
	EntityManager entityManager = emf.createEntityManager();
	EntityTransaction entityTransaction = entityManager.getTransaction();

	public PurchaseOrderDAO() {
		super();
	}

	public void addPO(PurchaseOrder po) {
		PurchaseOrder getName = entityManager.find(PurchaseOrder.class, po.getPoId());
		if (getName == null) {
			entityTransaction.begin();
			entityManager.persist(po);
			entityTransaction.commit();
		}

	}

	public PurchaseOrder getPO(int poId) {
		PurchaseOrder po  = entityManager.find(PurchaseOrder.class, poId);
		return po;

	}

	public void updatePO(PurchaseOrder po) {
		PurchaseOrder getName = entityManager.find(PurchaseOrder.class, po.getPoId());
		if (getName != null) {
			entityTransaction.begin();
			entityManager.merge(po);
			entityTransaction.commit();
		}

	}

	public void removePO(int poId) {
		PurchaseOrder po  = entityManager.find(PurchaseOrder.class, poId);
		if (po != null) {
			entityTransaction.begin();
			entityManager.remove(po);
			entityTransaction.commit();
		} 

	}

	public List<PurchaseOrder> listPOs() {
		String jpql = "SELECT t FROM purchase_order t";
		TypedQuery<PurchaseOrder> userQuery = entityManager.createQuery(jpql, PurchaseOrder.class);
		List<PurchaseOrder> pos = userQuery.getResultList();
		return pos;

	}

}
