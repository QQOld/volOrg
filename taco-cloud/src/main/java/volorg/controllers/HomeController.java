package volorg.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@ModelAttribute(name = "operations")
	public List<Operation> getOperations() {
	  List<Operation> operations = new ArrayList<>();
	  operationRepo.findAll().forEach(i -> operations.add(i));
	  return operations;
	}
	
	@GetMapping
	public String showHomeView() {
	  return "index";
	}
		
	@GetMapping("/activeOperations")
  public String getOperations(Model model) {
  	List<Operation> operations = (List<Operation>) operationRepo.findAll();
  	List<Operation> activeOperations = new ArrayList<Operation>();
  	operations.forEach(op -> { if(op.getStatus().equals("Активная")) activeOperations.add(op); });
  	model.addAttribute("operations", activeOperations); 

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
	@PreAuthorize("hasRole('Volunteer')")
  public String joinOperation(@PathVariable long id, @AuthenticationPrincipal User curUser) {
  	Operation operation = operationRepo.findById(id).orElse(null);
  	if(operation != null && operation.getStatus().equals("Активная")) {
  		operation.getUsers().add(curUser);
  		operation.getSearchRequest().getChat().getUsers().add(curUser);  		
  		operationRepo.save(operation);
  	}
  	return "redirect:/operations/{id}";
  }
	
}
