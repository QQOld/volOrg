package volorg.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import volorg.models.Chat;
import volorg.models.Operation;
import volorg.models.Role;
import volorg.models.SearchRequest;
import volorg.models.User;
import volorg.models.VolRequest;
import volorg.repositories.OperationRepository;
import volorg.repositories.RoleRepository;
import volorg.repositories.SearchRequestRepository;
import volorg.repositories.UserRepository;
import volorg.repositories.VolRequestRepository;

@Controller
@RequestMapping("/adminPanel")
public class AdminController {
	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private VolRequestRepository volReqRepo;
	private SearchRequestRepository searchReqRepo;
	private OperationRepository operationRepo;
	 
  @Autowired
  public AdminController(UserRepository userRepo, RoleRepository roleRepo, VolRequestRepository volReqRepo, SearchRequestRepository searchReqRepo, OperationRepository operationRepo) {
    this.userRepo = userRepo;
    this.volReqRepo = volReqRepo;
    this.searchReqRepo = searchReqRepo;
    this.roleRepo = roleRepo;
    this.operationRepo = operationRepo;
  }
  
  @GetMapping
  public String getAdminPanel(Model model) {
  	Iterable<User> users = userRepo.findAll();
  	model.addAttribute("users", users);
    return "adminPanel";
  }
  
  
  //Vol Requests
  
  @GetMapping("/volRequests")
  public String getVolRequests(Model model) {
  	List<VolRequest> volRequests = volReqRepo.findByStatus("Ожидание");
  	model.addAttribute("volRequests", volRequests);
    return "volRequests";
  }
  
  @GetMapping("/volRequests/{id}")
  public String responseVolReq(@PathVariable long id, Model model) {
  	VolRequest volRequest = volReqRepo.findById(id).orElse(null);
  	if(volRequest != null) {
  		model.addAttribute("volRequest", volRequest);
  		return "volResponse";
  	}
  	return "adminPanel";
  }
  
  @GetMapping("/volRequests/{id}/accept")
  public String acceptVolReq(@PathVariable long id) {
  	VolRequest volRequest = volReqRepo.findById(id).orElse(null);
  	if(volRequest != null) {
  		volRequest.setStatus("Принята");
  		volRequest.getUser().getRoles().add(roleRepo.findByName("Volunteer"));
  		volReqRepo.save(volRequest);
  	}
  	return "redirect:/adminPanel/volRequests";
  }
  
  @GetMapping("/volRequests/{id}/decline")
  public String declineVolReq(@PathVariable long id) {
  	VolRequest volRequest = volReqRepo.findById(id).orElse(null);
  	if(volRequest != null) {
  		volReqRepo.deleteById(id);
  	}
  	return "redirect:/adminPanel/volRequests";
  }
  
  
  //Search Requests
  
  @GetMapping("/searchRequests")
  public String getSearchRequests(Model model) {
  	List<SearchRequest> searchRequests = searchReqRepo.findByStatus("Ожидание");
  	model.addAttribute("searchRequests", searchRequests);
    return "searchRequests";
  }
  
  @GetMapping("/searchRequests/{id}")
  public String responseSearchReq(@PathVariable long id, Model model) {
  	SearchRequest searchRequest = searchReqRepo.findById(id).orElse(null);
  	if(searchRequest != null) {
  		model.addAttribute("searchRequest", searchRequest);
  		return "searchResponse";
  	}
  	return "adminPanel";
  }
  
  @GetMapping("/searchRequests/{id}/accept")
  public String acceptSearchReq(@PathVariable long id,  @AuthenticationPrincipal User curUser) {
  	SearchRequest searchRequest = searchReqRepo.findById(id).orElse(null);
  	if(searchRequest != null) {
  		searchRequest.setStatus("Принята");
  		Chat chat = new Chat();
  		chat.setSearchRequest(searchRequest);
  		chat.getUsers().add(curUser);
  		searchRequest.setChat(chat);
  		
  		Operation operation = new Operation();
  		operation.setStatus("Ожидание");
  		operation.setSearchRequest(searchRequest);
  		searchRequest.setOperation(operation);
  		
  		searchReqRepo.save(searchRequest);
  	}
  	return "redirect:/adminPanel/searchRequests";
  }
  
  @GetMapping("/searchRequests/{id}/decline")
  public String declineSearchReq(@PathVariable long id) {
  	SearchRequest searchRequest = searchReqRepo.findById(id).orElse(null);
  	if(searchRequest != null) {
  		searchReqRepo.deleteById(id);
  	}
  	return "redirect:/adminPanel/searchRequests";
  }
  
  
  // Operations
  
  @GetMapping("/operations")
  public String getOperations(Model model) {
  	List<Operation> operations = (List<Operation>) operationRepo.findAll();
  	List<Operation> waitOperations = new ArrayList<Operation>();
  	List<Operation> activeOperations = new ArrayList<Operation>();
  	List<Operation> completeOperations = new ArrayList<Operation>();
  	for(var op : operations) {
  		switch(op.getStatus()) {
	  		case "Ожидание": 
	  			waitOperations.add(op);
	        break;
		    case "Активная": 
		    	activeOperations.add(op);
	        break;
		    case "Завершённая": 
		    	completeOperations.add(op);
	        break;
  		}
  	}
  	model.addAttribute("operations", operations); 
  	model.addAttribute("waitOperations", waitOperations);
  	model.addAttribute("activeOperations", activeOperations);
  	model.addAttribute("completeOperations", completeOperations);

  	return "operations";
  }
  
  @GetMapping("/searchRequests/{id}/decline")
  public String c(@PathVariable long id) {
  	SearchRequest searchRequest = searchReqRepo.findById(id).orElse(null);
  	if(searchRequest != null) {
  		searchReqRepo.deleteById(id);
  	}
  	return "redirect:/adminPanel/searchRequests";
  }
}
