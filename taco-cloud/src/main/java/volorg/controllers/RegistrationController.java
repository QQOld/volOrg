package volorg.controllers;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import volorg.models.SearchRequest;
import volorg.models.User;
import volorg.models.VolRequest;
import volorg.repositories.RoleRepository;
import volorg.repositories.UserRepository;
import volorg.security.SecurityConfig;
import volorg.view_models.RegistrationForm;
 
@Controller
@RequestMapping("/register")
public class RegistrationController {
  
  private UserRepository userRepo;
  private RoleRepository roleRepo;
  private PasswordEncoder passwordEncoder;
 
  @Autowired
  public RegistrationController(
      UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.roleRepo = roleRepo;
  }
  
  @GetMapping
  public String registerForm(@ModelAttribute RegistrationForm registrationForm) {
    return "registration";
  }
  
  @PostMapping
  public String processRegistration(@Valid RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
	if (bindingResult.hasErrors()) {
		return "registration";
	}
	
	User user = userRepo.findByEmail(registrationForm.getEmail());
	if (user != null) {
		model.addAttribute("usernameError", "Пользователь с такой почтой уже существует.");
    return "registration";
	}
	
	User newUser = registrationForm.toUser(passwordEncoder);
	newUser.getRoles().add(roleRepo.findByName("User"));
  userRepo.save(newUser);
  return "redirect:/login";
  }
 
}