package com.fdmgroup.TimiWarehouse;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class PaymentCardDAO {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TimiWarehouse");
	EntityManager entityManager = emf.createEntityManager();
	EntityTransaction entityTransaction = entityManager.getTransaction();
	

	public void addPaymentCard(PaymentCard paymentCard) {
		PaymentCard getName = entityManager.find(PaymentCard.class, paymentCard.getCardId());
		if (getName == null) {
			entityTransaction.begin();
			entityManager.persist(paymentCard);
			entityTransaction.commit();
		}

	}

	public PaymentCard getPaymentCard(int poId) {
PaymentCard poId1 = entityManager.find(PaymentCard.class, poId);
		
		return poId1;

	}

	public void updatePaymentCard(PaymentCard pc) {
		PaymentCard getName = entityManager.find(PaymentCard.class, pc.getCardId());
		if (getName != null) {
			entityTransaction.begin();
			entityManager.merge(pc);
			entityTransaction.commit();
		}

	}

	public void removePaymentCard(int cardId) {
		PaymentCard getName = entityManager.find(PaymentCard.class, cardId);
		if (getName != null) {
			entityTransaction.begin();
			entityManager.remove(getName);
			entityTransaction.commit();
		}

	}

	public List<PaymentCard> listPaymentCards() {
		String jpql = "SELECT t FROM payment_card t";
		TypedQuery<PaymentCard> userQuery = entityManager.createQuery(jpql, PaymentCard.class);
		List<PaymentCard> pc = userQuery.getResultList();
		return pc;
	}


}
