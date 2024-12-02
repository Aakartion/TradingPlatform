package edu.miu.TradingPlatform.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendVerificationOtpEmail(String to, String OTP) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

      String subject = "Verify OTP";
      String text = "Your verification code is: " + OTP;

      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(text);
      mimeMessageHelper.setTo(to);
    } catch (MailException | MessagingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
