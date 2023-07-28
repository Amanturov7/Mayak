package kg.center.mayak.controllers;

import kg.center.mayak.model.EmailDetails;
import kg.center.mayak.service.EmailService;
import kg.center.mayak.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;


@RestController
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/api/sendMail")
    public ResponseEntity<?> sendMail(@RequestBody EmailDetails details) throws MessagingException {
        try {
        String status = emailService.sendSimpleMail(details);
        return ResponseEntity.ok("Mail Sent Successfully...");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while Sending Mail");
    }
    }

//    @PostMapping("/sendMailWithAttachment")
//    public String sendMailWithAttachment(@RequestBody EmailDetails details) throws MessagingException {
//        String status = emailService.sendMailWithAttachment(details);
//        return status;
//    }
//

}