package com.fdmgroup.TimiWarehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "items")
public class Item {

	@Id
	@Column(name = "item_id", length = 10)
	private int itemId;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "item_cat_id")
	private ItemCat itemCat;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@Column ( length = 50)
	private String description;

	@Column ( length = 5)
	private String itemSize;

	@Column ( precision =10, scale = 2)
	private double price;

	@Column(name = "stock_level", length = 5)
	private int stockLevel;



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((itemCat == null) ? 0 : itemCat.hashCode());
		result = prime * result + itemId;
		result = prime * result + ((itemSize == null) ? 0 : itemSize.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + stockLevel;
		result = prime * result + ((supplier == null) ? 0 : supplier.hashCode());
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
		Item other = (Item) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (itemCat == null) {
			if (other.itemCat != null)
				return false;
		} else if (!itemCat.equals(other.itemCat))
			return false;
		if (itemId != other.itemId)
			return false;
		if (itemSize == null) {
			if (other.itemSize != null)
				return false;
		} else if (!itemSize.equals(other.itemSize))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (stockLevel != other.stockLevel)
			return false;
		if (supplier == null) {
			if (other.supplier != null)
				return false;
		} else if (!supplier.equals(other.supplier))
			return false;
		return true;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public ItemCat getItemCat() {
		return itemCat;
	}

	public void setItemCat(ItemCat itemCat) {
		this.itemCat = itemCat;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public Item(int itemId, Brand brand, ItemCat itemCat, Supplier supplier, String description, String itemSize,
			double price, int stockLevel) {
		super();
		this.itemId = itemId;
		this.brand = brand;
		this.itemCat = itemCat;
		this.supplier = supplier;
		this.description = description;
		this.itemSize = itemSize;
		this.price = price;
		this.stockLevel = stockLevel;
	}
	
	public Item() {
		
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", brand=" + brand + ", itemCat=" + itemCat + ", supplier=" + supplier
				+ ", description=" + description + ", itemSize=" + itemSize + ", price=" + price + ", stockLevel="
				+ stockLevel + "]";
	}


}
