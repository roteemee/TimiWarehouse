package com.fdmgroup.TimiWarehouse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class SupplierDAO {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
	EntityManager entityManager = emf.createEntityManager();
	EntityTransaction entityTransaction = entityManager.getTransaction();

	public SupplierDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public void addSupplier(Supplier supplier) {
		Supplier getName = entityManager.find(Supplier.class, supplier.getSupplierId());
		if (getName == null) {
			entityTransaction.begin();
			entityManager.persist(supplier);
			entityTransaction.commit();
		}
		
	}
	
	public Supplier getSupplier(int  supplierId) {
		Supplier supplier  = entityManager.find(Supplier.class, supplierId);
		return supplier;
		
	}
	
	public void updateSupplier(Supplier supplier) {
		Supplier getName = entityManager.find(Supplier.class, supplier.getSupplierId());
		if (getName != null) {
			entityTransaction.begin();
			entityManager.merge(supplier);
			entityTransaction.commit();
		}
		
	}
	
	public void removeSupplier(int supplierId) {
		Supplier supplier  = entityManager.find(Supplier.class, supplierId);
		if (supplier != null) {
			entityTransaction.begin();
			entityManager.remove(supplier);
			entityTransaction.commit();
		} 
		
	}
	
	public List<Supplier> listSuppliers() {
		String jpql = "SELECT t FROM suppliers t";
		TypedQuery<Supplier> userQuery = entityManager.createQuery(jpql, Supplier.class);
		List<Supplier> suppliers = userQuery.getResultList();
		return suppliers;
	}

}
