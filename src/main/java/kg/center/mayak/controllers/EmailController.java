package kg.center.mayak.controllers;

import kg.center.mayak.model.EmailDetails;
import kg.center.mayak.service.EmailService;
import kg.center.mayak.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;


@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details) throws MessagingException {
        String status = emailService.sendSimpleMail(details);
        return status;
    }

//    @PostMapping("/sendMailWithAttachment")
//    public String sendMailWithAttachment(@RequestBody EmailDetails details) throws MessagingException {
//        String status = emailService.sendMailWithAttachment(details);
//        return status;
//    }
//

}