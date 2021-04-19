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
import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.exception.CmsAuthenticationException;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.exception.CmsMessagingUnavailableException;
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
			log.info(" Updating an existing user...");
			final User curUser = repository.findById(request.getId()).orElseThrow(() -> new CmsEntryNotFoundException("Tried to update an User with an invalid ID."));
			// attributes that cannot be updated from outside --------------------
			request.setApproved(curUser.getApproved());
			request.setBlocked(curUser.getBlocked());
			request.setEmail(curUser.getEmail());
			request.setFailedAttempts(curUser.getFailedAttempts());
			request.setUsername(curUser.getUsername());
			request.setValidated(curUser.getValidated());
			request.setValidationKey(curUser.getValidationKey());
			// -------------------------------------------------------------------
			request.setPassword(validPasswordChangeRequest(curUser, request) ? passwordEncoder.encode(request.getPassword()) : curUser.getPassword());
		} else {
			log.info(" Creating a new user...");
			validPasswordConfirmation(request.getPassword(), request.getPasswordConfirm());
			request.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		log.info(" Adding a validation key if not validated...");
		if (!request.getValidated()) {
			request.setValidationKey(generator.getRandomKey());
		}
		log.info(" Adding a Role if not setted...");
		if (request.getIdRole() == null) {
			request.setIdRole(Role.STANDARD.getId());
		}
		final User result = repository.save(request);
		logRepository.save(UserLog.builder().idOperation(operation.getId()).idOperator(idOperator).idUser(result.getId()).build());
		if (!result.getValidated()) {
			log.info(" >> User saved. Requesting validation...");
			try {
				this.sendValidationMessage(result);
			} catch (MessagingException e) {
				throw new CmsMessagingUnavailableException("Failed to send an account validation message", e);
			}
		}
		return result;
	}
	
	public void deleteSingle(Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new CmsEntryNotFoundException("The requested entry does not exist, therefore, it was not deleted.");
		}
		repository.deleteById(id);
	}
	
	public String getNameById(Long id) {
		return repository.getNameById(id);
	}
	
	public boolean requestPasswordReset(String email) {
		log.info("Trying to load an user with email: {}", email);
		final User user = repository.findByEmail(email).orElse(null);
		if (user == null) {
			log.warn("Tried to reset a password to an non-exiting user.");
			return false;
		}
		log.info("User found! Generating password reset flow and sending via email.");
		user.setPasswordResetKey(generator.getRandomKey());
		repository.save(user);
		try {
			this.sendResetMessage(user);
			return true;
		} catch (MessagingException e) {
			throw new CmsMessagingUnavailableException("Failed to send a password reset message", e);
		}
	}
	
	public void processPasswordReset(String email, String key, String password, String passwordConfirm) {
		log.info("Processing the password reset request...");
		final User user = this.repository.findByEmail(email).orElseThrow(() -> new CmsEntryNotFoundException("User not found."));
		if (!user.getPasswordResetKey().equals(key)) {
			throw new CmsAuthenticationException("Invalid password reset request.");
		}
		validPasswordConfirmation(password, passwordConfirm);
		log.info("Password change request valid! Updating...");
		user.setPassword(passwordEncoder.encode(password));
		user.setBlocked(Boolean.FALSE);
		this.repository.save(user);
		log.info("Password successfully reseted.");
	}
	
	public boolean validate(Long id, String key) {
		final User user = repository.findById(id).orElse(null);
		if (user == null) {
			log.warn("Tried to validate an non-existing user!");
			return false;
		}
		log.info("user found. Checking key.");
		if (!user.getValidationKey().equals(key)) {
			throw new CmsAuthenticationException("Invalid validation key received.");
		}
		user.setValidated(true);
		repository.save(user);
		return true;
	}
	
	public User checkAccountLock(String username) {
		final User checkUser = repository.findByEmail(username).orElseThrow(() -> new CmsAuthenticationException());
		log.info("User found. Current failed attempts: " + checkUser.getFailedAttempts().toString());
		if (checkUser.getFailedAttempts().intValue() >= config.getPwMaxIncorrectTries()) {
			log.warn(" >> Locking user account!!");
			checkUser.setBlocked(true);
		} else {
			log.info(" > Increasing the account failed attempts");
		}
		checkUser.increaseFailedAttepts();
		final User result = repository.save(checkUser);
		
		log.info(" > Current result attempts: " + result.getFailedAttempts().toString());
		
		return result;
		
	}
	
	private boolean validPasswordChangeRequest(User oldUser, User newUser) {
		if (newUser.getChangePassword()) {
			log.info("Password change requested. Analyzing...");
			if (!passwordEncoder.matches(newUser.getCurrentPassword(), oldUser.getPassword())) {
				throw new CmsAuthenticationException("Current password is invalid.");
			}
			validPasswordConfirmation(newUser.getPassword(), newUser.getPasswordConfirm());
			return true;
		}
		log.info("Password not changed.");
		return false;
	}
	
	private void validPasswordConfirmation(String password, String passwordConfirm) {
		if (password == null || password.isBlank() || passwordConfirm == null || passwordConfirm.isBlank()) {
			throw new CmsAuthenticationException("The new password must not be blank.");
		}
		if (!password.equals(passwordConfirm)) {
			throw new CmsAuthenticationException("The confirmation password do not match.");
		}
		passwordValidator.validate(password);
	}
	
	private void sendResetMessage(User user) throws MessagingException {
		log.info("Sending a password reset mail from {} to {}.", config.getMailFrom(), user.getEmail());
		//@formatter:off
		final StringBuilder msg = new StringBuilder(4096);
		// TODO: create a valid email message, generate an unique key and set it as parameter for the page
		msg.append("<p>To reset your password, click on the link below: </p>")
				.append("<p><a href='").append(config.getPwResetFormURL())
				.append("/").append(user.getEmail())
				.append("/").append(user.getPasswordResetKey())
				.append("'>click here</a></p>");
		
		mailService.sendMail(
			MailMessage.builder()
				.addressFrom(config.getMailFrom())
				.addressTo(user.getEmail())
				.message(msg.toString())
				.subject("Password reset")
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
