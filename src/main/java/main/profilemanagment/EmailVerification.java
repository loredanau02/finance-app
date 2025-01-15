package main.profilemanagment;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailVerification {
    private static final String FROM_EMAIL = "thisisjustatestemailformyuse@gmail.com";
    private static final String PASSWORD = "udqu yiyt xytk emtz";

    public static void sendVerificationCode(String toEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Finance App - Email Verification Code");
            message.setText("Hello,\n\nYour verification code is: " + verificationCode + 
                          "\n\nPlease enter this code in the application to verify your email." +
                          "\n\nBest regards,\nFinance App Team");

            Transport.send(message);
            System.out.println("Verification code sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.out.println("Failed to send verification code: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean verifyEmail(Profile profile, String code) {
        if (profile.getVerificationCode().equals(code)) {
            profile.setEmailVerified(true);
            return true;
        }
        return false;
    }
}
