package com.example.ordermanagement.order.DTO;

public record CheckoutDTO(
    Double subTotal,
    Double shipping,
    Double total
) 
{}
