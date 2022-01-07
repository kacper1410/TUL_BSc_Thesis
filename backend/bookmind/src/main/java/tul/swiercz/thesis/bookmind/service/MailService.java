package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendRegisterConfirmation(String recipient, String url) {
        SimpleMailMessage message = getDefaultMessage(recipient);
        message.setSubject("Confirmation email");
        message.setText("To confirm your email, please click link below\n" + url);
        javaMailSender.send(message);
    }

    private SimpleMailMessage getDefaultMessage(String recipient) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bookmind.info@gmail.com");
        message.setTo(recipient);
        return message;
    }
}
