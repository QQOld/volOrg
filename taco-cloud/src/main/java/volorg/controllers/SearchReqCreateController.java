package volorg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import volorg.models.SearchRequest;
import volorg.repositories.SearchRequestRepository;

@Controller
@RequestMapping("/searchReq")
public class SearchReqCreateController {
	
	private final SearchRequestRepository searchReqRepo;
	
	@Autowired
	public SearchReqCreateController(
			SearchRequestRepository searchReqRepo) {
	  this.searchReqRepo = searchReqRepo;
	}
	
	@ModelAttribute(name = "searchReq")
	public SearchRequest addSearchReq() {
	  return new SearchRequest();
	}
	
	@GetMapping
	public String searchReq() {
	  return "searchReq";
	}

}