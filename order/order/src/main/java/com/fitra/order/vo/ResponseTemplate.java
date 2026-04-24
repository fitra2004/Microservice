package com.fitra.order.vo;

import com.fitra.order.model.Order;

import lombok.Data;

@Data
public class ResponseTemplate {
    private Order order;
    private Produk produk;
}