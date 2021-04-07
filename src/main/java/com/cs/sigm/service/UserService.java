package com.cs.sigm.service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.domain.User;
import com.cs.sigm.domain.UserLog;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.exception.CmsMessagingException;
import com.cs.sigm.repository.UserLogRepository;
import com.cs.sigm.repository.UserRepository;
import com.cs.sigm.security.mail.MailMessage;
import com.cs.sigm.security.mail.MailService;
import com.cs.sigm.security.utils.KeyGenerator;
import com.cs.sigm.security.utils.PasswordValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private CmsConfig config;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private KeyGenerator generator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordValidator passwordValidator;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserLogRepository logRepository;
	
	public Optional<User> findSingle(Long id) {
		return repository.findById(id);
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User save(User request, Operation operation, Long idOperator) {
		if (request.getId() != null) {
			log.info(" >> Updating an existing user...");
			// TODO: translate the error message
			final User curUser = repository.findById(request.getId()).orElseThrow(() -> new CmsEntryNotFoundException("Tried to update an User with an invalid ID."));
			request.setUsername(curUser.getUsername());
			request.setPassword(curUser.getPassword());
			request.setEmail(curUser.getEmail());
		} else {
			log.info(" >> Creating a new user...");
			passwordValidator.validate(request.getPassword());
			request.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		log.info(" >> Adding a validation key if not validated...");
		if (!request.getValidated()) {
			request.setValidationKey(generator.getRandomKey());
		}
		final User result = repository.save(request);
		logRepository.save(UserLog.builder().idOperation(operation.getId()).idOperator(idOperator).idUser(result.getId()).build());
		log.info(" >> User saved. Requesting validation...");
		// TODO: what is the best approach for error handling in REST APIs?
		try {
			this.sendValidationMessage(result);
		} catch (MessagingException e) {
			log.error("Failed to send a message: " + e.getMessage(), e);
		}
		// ----------------------------------------------------------------
		return result;
	}
	
	public void deleteSingle(Long id) {
		if (repository.findById(id).isEmpty()) {
			// TODO: translate the error message
			throw new CmsEntryNotFoundException("The requested entry does not exist, therefore, was not deleted.");
		}
		repository.deleteById(id);
	}
	
	public String getNameById(Long id) {
		return repository.getNameById(id);
	}
	
	public boolean requestPwreset(String email) {
		final User user = repository.findByEmail(email).orElse(null);
		if (user == null) {
			log.warn("Tried to reset a password to an non-exiting user.");
			return false;
		}
		log.info("User found! Generating password reset flow and sending via email.");
		try {
			this.sendResetMessage(user);
			return true;
		} catch (MessagingException e) {
			throw new CmsMessagingException("Failed to send a password reset message", e);
		}
	}
	
	public boolean validate(Long id, String key) {
		final User user = repository.findById(id).orElse(null);
		if (user == null) {
			log.warn("Tried to validate an non-existing user!");
			return false;
		}
		log.info("user found. Setting status to validated.");
		user.setValidated(true);
		repository.save(user);
		return true;
	}
	
	private void sendResetMessage(User user) throws MessagingException {
		log.info("Sending the optin mail from {} to {}.", config.getMailFrom(), user.getEmail());
		//@formatter:off
		final StringBuilder msg = new StringBuilder(4096);
		// TODO: create a valid email message, generate an unique key and set it as parameter for the page
		msg.append("<p>To reset your password, click on the link below: </p>")
				.append("<p><a href='").append(config.getAccountsBaseURL())
				.append("/reset/").append(user.getEmail())
				.append("'>click here</a></p>");
		
		mailService.sendMail(
			MailMessage.builder()
				.addressFrom(config.getMailFrom())
				.addressTo(user.getEmail())
				.message(msg.toString())
				.subject("Please confirm your email")
			.build()
		);
		//@formatter:on
	}
	
	private void sendValidationMessage(User user) throws MessagingException {
		log.info("Sending the optin mail from {} to {}.", config.getMailFrom(), user.getEmail());
		//@formatter:off
		final StringBuilder msg = new StringBuilder(4096);
		msg.append("<p>Click on the link below: </p>")
			.append("<p><a href='").append(config.getAccountsBaseURL())
			.append("/validate/").append(user.getId())
			.append("/").append(user.getValidationKey())
			.append("'>validate!</a></p>");
		
		mailService.sendMail(
			MailMessage.builder()
				.addressFrom(config.getMailFrom())
				.addressTo(user.getEmail())
				.message(msg.toString())
				.subject("Please confirm your email")
			.build()
		);
		//@formatter:on
	}
	
}
