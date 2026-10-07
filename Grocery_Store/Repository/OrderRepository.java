package com.project.Grocery_Store.Repository;

import com.project.Grocery_Store.Entity.Order;
import com.project.Grocery_Store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{

    List<Order> findByUser(User user);

    List<Order> findByUserId(Long userId);

}
