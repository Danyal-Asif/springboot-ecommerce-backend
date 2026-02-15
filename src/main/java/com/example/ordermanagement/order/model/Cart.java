package com.example.ordermanagement.order.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cart {
    @Id
    private Long id;
    private String pName;
    private double price;
    private int quantity;
    private double totalPrice;

    
    public Cart(Long id,String pName, double price, int quantity, double total) {
        this.id=id;
        this.pName = pName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = total;
    }

    

    public String getpName() {
        return pName;
    }
    public void setpName(String pName) {
        this.pName = pName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return BigDecimal.valueOf(totalPrice).setScale(2,RoundingMode.HALF_UP).doubleValue();
    }
    public void setTotalPrice(double total) {
        this.totalPrice = total;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

   @Override
public String toString() {
    return "Cart{" +
            "id=" + id +
            ", name='" + pName + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            ", totalPrice=" + totalPrice +
            '}';
}

    

}
