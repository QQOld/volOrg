package volorg.controllers;

import java.sql.SQLException;
import java.util.Date;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import volorg.models.User;
import volorg.models.VolRequest;
import volorg.models_log.VolReqLog;
import volorg.repositories.UserRepository;
import volorg.repositories.VolRequestRepository;
import volorg.repositories_log.VolReqLogRepository;

@Controller
@RequestMapping("/volReq")
public class VolReqCreateController {
		
	private final VolRequestRepository volReqRepo;
	private final UserRepository userRepo;
	private final VolReqLogRepository logRepo;
	
	@Autowired
	public VolReqCreateController(VolRequestRepository volReqRepo, UserRepository userRepo, VolReqLogRepository logRepo) {
	  this.volReqRepo = volReqRepo;
	  this.userRepo = userRepo;
		this.logRepo = logRepo;
	}
	
	@GetMapping
	public String volReqForm(@ModelAttribute VolRequest volRequest) {
	  return "volReq";
	}
	
	@Transactional
	@PostMapping
	public String addVolReq(@Valid VolRequest volRequest, BindingResult bindingResult, @AuthenticationPrincipal User curUser, Model model) {
	  if (bindingResult.hasErrors()) {
	    return "volReq";
	  }
	  
    	VolReqLog log = new VolReqLog();
  	  log.setName(curUser.getName());
  	  log.setEmail(curUser.getEmail());
  	  log.setLivArea(volRequest.getLivArea());
  	  log.setPhone(volRequest.getPhone());
  	  log.setTimestamp(new Date());
  	  logRepo.save(log);
  	  
  	  if (userRepo.findByEmail(curUser.getEmail()).getVolRequest() != null) {
//  	  	throw new RuntimeException();
//  			model.addAttribute("usernameError", "Вы уже подали заявку в волнтёры.");
//  	    return "volReq";
  		}
  	  
  	 
  	  volRequest.setUser(curUser);
  	  volRequest.setStatus("Ожидание");
  	  
  	  volReqRepo.save(volRequest);
	  
	  return "redirect:/";
	}

}
