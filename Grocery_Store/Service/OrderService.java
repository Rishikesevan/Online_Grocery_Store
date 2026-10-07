package com.project.Grocery_Store.Service;

import com.project.Grocery_Store.Entity.Order;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Entity.User;
import com.project.Grocery_Store.Repository.OrderRepository;
import com.project.Grocery_Store.Repository.ProductRepository;
import com.project.Grocery_Store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // COD ORDER
    public Order saveCODOrder(Order order, Long productId, Long userId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);
        order.setProducts(List.of(product));
        order.setPaymentMode("COD");
        order.setPaymentStatus("PENDING");

        return orderRepository.save(order);
    }

    // ONLINE PAYMENT ORDER
    public Order saveOnlineOrder(
            Order order,
            Long productId,
            Long userId,
            String razorpayOrderId,
            String razorpayPaymentId
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);
        order.setProducts(List.of(product));
        order.setRazorpayOrderId(razorpayOrderId);
        order.setRazorpayPaymentId(razorpayPaymentId);
        order.setPaymentMode("ONLINE");
        order.setPaymentStatus("PAID");

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
