package com.smart.service;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpSession;



@Component
public class SessionService {

   
	public void removeMessageFromSession() {
        try {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("msg");
            System.out.println("Message removed from session successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while removing message from session.");
        }
    }
}