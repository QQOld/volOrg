package volorg.view_models;

import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import volorg.models.Chat;
import volorg.models.Operation;
import volorg.models.Role;
import volorg.models.SearchRequest;
import volorg.models.User;
import volorg.models.VolRequest;

@Data
public class RegistrationForm {
	
	@NotNull(message="Введите email")
	@Size(min=6, message="Введите email")
	private String email;
	
	@NotNull(message="Введите имя")
	@Size(min=2, message="Введите имя")
	private String name;
	
	@NotNull(message="Введите фамилию")
	@Size(min=2, message="Введите фамилию")
	private String surName;
	
	@NotNull(message="Введите пароль")
	@Size(min=8, message="Введите пароль(как минимум 8 символов)")
	private String password;
	
	public User toUser(PasswordEncoder passwordEncoder) {
	    return new User(email, name, surName, passwordEncoder.encode(password));
	}
}
