package kg.center.mayak.service;

import kg.center.mayak.model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

//    String sendMailWithAttachment(EmailDetails details) throws MessagingException;

}



