package volorg.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import volorg.models.Operation;
import volorg.repositories.OperationRepository;

@Controller
@RequestMapping("/index")
public class HomeController {
	
	private final OperationRepository operationRepo;
	
	@Autowired
	public HomeController(
			OperationRepository operationRepo) {
	  this.operationRepo = operationRepo;
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
	
}
