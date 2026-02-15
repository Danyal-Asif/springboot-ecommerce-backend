package com.example.ordermanagement.order.DTO;

public class OrderDTO {
private Long customerID;
private Long productId;
private Integer quantity;

public Long getCustomerID() {
	return customerID;
}
public void setCustomerID(Long customerID) {
	this.customerID = customerID;
}
public Long getProductId() {
	return productId;
}
public void setProductId(Long productId) {
	this.productId = productId;
}
public Integer getQuantity() {
	return quantity;
}
public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}


}
