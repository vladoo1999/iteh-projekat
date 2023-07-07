//package com.example.fon_classroommanagment.Models.Email;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//public class EmailSender {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//
//    public void sendEmail(Email mail) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(mail.getTo());
//        msg.setFrom(mail.getFrom());
//        msg.setSubject(mail.getSubject());
//        msg.setText(mail.getContent());
//        javaMailSender.send(msg);
//    }
//    public void sendEmailWithAttachment(Email mail) throws MessagingException, IOException {
//        MimeMessage msg = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//
//        helper.setTo(mail.getTo());
//        helper.setFrom(mail.getFrom());
//        helper.setSubject(mail.getSubject());
//        helper.setText(mail.getContent());
//        helper.addAttachment("Google Photo", new ClassPathResource("img.png"));
//        javaMailSender.send(msg);
//    }
//
//
//
//    public void sendHtmlMessage(Email email) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//        Context context = new Context();
//        context.setVariables(email.getVariables());
//        helper.setFrom(email.getFrom());
//        helper.setTo(email.getTo());
//        helper.setSubject(email.getSubject());
//        String html = templateEngine.process(email.getTemplate(), context);
//        helper.setText(html, true);
//        javaMailSender.send(message);
//    }
//}
