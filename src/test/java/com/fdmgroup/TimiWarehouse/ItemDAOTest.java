package com.fdmgroup.TimiWarehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ItemDAOTest {
	
	List<Item> itemList;
	ItemDAO itemDAO = new ItemDAO();
	BrandDAO brandDAO = new BrandDAO();
	ItemCatDAO itemCatDAO = new ItemCatDAO();
	SupplierDAO supplierDAO = new SupplierDAO();
	
	Brand brand1;
	Brand brand2;
	ItemCat itemCat1;
	ItemCat itemCat2;
	Supplier supplier1;
	Supplier supplier2;
	Item item1;
	Item item2;

	
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		entityManager.createQuery("DELETE FROM items s").executeUpdate();
		entityManager.createQuery("DELETE FROM brands s").executeUpdate();
		entityManager.createQuery("DELETE FROM Item_Categories s").executeUpdate();
		entityManager.createQuery("DELETE FROM suppliers s").executeUpdate();
		entityTransaction.commit();

	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		entityManager.createQuery("DELETE FROM items s").executeUpdate();
		entityManager.createQuery("DELETE FROM brands s").executeUpdate();
		entityManager.createQuery("DELETE FROM Item_Categories s").executeUpdate();
		entityManager.createQuery("DELETE FROM suppliers s").executeUpdate();
		entityTransaction.commit();
		Map<Item, Integer> items = new HashMap<Item, Integer>();
		brand1 = new Brand(1, "HairGel", "HairGel Internation");
		brand2 = new Brand(1, "BodyWashInc", "BodyWashIncorporated");
		itemCat1 = new ItemCat(1, "Shampoo");
		itemCat2 = new ItemCat(2, "Conditioner");
		supplier1 = new Supplier(1, "BodyWash", "info@bodywash.com", "1 body wash road", "+44012345789");
		supplier2 = new Supplier(2, "HairGel", "hello@hairgel.net", "251 crystal palace", "+447845561471");
		item1 = new Item(1, brand1, itemCat1, supplier1, "shampoo", "50ml", 10.99, 5);
		item2 = new Item(2, brand2, itemCat2, supplier2, "conditioner", "150ml", 24.99, 1);
		items.put(item1, 2);
		items.put(item2, 3);
		
		itemList = new ArrayList<Item>(); 
		 brandDAO.addBrand(brand1); 
		 brandDAO.addBrand(brand1); 
		 itemCatDAO.addItemCat(itemCat1); 
		 itemCatDAO.addItemCat(itemCat2); 
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		
	}

	@Test // 1
	public void test_listAllItemsReturnsEmptyListWhenNoItemsAdded() {
		itemList = itemDAO.listItems();
		assertEquals(0, itemList.size());

	}

	@Test // 2
	public void test_listAllItemsReturnsOneListWhenOneItemAdded() {
		itemDAO.addItem(item1);
		itemList = itemDAO.listItems();
		assertEquals(1, itemList.size());
	}

	@Test // 3
	public void test_listAllItemsReturnsOneListWhenTwoItemAddedAndOneRemoved() {
		itemDAO.addItem(item1);
		itemDAO.addItem(item2);
		itemDAO.removeItem(1);
		itemList = itemDAO.listItems();
		assertEquals(1, itemList.size());
	}

	@Test // 4
	public void test_listAllItemsReturnsOneListWhenOneItemAddedTwice() {
		itemDAO.addItem(item1);
		itemDAO.addItem(item1);
		itemList = itemDAO.listItems();
		assertEquals(1, itemList.size());
	}

	@Test // 5
	public void test_listAllItemsReturnsOneListWhenOneItemAddedAndOneThatsIsNotThereIsRemoved() {
		itemDAO.addItem(item2);
		itemDAO.removeItem(1);
		itemList = itemDAO.listItems();
		assertEquals(1, itemList.size());
	}

	@Test // 6
	public void test_CheckIfItemAddedIsOnTheDatabase() {
		itemDAO.addItem(item1);
		assertEquals(item1, itemDAO.getItem(1));
	}

	@Test // 7
	public void test_listAllItemsUpdated() {
		itemDAO.addItem(item1);
		Item supplier1Updated = new Item(1, brand1, itemCat1, supplier1, "energising shampoo", "50ml", 10.99, 5);
		itemDAO.updateItem(supplier1Updated);
		assertEquals(supplier1Updated, itemDAO.getItem(1));
	}

	@Test // 8
	public void test_updateTheFirstItemAndCheckTheRightItemIsUpdated() {
		itemDAO.addItem(item1);
		itemDAO.addItem(item2);
		Item supplier1Updated = new Item(1, brand1, itemCat1, supplier1, "energising shampoo", "50ml", 10.99, 5);
		itemDAO.updateItem(supplier1Updated);
		assertEquals(supplier1Updated, itemDAO.getItem(1));
	}

	@Test // 9
	public void test_updateTheSecondItemAndCheckTheRightItemIsUpdated() {
		itemDAO.addItem(item1);
		itemDAO.addItem(item2);
		Item po1Updated = new Item(2, brand1, itemCat2, supplier2, "soothing conditioner", "150ml", 25.99, 1);
		itemDAO.updateItem(po1Updated);
		assertEquals(po1Updated, itemDAO.getItem(2));
	}

	@Test // 10
	public void test_UpdatingListWithItemsNotCurrentlyThereShouldDoNothing() {
		itemDAO.addItem(item1);
		Item po1Updated = new Item(3, brand2, itemCat2, supplier2, "soothing conditioner", "150ml", 24.99, 1);
		itemDAO.updateItem(po1Updated);
		itemList = itemDAO.listItems();
		assertEquals(1, itemList.size());
	}

	@Test // 11
	public void test_getRemovedFirstItemReturnsNull() {
		itemDAO.addItem(item1);
		itemDAO.addItem(item2);
		itemDAO.removeItem(1);
		assertEquals(null, itemDAO.getItem(1));
	}

	@Test // 12
	public void test_getRemovedSecondItemReturnsNull() {
		itemDAO.addItem(item1);
		itemDAO.addItem(item2);
		itemDAO.removeItem(2);
		assertEquals(null, itemDAO.getItem(2));
	}

	@Test // 13
	public void test_isAddedItemInTheList() {
		itemDAO.addItem(item1);
		itemDAO.addItem(item2);
		itemList = itemDAO.listItems();
		assertTrue(itemList.contains(item1));
	}

}
