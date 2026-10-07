package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Order;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import com.project.Grocery_Store.Service.OrderService;
import com.razorpay.RazorpayClient;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    // BUY NOW PAGE
    @GetMapping("/api/viewBuyNow")
    public String viewBuyNow(@RequestParam Long productId, Model model) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        model.addAttribute("viewOrder", product);
        model.addAttribute("orderPlaced", new Order());
        model.addAttribute("razorpayKey", razorpayKey);

        return "BuyNow";
    }

    // CREATE RAZORPAY ORDER (ONLY FOR ONLINE)
    @PostMapping("/api/order/create")
    @ResponseBody
    public String createRazorpayOrder(@RequestParam int amount) {

        try {
            RazorpayClient client =
                    new RazorpayClient(razorpayKey, razorpaySecret);

            JSONObject options = new JSONObject();
            options.put("amount", amount * 100);
            options.put("currency", "INR");
            options.put("receipt", "txn_" + System.currentTimeMillis());
            System.out.println("razorpay");

            return client.orders.create(options).toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // CONFIRM ORDER (COD + ONLINE)
    @PostMapping("/api/order/confirm")
    public String confirmOrder(
            @ModelAttribute Order order,
            @RequestParam Long productId,
            @RequestParam(required = false) String razorpayOrderId,
            @RequestParam(required = false) String razorpayPaymentId,
            HttpSession session
    ) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Order savedOrder;

        // ✅ COD FLOW (NO RAZORPAY)
        if ("COD".equalsIgnoreCase(order.getPaymentMode())) {

            savedOrder = orderService.saveCODOrder(
                    order,
                    productId,
                    userId
            );

        } else {
            // ✅ ONLINE PAYMENT FLOW
            savedOrder = orderService.saveOnlineOrder(
                    order,
                    productId,
                    userId,
                    razorpayOrderId,
                    razorpayPaymentId
            );
        }
        System.out.println("saved order");

        return "redirect:/orderPlaced/details/" + savedOrder.getId();
    }

    // ORDER DETAILS
    @GetMapping("/orderPlaced/details/{id}")
    public String viewOrderDetails(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {

        Long userId = (Long) session.getAttribute("userId");

        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("allOrders", orderService.getAllOrdersByUser(userId));

        return "OrderPlaced";
    }

    // ALL ORDERS
    @GetMapping("/order")
    public String viewOrder(Model model, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        List<Order> orderList = orderService.getAllOrdersByUser(userId);
        model.addAttribute("allOrders", orderList);

        return "Orderview";
    }
}
