package com.chs.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class MailComponents {
    
    private final JavaMailSender javaMailSender;
    
    public void sendMailTest() {
    
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("cwd_2023@naver.com");
        msg.setSubject("안녕하세요. ReserveTableApp 입니다.");
        msg.setText(" 안녕하세요. ReserveTableApp 입니다. 반갑습니다. ");
        
        javaMailSender.send(msg);
    }
    
    public boolean sendMail(String mail, String subject, String text) {
        
        boolean result = false;
        
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };
        
        try {
            javaMailSender.send(msg);
            System.out.println("mail is send");
            result = true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return result;
    }
    

}
