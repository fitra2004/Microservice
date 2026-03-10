package com.fitra.produk.service;

import com.fitra.produk.model.Order;
import com.fitra.produk.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        if (order.getTotal() == null && order.getHarga() != null && order.getJumlah() != null) {
            order.setTotal(order.getHarga() * order.getJumlah());
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByPelanggan(Long idPelanggan) {
        return orderRepository.findByPelangganId(idPelanggan);
    }
}
