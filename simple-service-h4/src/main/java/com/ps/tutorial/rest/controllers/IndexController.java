package com.ps.tutorial.rest.controllers;

import com.ps.tutorial.rest.data.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping("/")
    public String documentation() {
        return "Try '/users' to get a list of users";
    }

    @RequestMapping("/users")
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> users() {
        Session session = sessionFactory.getCurrentSession();
        createDefaultUsers(session);
        return (List<User>) session.createQuery("from com.ps.tutorial.rest.data.User").list();
    }

    private void createDefaultUsers(Session session) {
        session.saveOrUpdate(new User("Randy Baiad"));
        session.saveOrUpdate(new User("Bobby Kumar"));
        session.saveOrUpdate(new User("Random " + Math.random()));
    }
}
