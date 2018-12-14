package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value= "/users")
public class UserController {

    private List<User> userList = new ArrayList();

    UserController() {
        this.userList = buildUsers();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers(){
        return this.userList;
    }

    @RequestMapping(value= "/{id}", method =RequestMethod.GET)
    public User getUser(@PathVariable("id") Integer id) {
        return this.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User saveUser(@RequestBody User user) {
        Integer nextId =0;
        if(this.userList.size() != 0) {
            User lastUser = this.userList.stream().skip(this.userList.size() - 1).findFirst().orElse(null);
            nextId = lastUser.getId()+ 1;
        }

        user.setId(nextId);
        this.userList.add(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {
        User modifiedUser = this.userList.stream().filter(u -> u.getId() == user.getId()).findFirst().orElse(null);
        modifiedUser.setFirstName(user.getFirstName());
        modifiedUser.setLastName(user.getLastName());
        modifiedUser.setEmail(user.getEmail());
        return modifiedUser;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable Integer id) {
        User deleteUser = this.userList.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
        if (deleteUser != null) {
            this.userList.remove(deleteUser);
            return true;
        } else  {
            return false;
        }
    }

    List<User> buildUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User(1, "John", "Doe", "john@email.com");
        User user2 = new User(2, "Jon", "Smith", "smith@email.com");
        User user3 = new User(3, "Will", "Craig", "will@email.com");
        User user4 = new User(4, "Sam", "Lernorad", "sam@email.com");
        User user5 = new User(5, "Ross", "Doe", "ross@email.com");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        return users;

    }

}
