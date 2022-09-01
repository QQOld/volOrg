package volorg.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import volorg.models.SearchRequest;
import volorg.models.User;
import volorg.models.VolRequest;
import volorg.repositories.SearchRequestRepository;
import volorg.repositories.UserRepository;

@Controller
@RequestMapping("/searchReq")
public class SearchReqCreateController {
	
	private final SearchRequestRepository searchReqRepo;
	private final UserRepository userRepo;
	
	@Autowired
	public SearchReqCreateController(
			SearchRequestRepository searchReqRepo, UserRepository userRepo) {
	  this.searchReqRepo = searchReqRepo;
	  this.userRepo = userRepo;
	}
	
	
	@GetMapping
	public String searchReq(@ModelAttribute SearchRequest searchRequest) {
	  return "searchReq";
	}
	
	@PostMapping
	public String addSearchReq(@Valid SearchRequest searchRequest, BindingResult bindingResult) {
	  if (bindingResult.hasErrors()) {
	    return "searchReq";
	  }
	 
	  searchRequest.setStatus("Ожидание");
	  searchReqRepo.save(searchRequest);
	  return "redirect:/index";
	}

}