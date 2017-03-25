package br.com.teste.cleber.wrapper;

import br.com.teste.cleber.model.Client;

public class OrderWrapper {

	private Integer controlNumber;
	private String dateRegister;
	private String productName;
	private double productPrice;
	private Integer quantity;
	private Client client;
	private double totalPrice;
	
	public Integer getControlNumber() {
		return controlNumber;
	}
	public void setControlNumber(Integer controlNumber) {
		this.controlNumber = controlNumber;
	}
	public String getDateRegister() {
		return dateRegister;
	}
	public void setDateRegister(String dateRegister) {
		this.dateRegister = dateRegister;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
