package com.fdmgroup.TimiWarehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
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

public class PurchaseOrderDAOTest {
	private PurchaseOrder po1;
	private PurchaseOrder po2;
	List<PurchaseOrder> poList;
	Map<Item, Integer> items;
	Item item1;
	Item item2;
	Brand brand1;
	Brand brand2;
	User user1;
	User user2;
	ItemCat itemCat1;
	ItemCat itemCat2;
	Supplier supplier1;
	Supplier supplier2;
	
	
	
	
	
	PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();
	ItemDAO itemDAO = new ItemDAO();
	BrandDAO brandDAO = new BrandDAO();
	UserDAO userDAO = new UserDAO();
	ItemCatDAO itemCatDAO = new ItemCatDAO();
	SupplierDAO supplierDAO = new SupplierDAO();
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		entityManager.createQuery("DELETE FROM purchase_order s").executeUpdate();
		entityManager.createQuery("DELETE FROM items s").executeUpdate();
		entityManager.createQuery("DELETE FROM suppliers s").executeUpdate();
		entityManager.createQuery("DELETE FROM Item_Categories s").executeUpdate();
		entityManager.createQuery("DELETE FROM brands s").executeUpdate();
		entityManager.createQuery("DELETE FROM users s").executeUpdate();
		entityTransaction.commit();

	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		entityManager.createQuery("DELETE FROM purchase_order s").executeUpdate();
		entityManager.createQuery("DELETE FROM items s").executeUpdate();
		entityManager.createQuery("DELETE FROM suppliers s").executeUpdate();
		entityManager.createQuery("DELETE FROM Item_Categories s").executeUpdate();
		entityManager.createQuery("DELETE FROM brands s").executeUpdate();
		entityManager.createQuery("DELETE FROM users s").executeUpdate();
		entityTransaction.commit();
		items = new HashMap<Item, Integer>();
		brand1 = new Brand(1, "HairGel", "HairGel Internation");
		brand2 = new Brand(1, "BodyWashInc", "BodyWashIncorporated");
		user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
				"1 london bridge SE1");
		user2 = new User("jgirl", "Jola", "Akinbote", "Jola.akinbote@fdmgroup.com", "sleekyPassword!",
				"1 BARKING ROAD IG11");
		itemCat1 = new ItemCat(1, "Shampoo");
		itemCat2 = new ItemCat(2, "Conditioner");
		supplier1 = new Supplier(1, "BodyWash", "info@bodywash.com", "1 body wash road", "+44012345789");
		supplier2 = new Supplier(2, "HairGel", "hello@hairgel.net", "251 crystal palace", "+447845561471");
		item1 = new Item(1, brand1, itemCat1, supplier1, "shampoo", "50ml", 10.99, 5);
		item2 = new Item(2, brand2, itemCat2, supplier2, "conditioner", "150ml", 24.99, 1);
		items.put(item1, 2);
		items.put(item2, 3);
		Date date1 = new Date(1220227200L);
		Date date2 = new Date(1220227200L);

		po1 = new PurchaseOrder(1, items, user1, date1, 68.99, "251 crystal palace", true);
		po2 = new PurchaseOrder(2, items, user2, date2, 18.99, "251 crystal palace", false);
		poList = new ArrayList<PurchaseOrder>();
		
		
		brandDAO.addBrand(brand1);
		brandDAO.addBrand(brand2);
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		itemCatDAO.addItemCat(itemCat1);
		itemCatDAO.addItemCat(itemCat2);
		supplierDAO.addSupplier(supplier1);
		supplierDAO.addSupplier(supplier2);
		itemDAO.addItem(item1);
		itemDAO.addItem(item2);
		
		
	}

	@Test // 1
	public void test_listAllpurchaseOrdersReturnsEmptyListWhenNopurchaseOrdersAdded() {
		poList = purchaseOrderDAO.listPOs();
		assertEquals(0, poList.size());

	}

	@Test // 2
	public void test_listAllpurchaseOrdersReturnsOneListWhenOnepurchaseOrderAdded() {
		purchaseOrderDAO.addPO(po1);
		poList = purchaseOrderDAO.listPOs();
		assertEquals(1, poList.size());
	}

	@Test // 3
	public void test_listAllpurchaseOrdersReturnsOneListWhenTwopurchaseOrderAddedAndOneRemoved() {
		purchaseOrderDAO.addPO(po1);
		purchaseOrderDAO.addPO(po2);
		purchaseOrderDAO.removePO(1);
		poList = purchaseOrderDAO.listPOs();
		assertEquals(1, poList.size());
	}

	@Test // 4
	public void test_listAllpurchaseOrdersReturnsOneListWhenOnepurchaseOrderAddedTwice() {
		System.out.println("Test 4");
		purchaseOrderDAO.addPO(po1);
		purchaseOrderDAO.addPO(po1);
		poList = purchaseOrderDAO.listPOs();
		assertEquals(1, poList.size());
	}

	@Test // 5
	public void test_listAllpurchaseOrdersReturnsOneListWhenOnepurchaseOrderAddedAndOneThatsIsNotThereIsRemoved() {
		purchaseOrderDAO.addPO(po2);
		purchaseOrderDAO.removePO(1);
		poList = purchaseOrderDAO.listPOs();
		assertEquals(1, poList.size());
	}

	@Test // 6
	public void test_CheckIfpurchaseOrderAddedIsOnTheDatabase() {
		purchaseOrderDAO.addPO(po1);
		assertEquals(po1, purchaseOrderDAO.getPO(1));
	}

	@Test // 7
	public void test_listAllpurchaseOrdersUpdated() {
//		Map<Item, Integer> items = new HashMap<Item, Integer>();
//		Brand brand1 = new Brand(1, "HairGel", "HairGel Internation");
//		Brand brand2 = new Brand(1, "BodyWashInc", "BodyWashIncorporated");
//		User user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
//				"1 london bridge SE1");
//		User user2 = new User("jgirl", "Jola", "Akinbote", "Jola.akinbote@fdmgroup.com", "sleekyPassword!",
//				"1 BARKING ROAD IG11");
//		ItemCat itemCat1 = new ItemCat(1, "Shampoo");
//		ItemCat itemCat2 = new ItemCat(2, "Conditioner");
//		Supplier supplier1 = new Supplier(1, "BodyWash", "info@bodywash.com", "1 body wash road", "+44012345789");
//		Supplier supplier2 = new Supplier(2, "HairGel", "hello@hairgel.net", "251 crystal palace", "+447845561471");
//		Item item1 = new Item(1, brand1, itemCat1, supplier1, "shampoo", "50ml", 10.99, 5);
//		Item item2 = new Item(2, brand2, itemCat2, supplier2, "conditioner", "150ml", 24.99, 1);
//		items.put(item1, 2);
//		items.put(item2, 3);
		Date date1 = new Date(1220227200L);
		Date date2 = new Date(1220227200L);

		po1 = new PurchaseOrder(1, items, user1, date1, 68.99, "251 crystal palace", true);
		po2 = new PurchaseOrder(2, items, user2, date2, 18.99, "251 crystal palace", false);
		poList = new ArrayList<PurchaseOrder>();
		purchaseOrderDAO.addPO(po1);
		PurchaseOrder supplier1Updated = new PurchaseOrder(1, items, user1, date1, 68.99, "251 crystal palace road", true);
		purchaseOrderDAO.updatePO(supplier1Updated);
		assertEquals(supplier1Updated, purchaseOrderDAO.getPO(1));
	}

	@Test // 8
	public void test_updateTheFirstpurchaseOrderAndCheckTheRightpurchaseOrderIsUpdated() {
//		Map<Item, Integer> items = new HashMap<Item, Integer>();
//		Brand brand1 = new Brand(1, "HairGel", "HairGel Internation");
//		Brand brand2 = new Brand(1, "BodyWashInc", "BodyWashIncorporated");
//		User user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
//				"1 london bridge SE1");
//		User user2 = new User("jgirl", "Jola", "Akinbote", "Jola.akinbote@fdmgroup.com", "sleekyPassword!",
//				"1 BARKING ROAD IG11");
//		ItemCat itemCat1 = new ItemCat(1, "Shampoo");
//		ItemCat itemCat2 = new ItemCat(2, "Conditioner");
//		Supplier supplier1 = new Supplier(1, "BodyWash", "info@bodywash.com", "1 body wash road", "+44012345789");
//		Supplier supplier2 = new Supplier(2, "HairGel", "hello@hairgel.net", "251 crystal palace", "+447845561471");
//		Item item1 = new Item(1, brand1, itemCat1, supplier1, "shampoo", "50ml", 10.99, 5);
//		Item item2 = new Item(2, brand2, itemCat2, supplier2, "conditioner", "150ml", 24.99, 1);
//		items.put(item1, 2);
//		items.put(item2, 3);
		Date date1 = new Date(1220227200L);
		Date date2 = new Date(1220227200L);

		po1 = new PurchaseOrder(1, items, user1, date1, 68.99, "251 crystal palace", true);
		po2 = new PurchaseOrder(2, items, user2, date2, 18.99, "251 crystal palace", false);
		poList = new ArrayList<PurchaseOrder>();
		purchaseOrderDAO.addPO(po1);
		purchaseOrderDAO.addPO(po2);
		PurchaseOrder supplier1Updated = new PurchaseOrder(1, items, user1, date1, 68.99, "251 crystal palace road", true);
		purchaseOrderDAO.updatePO(supplier1Updated);
		assertEquals(supplier1Updated, purchaseOrderDAO.getPO(1));
	}

	@Test // 9
	public void test_updateTheSecondpurchaseOrderAndCheckTheRightpurchaseOrderIsUpdated() {
//		Map<Item, Integer> items = new HashMap<Item, Integer>();
//		Brand brand1 = new Brand(1, "HairGel", "HairGel Internation");
//		Brand brand2 = new Brand(1, "BodyWashInc", "BodyWashIncorporated");
//		User user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
//				"1 london bridge SE1");
//		User user2 = new User("jgirl", "Jola", "Akinbote", "Jola.akinbote@fdmgroup.com", "sleekyPassword!",
//				"1 BARKING ROAD IG11");
//		ItemCat itemCat1 = new ItemCat(1, "Shampoo");
//		ItemCat itemCat2 = new ItemCat(2, "Conditioner");
//		Supplier supplier1 = new Supplier(1, "BodyWash", "info@bodywash.com", "1 body wash road", "+44012345789");
//		Supplier supplier2 = new Supplier(2, "HairGel", "hello@hairgel.net", "251 crystal palace", "+447845561471");
//		Item item1 = new Item(1, brand1, itemCat1, supplier1, "shampoo", "50ml", 10.99, 5);
//		Item item2 = new Item(2, brand2, itemCat2, supplier2, "conditioner", "150ml", 24.99, 1);
//		items.put(item1, 2);
//		items.put(item2, 3);
		Date date1 = new Date(1220227200L);
		Date date2 = new Date(1220227200L);

		po1 = new PurchaseOrder(1, items, user1, date1, 68.99, "251 crystal palace", true);
		po2 = new PurchaseOrder(2, items, user2, date2, 18.99, "251 crystal palace", false);
		poList = new ArrayList<PurchaseOrder>();
		purchaseOrderDAO.addPO(po1);
		purchaseOrderDAO.addPO(po2);
		PurchaseOrder po1Updated = new PurchaseOrder(2, items, user2, date2, 18.99, "251 crystal palace road", false);;
		purchaseOrderDAO.updatePO(po1Updated);
		assertEquals(po1Updated, purchaseOrderDAO.getPO(2));
	}

	@Test // 10
	public void test_UpdatingListWithpurchaseOrdersNotCurrentlyThereShouldDoNothing() {
//		Map<Item, Integer> items = new HashMap<Item, Integer>();
//		Brand brand1 = new Brand(1, "HairGel", "HairGel Internation");
//		Brand brand2 = new Brand(1, "BodyWashInc", "BodyWashIncorporated");
//		User user2 = new User("jgirl", "Jola", "Akinbote", "Jola.akinbote@fdmgroup.com", "sleekyPassword!",
//				"1 BARKING ROAD IG11");
//		ItemCat itemCat1 = new ItemCat(1, "Shampoo");
//		ItemCat itemCat2 = new ItemCat(2, "Conditioner");
//		Supplier supplier1 = new Supplier(1, "BodyWash", "info@bodywash.com", "1 body wash road", "+44012345789");
//		Supplier supplier2 = new Supplier(2, "HairGel", "hello@hairgel.net", "251 crystal palace", "+447845561471");
//		Item item1 = new Item(1, brand1, itemCat1, supplier1, "shampoo", "50ml", 10.99, 5);
//		Item item2 = new Item(2, brand2, itemCat2, supplier2, "conditioner", "150ml", 24.99, 1);
//		items.put(item1, 2);
//		items.put(item2, 3);
		Date date2 = new Date(1220227200L);
		purchaseOrderDAO.addPO(po1);
		PurchaseOrder po1Updated = new PurchaseOrder(3, items, user2, date2, 18.99, "251 crystal palace road", false);
		purchaseOrderDAO.updatePO(po1Updated);
		poList = purchaseOrderDAO.listPOs();
		assertEquals(1, poList.size());
	}

	@Test // 11
	public void test_getRemovedFirstpurchaseOrderReturnsNull() {
		purchaseOrderDAO.addPO(po1);
		purchaseOrderDAO.addPO(po2);
		purchaseOrderDAO.removePO(1);
		assertEquals(null, purchaseOrderDAO.getPO(1));
	}

	@Test // 12
	public void test_getRemovedSecondpurchaseOrderReturnsNull() {
		purchaseOrderDAO.addPO(po1);
		purchaseOrderDAO.addPO(po2);
		purchaseOrderDAO.removePO(2);
		assertEquals(null, purchaseOrderDAO.getPO(2));
	}

	@Test // 13
	public void test_isAddedpurchaseOrderInTheList() {
		purchaseOrderDAO.addPO(po1);
		purchaseOrderDAO.addPO(po2);
		poList = purchaseOrderDAO.listPOs();
		assertTrue(poList.contains(po1));
	}

}
