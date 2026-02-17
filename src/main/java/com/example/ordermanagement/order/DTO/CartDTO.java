package com.example.ordermanagement.order.DTO;

public record CartDTO(
    Long id,
    String productName,
    double price,
    int quantity,
    double totalPrice
) {}
