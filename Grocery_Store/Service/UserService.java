package com.project.Grocery_Store.Service;

import com.project.Grocery_Store.Entity.User;
import com.project.Grocery_Store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        User user1 = new User();
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        return userRepository.save(user1);
    }

    public List<User> checkUser(String email){
        List<User> userList = userRepository.findAll();
        return userList;

    }

}
