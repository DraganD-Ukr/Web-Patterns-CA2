package com.dragand.spring_tutorial.ca2.controllers;

import com.dragand.spring_tutorial.ca2.model.Message;
import com.dragand.spring_tutorial.ca2.model.User;
import com.dragand.spring_tutorial.ca2.persistence.MessageDao;
import com.dragand.spring_tutorial.ca2.persistence.MessageDaoImpl;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashSet;

@Controller
@Slf4j
public class MessageController {

    @GetMapping("/getMessagesFromQuery{query}")
    public String getMessagesFromQuery(
            @PathVariable String query,
            Model model,
            HttpSession session
    ){
//        Check if user logged in
        User user = (User)session.getAttribute("loggedInUser");
        authorize(user);

        MessageDao messageDao = new MessageDaoImpl("database.properties");

        HashSet<Message> messages = messageDao.getReceivedMessagesBySubjectOrBody(user.getUsername(), query);
        model.addAttribute("messages", messages);

        log.info("Messages received by " + user.getUsername() + messages.toString());
        return "messages";
    }

    @GetMapping("/viewAllMessages")
    public String viewAllMessages(
            Model model,
            HttpSession session
    ){
        User user = (User) session.getAttribute("loggedInUser");
        authorize(user);

        MessageDao messageDao = new MessageDaoImpl("database.properties");
        ArrayList<Message> allMessages = messageDao.getReceivedMessagesForUser(user.getUsername());
        model.addAttribute("allMessages", allMessages);

        log.info("Getting all messages for user " + session.getAttribute("loggedInUser"));
        log.debug(allMessages.toString());
        return "userMessages";
    }

    private boolean authorize(User user){
        if (user == null){
            throw new RuntimeException("Not authorized!");
        }
        return true;
    }


}
