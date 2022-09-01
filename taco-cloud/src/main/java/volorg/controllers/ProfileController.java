package volorg.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import volorg.models.User;
import volorg.repositories.UserRepository;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	 	private UserRepository userRepo;
	 
	  @Autowired
	  public ProfileController(
	    UserRepository userRepo, PasswordEncoder passwordEncoder) {
	    this.userRepo = userRepo;
	  }
	  
	  @GetMapping
	  public String getProfile(@AuthenticationPrincipal User user, Model model) {
	  	User curUser = userRepo.findById(user.getId()).orElse(null);
	  	model.addAttribute("user", curUser);
	    return "editProfile";
	  }
	  
	  @PostMapping
	  public String editProfile(@Valid User user, BindingResult bindingResult, @AuthenticationPrincipal User curUser) {
			if (bindingResult.hasErrors()) {
				return "editProfile";
			}
			curUser.setEmail(user.getEmail());
			curUser.setName(user.getName());
			curUser.setSurName(user.getSurName());
		  userRepo.save(curUser);
		  return "redirect:/index";
	  }
}
