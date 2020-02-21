package com.fdmgroup.TimiWarehouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity(name = "purchase_order")
public class PurchaseOrder {

	@Id
	@Column(name = "po_id", length = 10)
	private int poId;

	@ElementCollection
	@CollectionTable(name = "item_quantity", joinColumns = @JoinColumn(name = "po_id"))
	@MapKeyJoinColumn(name = "item_id")
	@Column(name = "quantity")

	private Map<Item, Integer> lineItem = new HashMap<Item, Integer>();

	@ManyToOne
	@JoinColumn(name = "username")
	private User user;

	@Temporal(TemporalType.DATE)
	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "total_price", precision =10, scale = 2)
	private double totalPrice;

	@Column(length = 50)
	private String address;

	@Type(type = "yes_no")
	@Column (length = 1)
	private boolean completed;

	public PurchaseOrder() {
		super();
	}

	public PurchaseOrder(int poId, Map<Item, Integer> lineItem, User user, Date orderDate, double totalPrice,
			String address, boolean completed) {
		super();
		this.poId = poId;
		this.lineItem = lineItem;
		
		this.user = user;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.address = address;
		this.completed = completed;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public Map<Item, Integer> getLineItem() {
		return lineItem;
	}

	public void setLineItem(Map<Item, Integer> lineItem) {
		this.lineItem = lineItem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void addToLineItem(Item item, Integer quantity) {
		lineItem.put(item, quantity);
	}

	public void removeFromLineItem(Item item) {
		lineItem.remove(item);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((lineItem == null) ? 0 : lineItem.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + poId;
		long temp;
		temp = Double.doubleToLongBits(totalPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseOrder other = (PurchaseOrder) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (completed != other.completed)
			return false;
		if (lineItem == null) {
			if (other.lineItem != null)
				return false;
		} else if (!lineItem.equals(other.lineItem))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (poId != other.poId)
			return false;
		if (Double.doubleToLongBits(totalPrice) != Double.doubleToLongBits(other.totalPrice))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	

}
