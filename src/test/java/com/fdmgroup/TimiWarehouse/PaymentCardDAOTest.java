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

public class PaymentCardDAOTest {
	private PaymentCard pc1;
	private PaymentCard pc2;
	List<PaymentCard>pcList;
	PaymentCardDAO paymentCardDAO = new PaymentCardDAO();
	UserDAO userDAO = new UserDAO();
	
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM payment_card s").executeUpdate();
		entityTransaction.commit();
		
	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM payment_card s").executeUpdate();
		entityTransaction.commit();
	
		User user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
				"1 london bridge SE1");
		User user2 = new User("jgirl", "Jola", "Akinbote", "Jola.akinbote@fdmgroup.com", "sleekyPassword!",
				"1 BARKING ROAD IG11");
		pc1 = new PaymentCard(1, user1, 5000.00);
		pc2 = new PaymentCard(2, user2, 7000.00);
		pcList = new ArrayList<PaymentCard>();
		userDAO.addUser(user1);
		userDAO.addUser(user2);
	}

	@Test //1
	public void test_listAllPaymentCardsReturnsEmptyListWhenNoPaymentCardsAdded() {
		pcList = paymentCardDAO.listPaymentCards();
		assertEquals(0, pcList.size());
		
	}
	
	@Test //2
	public void test_listAllPaymentCardsReturnsOneListWhenOnePaymentCardAdded() {
		paymentCardDAO.addPaymentCard(pc1);
		pcList = paymentCardDAO.listPaymentCards();
		assertEquals(1, pcList.size());
	}
	
	
	
	@Test //3
	public void test_listAllPaymentCardsReturnsOneListWhenTwoPaymentCardAddedAndOneRemoved() {
		paymentCardDAO.addPaymentCard(pc1);
		paymentCardDAO.addPaymentCard(pc2);
		paymentCardDAO.removePaymentCard(1);
		pcList = paymentCardDAO.listPaymentCards();
		assertEquals(1, pcList.size());
	}
	
	@Test //4
	public void test_listAllPaymentCardsReturnsOneListWhenOnePaymentCardAddedTwice() {
		paymentCardDAO.addPaymentCard(pc1);
		paymentCardDAO.addPaymentCard(pc1);
		pcList = paymentCardDAO.listPaymentCards();
		assertEquals(1, pcList.size());
	}
	

	@Test //5
	public void test_listAllPaymentCardsReturnsOneListWhenOnePaymentCardAddedAndOneThatsIsNotThereIsRemoved() {
		paymentCardDAO.addPaymentCard(pc2);
		paymentCardDAO.removePaymentCard(1);
		pcList = paymentCardDAO.listPaymentCards();
		assertEquals(1, pcList.size());
	}
	
	@Test //6
	public void test_CheckIfPaymentCardAddedIsOnTheDatabase() {
		paymentCardDAO.addPaymentCard(pc1);
		assertEquals(pc1, paymentCardDAO.getPaymentCard(1));
	}

	@Test //7
	public void test_listAllPaymentCardsUpdated() {
		User user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
				"1 london bridge SE1");
		paymentCardDAO.addPaymentCard(pc1);
		PaymentCard paymentCard1Updated = new PaymentCard(1, user1, 3000.00);
		paymentCardDAO.updatePaymentCard(paymentCard1Updated);
		assertEquals(paymentCard1Updated, paymentCardDAO.getPaymentCard(1));
	}
	
	@Test //8
	public void test_updateTheFirstPaymentCardAndCheckTheRightPaymentCardIsUpdated() {
		User user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
				"1 london bridge SE1");
		paymentCardDAO.addPaymentCard(pc1);
		paymentCardDAO.addPaymentCard(pc2);
		PaymentCard paymentCard1Updated = new PaymentCard(1, user1, 3000.00);
		paymentCardDAO.updatePaymentCard(paymentCard1Updated);
		assertEquals(paymentCard1Updated, paymentCardDAO.getPaymentCard(1));
	}
	@Test //9
	public void test_updateTheSecondPaymentCardAndCheckTheRightPaymentCardIsUpdated() {
		User user2 = new User("jgirl", "Jola", "Akinbote", "Jola.akinbote@fdmgroup.com", "sleekyPassword!",
				"1 BARKING ROAD IG11");
		paymentCardDAO.addPaymentCard(pc1);
		paymentCardDAO.addPaymentCard(pc2);
		PaymentCard paymentCard1Updated = new PaymentCard(2, user2, 10000.00);
		paymentCardDAO.updatePaymentCard(paymentCard1Updated);
		assertEquals(paymentCard1Updated, paymentCardDAO.getPaymentCard(2));
	}
	
	@Test //10
	public void test_UpdatingListWithPaymentCardsNotCurrentlyThereShouldDoNothing() {
		User user1 = new User("tboy", "Timi", "Akinbote", "olarotimi.akinbote@fdmgroup.com", "funkyPassword!",
				"1 london bridge SE1");
		paymentCardDAO.addPaymentCard(pc1);
		PaymentCard paymentCard1Updated = new PaymentCard(3, user1, 3000.00);
		paymentCardDAO.updatePaymentCard(paymentCard1Updated);
		pcList = paymentCardDAO.listPaymentCards();
		assertEquals(1, pcList.size());
	}
	
	@Test //11
	public void test_getRemovedFirstPaymentCardReturnsNull() {
		paymentCardDAO.addPaymentCard(pc1);
		paymentCardDAO.addPaymentCard(pc2);
		paymentCardDAO.removePaymentCard(1);
		assertEquals(null, paymentCardDAO.getPaymentCard(1));
	}
	
	@Test //12
	public void test_getRemovedSecondPaymentCardReturnsNull() {
		paymentCardDAO.addPaymentCard(pc1);
		paymentCardDAO.addPaymentCard(pc2);
		paymentCardDAO.removePaymentCard(2);
		assertEquals(null, paymentCardDAO.getPaymentCard(2));
	}
	

	@Test //13
	public void test_isAddedPaymentCardInTheList() {
		paymentCardDAO.addPaymentCard(pc1);
		paymentCardDAO.addPaymentCard(pc2);
		pcList = paymentCardDAO.listPaymentCards();
		assertTrue(pcList.contains(pc1));
	}

	
}
