package tul.swiercz.thesis.bookmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${application.frontend.url}")
    private String frontUrl;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendRegisterConfirmation(String recipient, String code) {
        String confirmationUrl = frontUrl + "/confirm/" + code;
        SimpleMailMessage message = getDefaultMessage(recipient);
        message.setSubject("Confirmation email");
        message.setText("To confirm your email, please click link below\n" + confirmationUrl);
        javaMailSender.send(message);
    }

    private SimpleMailMessage getDefaultMessage(String recipient) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bookmind.info@gmail.com");
        message.setTo(recipient);
        return message;
    }
}
