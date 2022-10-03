package volorg.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	public VolReqCreateController(VolRequestRepository volReqRepo, UserRepository userRepo) {
	  this.volReqRepo = volReqRepo;
	  this.userRepo = userRepo;
	}
	
	@GetMapping
	public String volReqForm(@ModelAttribute VolRequest volRequest) {
	  return "volReq";
	}
	
	@PostMapping
	public String addVolReq(@Valid VolRequest volRequest, BindingResult bindingResult, @AuthenticationPrincipal User curUser) {
	  if (bindingResult.hasErrors()) {
	    return "volReq";
	  }
	 
	  volRequest.setUser(curUser);
	  volRequest.setStatus("Ожидание");
	  
	  volReqRepo.save(volRequest);
	  return "redirect:/";
	}

}
