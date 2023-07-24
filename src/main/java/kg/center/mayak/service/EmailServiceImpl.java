package kg.center.mayak.service;

import javax.mail.internet.MimeMessage;

import jakarta.mail.MessagingException;
import kg.center.mayak.model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}") private String sender;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(details.getRecipient());

            // Including the phone number in the email body
            String emailBodyWithPhone = details.getMsgBody() + "\nPhone: " + details.getPhone();
            message.setText(emailBodyWithPhone);

            message.setSubject(details.getTitle());

            mailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}

//    public String sendMailWithAttachment(EmailDetails details) throws MessagingException {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom(sender);
//            helper.setTo(details.getRecipient());
//            helper.setText(details.getMsgBody());
//            helper.setSubject(details.getTitle());
//
//            FileSystemResource attachment = new FileSystemResource(details.getAttachmentPath());
//            helper.addAttachment(details.getAttachmentName(), attachment);
//
//            mailSender.send(message);
//            System.out.println("Mail with attachment sent...");
//            return "Mail With Attachment Sent Successfully...";
//        } catch (Exception e) {
//            return "Error while Sending Mail With Attachment ";
//        }
//    }

