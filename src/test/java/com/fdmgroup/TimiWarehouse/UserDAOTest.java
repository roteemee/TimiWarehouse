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

public class UserDAOTest {
	 User user1;
	 User user2;
	List<User>userList;
	UserDAO userDAO = new UserDAO();
	
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM users s").executeUpdate();
		entityTransaction.commit();
		
	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
			entityManager.createQuery("DELETE FROM users s").executeUpdate();
		entityTransaction.commit();
		user1 = new User("tboy", "Timi", "Akinbote","olarotimi.akinbote@fdmgroup.com","funkyPassword!","1 london bridge SE1");
		user2 = new User("jgirl", "Jola", "Akinbote","Jola.akinbote@fdmgroup.com","sleekyPassword!","1 BARKING ROAD IG11");
		userList = new ArrayList<User>();
	}

	@Test //1
	public void test_listAllUsersReturnsEmptyListWhenNoUsersAdded() {
		userList = userDAO.listUsers();
		assertEquals(0, userList.size());
		
	}
	
	@Test //2
	public void test_listAllUsersReturnsOneListWhenOneUserAdded() {
		userDAO.addUser(user1);
		userList = userDAO.listUsers();
		assertEquals(1, userList.size());
	}
	
	
	
	@Test //3
	public void test_listAllUsersReturnsOneListWhenTwoUserAddedAndOneRemoved() {
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		userDAO.removeUser("tboy");
		userList = userDAO.listUsers();
		assertEquals(1, userList.size());
	}
	
	@Test //4
	public void test_listAllUsersReturnsOneListWhenOneUserAddedTwice() {
		userDAO.addUser(user1);
		userDAO.addUser(user1);
		userList = userDAO.listUsers();
		assertEquals(1, userList.size());
	}
	

	@Test //5
	public void test_listAllUsersReturnsOneListWhenOneUserAddedAndOneThatsIsNotThereIsRemoved() {
		userDAO.addUser(user2);
		userDAO.removeUser("tboy");
		userList = userDAO.listUsers();
		assertEquals(1, userList.size());
	}
	
	@Test //6
	public void test_CheckIfUserAddedIsOnTheDatabase() {
		userDAO.addUser(user1);
		assertEquals(user1, userDAO.getUser("tboy"));
	}

	@Test //7
	public void test_listAllUsersUpdated() {
		userDAO.addUser(user1);
		User user1Updated = new User("tboy", "TimiTimi", "Akinbote","olarotimi.akinbote@fdmgroup.com","funkyPassword!","1 london bridge SE1");
		userDAO.updateUser(user1Updated);
		assertEquals(user1Updated, userDAO.getUser("tboy"));
	}
	
	@Test //8
	public void test_updateTheFirstUserAndCheckTheRightUserIsUpdated() {
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		User user1Updated = new User("tboy", "TimiTimi", "Akinbote","olarotimi.akinbote@fdmgroup.com","funkyPassword!","1 london bridge SE1");
		userDAO.updateUser(user1Updated);
		assertEquals(user1Updated, userDAO.getUser("tboy"));
	}
	@Test //9
	public void test_updateTheSecondUserAndCheckTheRightUserIsUpdated() {
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		User user1Updated = new User("jgirl", "JiggyBoo", "Akinbote","Jola.akinbote@fdmgroup.com","sleekyPassword!","1 BARKING ROAD IG11");;
		userDAO.updateUser(user1Updated);
		assertEquals(user1Updated, userDAO.getUser("jgirl"));
	}
	
	@Test //10
	public void test_UpdatingListWithUsersNotCurrentlyThereShouldDoNothing() {
		userDAO.addUser(user1);
		User user1Updated = new User("tboyboy", "Timi", "Akinbote","olarotimi.akinbote@fdmgroup.com","funkyPassword!","1 london bridge SE1");
		userDAO.updateUser(user1Updated);
		userList = userDAO.listUsers();
		assertEquals(1, userList.size());
	}
	
	@Test //11
	public void test_getRemovedFirstUserReturnsNull() {
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		userDAO.removeUser("tboy");
		assertEquals(null, userDAO.getUser("tboy"));
	}
	
	@Test //12
	public void test_getRemovedSecondUserReturnsNull() {
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		userDAO.removeUser("jgirl");
		assertEquals(null, userDAO.getUser("jgirl"));
	}
	

	@Test //13
	public void test_isAddedUserInTheList() {
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		userList = userDAO.listUsers();
		assertTrue(userList.contains(user1));
	}

	
}
