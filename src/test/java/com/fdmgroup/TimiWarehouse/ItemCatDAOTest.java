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

public class ItemCatDAOTest {
	private ItemCat ic1;
	private ItemCat ic2;
	List<ItemCat>icList;
	ItemCatDAO itemCatDAO = new ItemCatDAO();
	
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		entityTransaction.commit();
		
	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM Item_Categories s").executeUpdate();
		entityTransaction.commit();
	
		ic1 = new ItemCat(1, "Shampoo");
		ic2 = new ItemCat(2, "Conditioner");
		icList = new ArrayList<ItemCat>();
	}

	@Test //1
	public void test_listAllItemCatsReturnsEmptyListWhenNoItemCatsAdded() {
		icList = itemCatDAO.listItemCats();
		assertEquals(0, icList.size());
		
	}
	
	@Test //2
	public void test_listAllItemCatsReturnsOneListWhenOneItemCatAdded() {
		itemCatDAO.addItemCat(ic1);
		icList = itemCatDAO.listItemCats();
		assertEquals(1, icList.size());
	}
	
	
	
	@Test //3
	public void test_listAllItemCatsReturnsOneListWhenTwoItemCatAddedAndOneRemoved() {
		itemCatDAO.addItemCat(ic1);
		itemCatDAO.addItemCat(ic2);
		itemCatDAO.removeItemCat(1);
		icList = itemCatDAO.listItemCats();
		assertEquals(1, icList.size());
	}
	
	@Test //4
	public void test_listAllItemCatsReturnsOneListWhenOneItemCatAddedTwice() {
		itemCatDAO.addItemCat(ic1);
		itemCatDAO.addItemCat(ic1);
		icList = itemCatDAO.listItemCats();
		assertEquals(1, icList.size());
	}
	

	@Test //5
	public void test_listAllItemCatsReturnsOneListWhenOneItemCatAddedAndOneThatsIsNotThereIsRemoved() {
		itemCatDAO.addItemCat(ic2);
		itemCatDAO.removeItemCat(1);
		icList = itemCatDAO.listItemCats();
		assertEquals(1, icList.size());
	}
	
	@Test //6
	public void test_CheckIfItemCatAddedIsOnTheDatabase() {
		itemCatDAO.addItemCat(ic1);
		assertEquals(ic1, itemCatDAO.getItemCat(1));
	}

	@Test //7
	public void test_listAllItemCatsUpdated() {
		itemCatDAO.addItemCat(ic1);
		ItemCat paymentCard1Updated = new ItemCat(1, "Energising Shampoo");
		itemCatDAO.updateItemCat(paymentCard1Updated);
		assertEquals(paymentCard1Updated, itemCatDAO.getItemCat(1));
	}
	
	@Test //8
	public void test_updateTheFirstItemCatAndCheckTheRightItemCatIsUpdated() {
		itemCatDAO.addItemCat(ic1);
		itemCatDAO.addItemCat(ic2);
		ItemCat paymentCard1Updated = new ItemCat(1, "Energising Shampoo");
		itemCatDAO.updateItemCat(paymentCard1Updated);
		assertEquals(paymentCard1Updated, itemCatDAO.getItemCat(1));
	}
	@Test //9
	public void test_updateTheSecondItemCatAndCheckTheRightItemCatIsUpdated() {
		itemCatDAO.addItemCat(ic1);
		itemCatDAO.addItemCat(ic2);
		ItemCat paymentCard1Updated = new ItemCat(2, "Cleansing Conditioner");
		itemCatDAO.updateItemCat(paymentCard1Updated);
		ItemCat result = itemCatDAO.getItemCat(2);
		assertEquals(paymentCard1Updated, result);
	}
	
	@Test //10
	public void test_UpdatingListWithItemCatsNotCurrentlyThereShouldDoNothing() {
		itemCatDAO.addItemCat(ic1);
		ItemCat paymentCard1Updated = new ItemCat(3, "Cleansing Conditioner");
		itemCatDAO.updateItemCat(paymentCard1Updated);
		icList = itemCatDAO.listItemCats();
		assertEquals(1, icList.size());
	}
	
	@Test //11
	public void test_getRemovedFirstItemCatReturnsNull() {
		itemCatDAO.addItemCat(ic1);
		itemCatDAO.addItemCat(ic2);
		itemCatDAO.removeItemCat(1);
		assertEquals(null, itemCatDAO.getItemCat(1));
	}
	
	@Test //12
	public void test_getRemovedSecondItemCatReturnsNull() {
		itemCatDAO.addItemCat(ic1);
		itemCatDAO.addItemCat(ic2);
		itemCatDAO.removeItemCat(2);
		assertEquals(null, itemCatDAO.getItemCat(2));
	}
	

	@Test //13
	public void test_isAddedItemCatInTheList() {
		itemCatDAO.addItemCat(ic1);
		itemCatDAO.addItemCat(ic2);
		icList = itemCatDAO.listItemCats();
		assertTrue(icList.contains(ic1));
	}

	
}
