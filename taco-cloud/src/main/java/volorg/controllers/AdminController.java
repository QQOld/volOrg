package volorg.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import volorg.models.User;
import volorg.models.VolRequest;
import volorg.repositories.UserRepository;
import volorg.repositories.VolRequestRepository;

@Controller
@RequestMapping("/adminPanel")
public class AdminController {
	private UserRepository userRepo;
	private VolRequestRepository volReqRepo;
	 
  @Autowired
  public AdminController(UserRepository userRepo, VolRequestRepository volReqRepo) {
    this.userRepo = userRepo;
    this.volReqRepo = volReqRepo;
  }
  
  @GetMapping
  public String getAdminPanel(Model model) {
  	Iterable<User> users = userRepo.findAll();
  	model.addAttribute("users", users);
    return "adminPanel";
  }
  
  @GetMapping("/volRequests")
  public String getVolRequests(Model model) {
  	List<VolRequest> volRequests = volReqRepo.findByStatus("Ожидание");
  	model.addAttribute("volRequests", volRequests);
  	volRequests.get(1).getUser().getSurName();
    return "volRequests";
  }
  
}
