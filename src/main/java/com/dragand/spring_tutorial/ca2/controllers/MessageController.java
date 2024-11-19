package com.dragand.spring_tutorial.ca2.controllers;

import com.dragand.spring_tutorial.ca2.model.Message;
import com.dragand.spring_tutorial.ca2.model.User;
import com.dragand.spring_tutorial.ca2.persistence.MessageDao;
import com.dragand.spring_tutorial.ca2.persistence.MessageDaoImpl;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

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
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null){
            throw new RuntimeException("Not authorized!");
        }

        MessageDao messageDao = new MessageDaoImpl("database.properties");

        ArrayList<Message> messages = messageDao.getReceivedMessagesBySubjectOrBody(user.getUsername(), query);
        model.addAttribute("messages", messages);

        log.debug("Messages recived by " + user.getUsername() + messages.toString());
        return "messages";
    }

}
