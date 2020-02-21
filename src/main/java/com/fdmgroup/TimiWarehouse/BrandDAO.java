package com.fdmgroup.TimiWarehouse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class BrandDAO {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
	EntityManager entityManager = emf.createEntityManager();
	EntityTransaction entityTransaction = entityManager.getTransaction();
	

	public BrandDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addBrand(Brand brand) {
		Brand getName = entityManager.find(Brand.class, brand.getBrandId());
		if (getName == null) {
			entityTransaction.begin();
			entityManager.persist(brand);
			entityTransaction.commit();
		}

	}

	public Brand getBrand(int brandId) {
		Brand brand  = entityManager.find(Brand.class, brandId);
		return brand;

	}
	

	public void updateBrand(Brand brand) {
		Brand getName = entityManager.find(Brand.class, brand.getBrandId());
		if (getName != null) {
			entityTransaction.begin();
			entityManager.merge(brand);
			entityTransaction.commit();
		}

	}

	public void removeBrand(int brandId) {
		Brand brand  = entityManager.find(Brand.class, brandId);
		if (brand != null) {
			entityTransaction.begin();
			entityManager.remove(brand);
			entityTransaction.commit();
		} 

	}

	public List<Brand> listBrands() {
		String jpql = "SELECT t FROM brands t";
		TypedQuery<Brand> userQuery = entityManager.createQuery(jpql, Brand.class);
		List<Brand> brands = userQuery.getResultList();
		return brands;
		
	}
		

	
	
	
}
