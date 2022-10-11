package volorg.controllers;

import java.io.IOException;
import java.io.*;
import java.nio.file.*;

import javax.validation.Valid;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String addSearchReq(@Valid SearchRequest searchRequest, BindingResult bindingResult, @AuthenticationPrincipal User curUser, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		if (bindingResult.hasErrors()) {
	    return "searchReq";
	  }
	  
	  String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	  searchRequest.setPhoto("/imgsearch/" + fileName);
	  searchRequest.setStatus("Ожидание");
	  searchRequest.setUser(curUser);
	  
	  searchReqRepo.save(searchRequest);
	  
	  if(!fileName.isEmpty()) {
	  	FileUploadUtil.saveFile("src/main/resources/static/imgsearch/", fileName, multipartFile);
	  }
	  
	  return "redirect:/";
	}
	
	public class FileUploadUtil {
    
    public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }
}

}