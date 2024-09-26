package com.barber.appointment.Model.Observer;

import com.barber.appointment.EmailService;
import com.barber.appointment.Interface.Observer;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class EmailObserver implements Observer {

    private String userEmail;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public void update(String message) {
        EmailService.getInstance().sendEmail(userEmail, "Appointment Notification", message);
    }
}
