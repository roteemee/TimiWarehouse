package com.fdmgroup.TimiWarehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BrandDAOTest {
	private Brand brand1;
	private Brand brand2;
	List<Brand>brandList;
	BrandDAO brandDAO = new BrandDAO();
	
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM brands s").executeUpdate();
		entityTransaction.commit();
		
	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM brands s").executeUpdate();
		entityTransaction.commit();
		brand1 = new Brand(1, "HairGel", "HairGel International");
		brand2 = new Brand(2, "BodyWashInc", "BodyWashIncorporated");
		brandList = new ArrayList<Brand>();
	}

	@Test //1
	public void test_listAllBrandsReturnsEmptyListWhenNoBrandsAdded() {
		brandList = brandDAO.listBrands();
		assertEquals(0, brandList.size());
	
	}
	
	@Test //2
	public void test_listAllBrandsReturnsOneListWhenOneBrandAdded() {
		brandDAO.addBrand(brand1);
		brandList = brandDAO.listBrands();
		assertEquals(1, brandList.size());
	}
	
	
	
	@Test //3
	public void test_listAllBrandsReturnsOneListWhenTwoBrandsAddedAndOneRemoved() {
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand2);
		brandDAO.removeBrand(1);
		brandList = brandDAO.listBrands();
		assertEquals(1, brandList.size());
	}
	
	@Test //4
	public void test_listAllBrandsReturnsOneListWhenOneBrandAddedTwice() {
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand1);
		brandList = brandDAO.listBrands();
		assertEquals(1, brandList.size());
	}
	

	@Test //5
	public void test_listAllBrandsReturnsOneListWhenOneBrandAddedAndOneThatsIsNotThereIsRemoved() {
		brandDAO.addBrand(brand2);
		brandDAO.removeBrand(1);
		brandList = brandDAO.listBrands();
		assertEquals(1, brandList.size());
	}
	
	@Test //6
	public void test_CheckIfBrandAddedIsOnTheDatabase() {
		brandDAO.addBrand(brand1);
		assertEquals(brand1, brandDAO.getBrand(1));
	}

	@Test //7
	public void test_listAllbrandsUpdated() {
		brandDAO.addBrand(brand1);
		Brand brand1Updated = new Brand(1, "Best HairGel", "HairGel International");
		brandDAO.updateBrand(brand1Updated);
		assertEquals(brand1Updated, brandDAO.getBrand(1));
	}
	
	@Test //8
	public void test_updateTheFirstBrandAndCheckTheRightBrandIsUpdated() {
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand2);
		Brand brand1Updated = new Brand(1, "Best HairGel", "HairGel Internation");
		brandDAO.updateBrand(brand1Updated);
		assertEquals(brand1Updated, brandDAO.getBrand(1));
	}
	@Test //9
	public void test_updateTheSecondBrandAndCheckTheRightBrandIsUpdated() {
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand2);
		Brand brand1Updated = new Brand(2, "Favourite BodyWash", "BodyWashIncorporated");
		brandDAO.updateBrand(brand1Updated);
		assertEquals(brand1Updated, brandDAO.getBrand(2));
	}
	
	@Test //10
	public void test_UpdatingListWithBrandsNotCurrentlyThereShouldDoNothing() {
		brandDAO.addBrand(brand1);
		Brand brand1Updated = new Brand(3, "Favourite BodyWashInc", "BodyWashIncorporated");
		brandDAO.updateBrand(brand1Updated);
		brandList = brandDAO.listBrands();
		assertEquals(1, brandList.size());
	}
	
	@Test //11
	public void test_getRemovedFirstBrandReturnsNull() {
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand2);
		brandDAO.removeBrand(1);
		assertEquals(null, brandDAO.getBrand(1));
	}
	
	@Test //12
	public void test_getRemovedSecondBrandReturnsNull() {
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand2);
		brandDAO.removeBrand(2);
		assertEquals(null, brandDAO.getBrand(2));
	}
	

	@Test //13
	public void test_isAddedBrandInTheList() {
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand2);
		brandList = brandDAO.listBrands();
		assertTrue(brandList.contains(brand1));
	}

	
}
