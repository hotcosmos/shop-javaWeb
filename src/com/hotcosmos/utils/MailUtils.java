package com.hotcosmos.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	private MailUtils() {
	}
	public static void sendMail(String email,String subject, String emailMsg)
			throws AddressException, MessagingException, IOException {
		
		//读取发送邮件方的信息（配置文件）
		ClassLoader classLoader = MailUtils.class.getClassLoader();
		InputStream inputStreamMail = classLoader.getResourceAsStream("mail.properties");
		Properties mailProps = new Properties();
		mailProps.load(inputStreamMail);
		
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", mailProps.getProperty("mailProtocol"));
		props.setProperty("mail.host", mailProps.getProperty("mailHost"));
		props.setProperty("mail.port", mailProps.getProperty("mailPort"));
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailProps.getProperty("mailAuth"), mailProps.getProperty("mailPassword"));
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(mailProps.getProperty("mailInternetAddress"))); // 设置发送者
		
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

		message.setSubject(subject);

		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送

		Transport.send(message);
	}
}
