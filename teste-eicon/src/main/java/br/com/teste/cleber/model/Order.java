package br.com.teste.cleber.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "control_number")
	private Integer controlNumber;
	@Column(name = "date_register")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateRegister;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "product_price")
	private double productPrice;
	@Column(name = "quantity")
	private Integer quantity;
	@ManyToOne(fetch=FetchType.EAGER)
	private Client client;
	@Column(name="total_price")
	private double totalPrice;

	public Integer getControlNumber() {
		return controlNumber;
	}

	public void setControlNumber(Integer controlNumber) {
		this.controlNumber = controlNumber;
	}

	public Date getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(Date dateRegister) {
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
	
	@Override
	public String toString() {
		return "controlNumber: " + getControlNumber();
	}

}
