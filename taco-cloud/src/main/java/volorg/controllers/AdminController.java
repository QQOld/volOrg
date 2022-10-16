package volorg.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volorg.models.Chat;
import volorg.models.Comment;
import volorg.models.Operation;
import volorg.models.Role;
import volorg.models.SearchRequest;
import volorg.models.User;
import volorg.models.VolRequest;
import volorg.repositories.ChatRepository;
import volorg.repositories.CommentRepository;
import volorg.repositories.OperationRepository;
import volorg.repositories.RoleRepository;
import volorg.repositories.SearchRequestRepository;
import volorg.repositories.UserRepository;
import volorg.repositories.VolRequestRepository;
import volorg.view_models.EditForm;
import volorg.view_models.RegistrationForm;

@Controller
@RequestMapping("/adminPanel")
public class AdminController {
	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private VolRequestRepository volReqRepo;
	private SearchRequestRepository searchReqRepo;
	private OperationRepository operationRepo;
	private ChatRepository chatRepo;
	private CommentRepository commentRepo;
	private PasswordEncoder passwordEncoder;
	 
  @Autowired
  public AdminController(UserRepository userRepo, RoleRepository roleRepo, VolRequestRepository volReqRepo, SearchRequestRepository searchReqRepo, OperationRepository operationRepo, ChatRepository chatRepo, CommentRepository commentRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.volReqRepo = volReqRepo;
    this.searchReqRepo = searchReqRepo;
    this.roleRepo = roleRepo;
    this.operationRepo = operationRepo;
    this.chatRepo = chatRepo;
    this.commentRepo = commentRepo;
    this.passwordEncoder = passwordEncoder;
  }
  
  @GetMapping
  public String getAdminPanel(Model model, @RequestParam("page") Optional<Integer> page) {
  	int currentPage = page.orElse(1);
  	Pageable pageable = PageRequest.of(currentPage - 1, 5, Sort.by("name").ascending());
  	Page<User> users = userRepo.findAll(pageable);
  	model.addAttribute("users", users);
    return "adminPanel";
  }
  
  @GetMapping("/createUser")
  public String createUserForm(@ModelAttribute RegistrationForm registrationForm) {
    return "createUser";
  }
  
  @PostMapping("/createUser")
  public String createUser(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
  	if (bindingResult.hasErrors()) {
  		return "createUser";
  	}
  	
  	User user = registrationForm.toUser(passwordEncoder);
  	user.getRoles().add(roleRepo.findByName("User"));
    userRepo.save(user);
    return "redirect:/adminPanel";
  }
  
  @GetMapping("/edit/{id}")
  public String editUserForm(@PathVariable long id, Model model) {
  	User user = userRepo.findById(id).orElse(null);
  	if(user != null) {
  		EditForm editForm = new EditForm(user.getId(), user.getEmail(), user.getName(), user.getSurName());
  		model.addAttribute("editForm", editForm);
  		return "editUser";
  	}
    return "redirect:/adminPanel";
  }
  
  @PostMapping("/edit/{id}")
  public String editUser(@Valid EditForm editForm, BindingResult bindingResult) {
  	if (bindingResult.hasErrors()) {
  		return "editUser";
  	};
  	User user = userRepo.findById(editForm.getId()).orElse(null);
  	if(user != null) {
  		user.setEmail(editForm.getEmail());
  		user.setName(editForm.getName());
  		user.setSurName(editForm.getSurName());
  		userRepo.save(user);
  	}
    return "redirect:/adminPanel";
  }
  
  @PostMapping("/delete/{id}")
  public String deleteUser(@PathVariable long id) {
  	User user = userRepo.findById(id).orElse(null);
  	if(user != null) {
  		user.getOperations().clear();
  		user.getRoles().clear();
  		userRepo.deleteById(id);
  	}
    return "redirect:/adminPanel";
  }
  
  
  //Vol Requests
  
  @GetMapping("/volRequests")
  public String getVolRequests(Model model, @RequestParam("page") Optional<Integer> page) {
  	int currentPage = page.orElse(1);
  	Pageable pageable = PageRequest.of(currentPage - 1, 2);
  	Page<VolRequest> volRequests = volReqRepo.findByStatus("Ожидание", pageable);
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
  	return "redirect:/adminPanel";
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
  		volRequest.getUser().setVolRequest(null);
  		volReqRepo.deleteById(id);
  	}
  	return "redirect:/adminPanel/volRequests";
  }
  
  
  //Search Requests
  
  @GetMapping("/searchRequests")
  public String getSearchRequests(Model model, @RequestParam("page") Optional<Integer> page) {
  	int currentPage = page.orElse(1);
  	Pageable pageable = PageRequest.of(currentPage - 1, 2);
  	Page<SearchRequest> searchRequests = searchReqRepo.findByStatus("Ожидание", pageable);
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
  		
  		Operation operation = new Operation();
  		operation.setStatus("Ожидание");
  		operation.setSearchRequest(searchRequest);
  		searchRequest.setOperation(operation);
  		
  		Chat chat = new Chat();
  		chat.setOperation(searchRequest.getOperation());
  		chat.getUsers().add(curUser);
  		searchRequest.getOperation().setChat(chat);
  		
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
  
  @GetMapping("/operations/{id}/start")
  public String startOperation(@PathVariable long id) {
  	Operation operation = operationRepo.findById(id).orElse(null);
  	if(operation != null) {
  		operation.setStatus("Активная");
  		operationRepo.save(operation);
  	}
  	return "redirect:/adminPanel/operations";
  }
  
  @GetMapping("/operations/{id}/stop")
  public String stopOperation(@PathVariable long id) {
  	Operation operation = operationRepo.findById(id).orElse(null);
  	if(operation != null) {
  		long chatId = operation.getSearchRequest().getOperation().getChat().getId();
  		operation.getSearchRequest().getOperation().setChat(null);
  		chatRepo.deleteById(chatId);
  		operation.setStatus("Завершённая");
  		operationRepo.save(operation);
  	}
  	return "redirect:/adminPanel/operations";
  }
  
  @PostMapping("/operations/{id}/addComment")
  public String addComment(@Valid Comment comment, BindingResult bindingResult, @PathVariable long id) {
  	Operation operation = operationRepo.findById(id).orElse(null);
  	if(operation != null) {
  		comment.setOperation(operation);
  		operation.getComments().add(comment);
  		operationRepo.save(operation);
  	}
  	return "redirect:/operations/{id}";
  }
  
  @GetMapping("/operations/{opId}/deleteComment/{id}")
  public String deleteComment(@PathVariable long id) {
  	Comment comment = commentRepo.findById(id).orElse(null);
  	if(comment != null) {
  		commentRepo.deleteById(id);
  	}
  	return "redirect:/operations/{opId}";
  }
  
}
