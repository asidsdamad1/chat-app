package org.asi.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asi.dtomodels.UserDTO;
import org.asi.mailservice.service.SendMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements SendMailService {

    private final JavaMailSender sender;
    private final TemplateEngine templateEngine;

    @Value("${mail.links.baseUrl:none}")
    private String baseMailProcessingUrl;

    @Value("${mail.links.activation-resource-path:}")
    private String userActivationResourcePath;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        var mimeMessage = this.sender.createMimeMessage();

        try {
            var mimeHelper = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            mimeHelper.setTo(to);
            mimeHelper.setSubject(subject);
            mimeHelper.setText(content, isHtml);

            sender.send(mimeMessage);
        } catch (MailException | jakarta.mail.MessagingException e) {
            log.warn("Email wasn't send to user {}", to, e);
        }
    }


    @Async
    @Override
    public void sendActivationEmail(UserDTO user) {

        var userEmail = user.getEmail();
        if (userEmail != null) {
            log.debug("Sending email template to {}", userEmail);

            var activationLink = baseMailProcessingUrl + userActivationResourcePath + user.getActivationKey();
            var context = new Context();
            context.setVariable("user", user);
            context.setVariable("activationUrl", activationLink);

            var subject = "Activate your account";
            var content = templateEngine.process("mail/activationMail", context);
            sendEmail(userEmail, subject, content, false, true);
        }

    }


}
