package volorg.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import volorg.models.User;
import volorg.models.VolRequest;
import volorg.repositories.UserRepository;
import volorg.repositories.VolRequestRepository;

@Controller
@RequestMapping("/volReq")
public class VolReqCreateController {
	
private final VolRequestRepository volReqRepo;
private final UserRepository userRepo;
	
	@Autowired
	public VolReqCreateController(
			VolRequestRepository volReqRepo, UserRepository userRepo) {
	  this.volReqRepo = volReqRepo;
	  this.userRepo = userRepo;
	}
	
	@ModelAttribute(name = "volReq")
	public VolRequest addVolRequest() {
	  return new VolRequest();
	}
	
	@GetMapping
	public String volReqForm() {
	  return "volReq";
	}
	
	@PostMapping
	public String addVolReq(@Valid VolRequest volReq, Errors errors) {
	  if (errors.hasErrors()) {
	    return "volReq";
	  }
	  User user = new User();
	  user.setName("admin");
	  user.setSurName("admin");
	  userRepo.save(user);
	  
	  volReq.setStatus("Ожидание");
	  volReq.setUser(user);
	  volReqRepo.save(volReq);
	  return "redirect:/index";
	}

}
