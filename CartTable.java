package Model;

public class CartTable {
	private Product product;

	public Product getProduct() {
        return product;
    }
	
	private String name, brand;
	private int price, quantity, totalPrice;
	public CartTable(String name, String brand, int price, int quantity, int totalPrice) {
		super();
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	

}
