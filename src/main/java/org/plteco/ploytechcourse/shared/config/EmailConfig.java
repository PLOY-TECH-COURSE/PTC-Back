package org.plteco.ploytechcourse.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 이메일 설정을 위한 구성 클래스입니다.
 * SMTP 서버 설정을 Spring Mail Sender에 전달하여 이메일 전송 기능을 설정합니다.
 */
@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;

    /**
     * JavaMailSender 빈을 생성하고 반환합니다.
     * 이메일 서버 설정을 기반으로 이메일 전송을 위한 JavaMailSender를 구성합니다.
     *
     * @return JavaMailSender 설정된 이메일 전송 객체
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setJavaMailProperties(getMailProperties());

        return mailSender;
    }

    /**
     * 이메일 전송에 필요한 추가 속성들을 반환합니다.
     *
     * @return 설정된 메일 속성(Properties 객체)
     */
    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttlsEnable);
        properties.put("mail.smtp.timeout", timeout);
        return properties;
    }
}
