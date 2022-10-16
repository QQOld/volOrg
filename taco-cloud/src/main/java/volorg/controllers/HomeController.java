package volorg.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volorg.models.Comment;
import volorg.models.Operation;
import volorg.models.User;
import volorg.repositories.OperationRepository;
import volorg.repositories.RoleRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final OperationRepository operationRepo;
	private final RoleRepository roleRepo;

	@Autowired
	public HomeController(
			OperationRepository operationRepo, RoleRepository roleRepo) {
	  this.operationRepo = operationRepo;
	  this.roleRepo = roleRepo;
	}
	
	@GetMapping
	public String showHomeView(Model model) {
		List<Operation> operations = new ArrayList<>();
	  operationRepo.findAll().forEach(o -> {
	  	if(!o.getStatus().equals("Завершённая")) operations.add(o);
	  });
	  model.addAttribute("operations", operations);
	  return "index";
	}
		
	@GetMapping("/activeOperations")
  public String getOperations(Model model, @RequestParam("page") Optional<Integer> page) {
		int currentPage = page.orElse(1);
  	Pageable pageable = PageRequest.of(currentPage - 1, 2);
  	Page<Operation> operations = operationRepo.findByStatus("Активная", pageable);
  	//List<Operation> activeOperations = new ArrayList<Operation>();
//  	operations.forEach(op -> { if(op.getStatus().equals("Активная")) activeOperations.add(op); });
  	model.addAttribute("operations", operations);

  	return "activeOperations";
  }
	
	@GetMapping("/operations/{id}")
  public String operationDetails(@PathVariable long id, Model model, @AuthenticationPrincipal User curUser) {
  	Operation operation = operationRepo.findById(id).orElse(null);
  	if(operation != null) {
			model.addAttribute("comment", new Comment());			
  		
  		boolean isAllowed = !operation.getUsers().contains(curUser);
    	model.addAttribute("isAllowed", isAllowed);
  		model.addAttribute("operation", operation);
  		return "operationDetails";
  	}
  	if(curUser.getRoles().contains(roleRepo.findByName("Admin"))) {
  		return "redirect:/adminPanel/operations";
  	} else {
  		return "redirect:/activeOperations";
  	}
  }
	
	@GetMapping("/operations/{id}/join")
	@PreAuthorize("hasAuthority('Volunteer')")
  public String joinOperation(@PathVariable long id, @AuthenticationPrincipal User curUser) {
  	Operation operation = operationRepo.findById(id).orElse(null);
  	if(operation != null && operation.getStatus().equals("Активная")) {
  		operation.getUsers().add(curUser);
  		operation.getSearchRequest().getOperation().getChat().getUsers().add(curUser);  		
  		operationRepo.save(operation);
  	}
  	return "redirect:/operations/{id}";
  }
	
}
