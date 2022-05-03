package volorg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import volorg.models.VolRequest;
import volorg.repositories.VolRequestRepository;

@Controller
@RequestMapping("/volReq")
public class VolReqCreateController {
	
private final VolRequestRepository volReqRepo;
	
	@Autowired
	public VolReqCreateController(
			VolRequestRepository volReqRepo) {
	  this.volReqRepo = volReqRepo;
	}
	
	@ModelAttribute(name = "volReq")
	public VolRequest addVolRequest() {
	  return new VolRequest();
	}
	
	@GetMapping
	public String volReq() {
	  return "volReq";
	}

}
