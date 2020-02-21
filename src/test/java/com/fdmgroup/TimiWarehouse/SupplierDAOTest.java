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

public class SupplierDAOTest {
	private Supplier supplier1;
	private Supplier supplier2;
	List<Supplier>supplierList;
	SupplierDAO supplierDAO = new SupplierDAO();
	
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM suppliers s").executeUpdate();
		entityTransaction.commit();
		
	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM suppliers s").executeUpdate();
		entityTransaction.commit();
		supplier1 = new Supplier(1, "BodyWashInc", "info@bodywash.com","1 body wash road","+44012345789");
		supplier2 = new Supplier(2, "HairGelExpress", "hello@hairgel.net","251 crystal palace","+447845561471");
		supplierList = new ArrayList<Supplier>();
	}

	@Test //1
	public void test_listAllSuppliersReturnsEmptyListWhenNoSuppliersAdded() {
		supplierList = supplierDAO.listSuppliers();
		assertEquals(0, supplierList.size());
		
	}
	
	@Test //2
	public void test_listAllSuppliersReturnsOneListWhenOneSupplierAdded() {
		supplierDAO.addSupplier(supplier1);
		supplierList = supplierDAO.listSuppliers();
		assertEquals(1, supplierList.size());
	}
	
	
	
	@Test //3
	public void test_listAllSuppliersReturnsOneListWhenTwoSupplierAddedAndOneRemoved() {
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		supplierDAO.removeSupplier(1);
		supplierList = supplierDAO.listSuppliers();
		assertEquals(1, supplierList.size());
	}
	
	@Test //4
	public void test_listAllSuppliersReturnsOneListWhenOneSupplierAddedTwice() {
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier1);
		supplierList = supplierDAO.listSuppliers();
		assertEquals(1, supplierList.size());
	}
	

	@Test //5
	public void test_listAllSuppliersReturnsOneListWhenOneSupplierAddedAndOneThatsIsNotThereIsRemoved() {
		supplierDAO.addSupplier(supplier2);
		supplierDAO.removeSupplier(1);
		supplierList = supplierDAO.listSuppliers();
		assertEquals(1, supplierList.size());
	}
	
	@Test //6
	public void test_CheckIfSupplierAddedIsOnTheDatabase() {
		supplierDAO.addSupplier(supplier1);
		assertEquals(supplier1, supplierDAO.getSupplier(1));
	}

	@Test //7
	public void test_listAllSuppliersUpdated() {
		supplierDAO.addSupplier(supplier1);
		Supplier supplier1Updated = new Supplier(1, "BodyWashInc", "sales@bodywash.com","1 body wash road","+44012345789");
		supplierDAO.updateSupplier(supplier1Updated);
		assertEquals(supplier1Updated, supplierDAO.getSupplier(1));
	}
	
	@Test //8
	public void test_updateTheFirstSupplierAndCheckTheRightSupplierIsUpdated() {
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		Supplier supplier1Updated = new Supplier(1, "BodyWashInc", "sales@bodywash.com","1 body wash road","+44012345789");
		supplierDAO.updateSupplier(supplier1Updated);
		assertEquals(supplier1Updated, supplierDAO.getSupplier(1));
	}
	@Test //9
	public void test_updateTheSecondSupplierAndCheckTheRightSupplierIsUpdated() {
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		Supplier supplier1Updated = new Supplier(2, "HairGelExpress", "info@hairgel.net","251 crystal palace","+447845561471");
		supplierDAO.updateSupplier(supplier1Updated);
		assertEquals(supplier1Updated, supplierDAO.getSupplier(2));
	}
	
	@Test //10
	public void test_UpdatingListWithSuppliersNotCurrentlyThereShouldDoNothing() {
		supplierDAO.addSupplier(supplier1);
		Supplier supplier1Updated = new Supplier(3, "HairGelExpress", "info@hairgel.net","251 crystal palace","+447845561471");
		supplierDAO.updateSupplier(supplier1Updated);
		supplierList = supplierDAO.listSuppliers();
		assertEquals(1, supplierList.size());
	}
	
	@Test //11
	public void test_getRemovedFirstSupplierReturnsNull() {
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		supplierDAO.removeSupplier(1);
		assertEquals(null, supplierDAO.getSupplier(1));
	}
	
	@Test //12
	public void test_getRemovedSecondSupplierReturnsNull() {
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		supplierDAO.removeSupplier(2);
		assertEquals(null, supplierDAO.getSupplier(2));
	}
	

	@Test //13
	public void test_isAddedSupplierInTheList() {
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		supplierList = supplierDAO.listSuppliers();
		assertTrue(supplierList.contains(supplier1));
	}

	
}
