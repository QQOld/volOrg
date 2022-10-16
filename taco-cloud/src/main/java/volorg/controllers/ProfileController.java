package volorg.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import volorg.models.Message;
import volorg.models.User;
import volorg.models.VolRequest;
import volorg.repositories.ChatRepository;
import volorg.repositories.MessageRepository;
import volorg.repositories.RoleRepository;
import volorg.repositories.UserRepository;
import volorg.repositories.VolRequestRepository;
import volorg.view_models.ChangePasswordViewModel;
import volorg.view_models.RegistrationForm;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	 	private UserRepository userRepo;
	 	private VolRequestRepository volReqRepo;
	 	private ChatRepository chatRepo;
	 	private MessageRepository messageRepo;
	 	private PasswordEncoder passwordEncoder;
	 
	  @Autowired
	  public ProfileController(
	    UserRepository userRepo, PasswordEncoder passwordEncoder, VolRequestRepository volReqRepo, ChatRepository chatRepo, MessageRepository messageRepo) {
	    this.userRepo = userRepo;
	    this.volReqRepo = volReqRepo;
	    this.chatRepo = chatRepo;
	    this.messageRepo = messageRepo;
	    this.passwordEncoder = passwordEncoder;
	  }
	  
	  @GetMapping
	  public String getProfile(@AuthenticationPrincipal User user, Model model) {
	  	User curUser = userRepo.findById(user.getId()).orElse(null);
	  	model.addAttribute("user", curUser);
	    return "editProfile";
	  }
	  
	  @PostMapping
	  public String editProfile(@Valid User user, BindingResult bindingResult, @AuthenticationPrincipal User curUser) {
			if (bindingResult.hasErrors()) {
				return "editProfile";
			}
			curUser.setEmail(user.getEmail());
			curUser.setName(user.getName());
			curUser.setSurName(user.getSurName());
			if(curUser.getVolRequest() != null) {
				VolRequest newVolReq =  user.getVolRequest();
				curUser.getVolRequest().setAge(newVolReq.getAge());
				curUser.getVolRequest().setPhone(newVolReq.getPhone());
				curUser.getVolRequest().setSex(newVolReq.getSex());
				curUser.getVolRequest().setLivArea(newVolReq.getLivArea());
			}
		  userRepo.save(curUser);
		  return "redirect:/";
	  }
	  
	  @GetMapping("/changePassword")
	  public String changePasswordForm(@ModelAttribute ChangePasswordViewModel changePasswordViewModel, Model model) {	  	
	  	model.addAttribute("isMatch", false);
	    return "changePassword";
	  }
	  
		@PostMapping("/changePassword")
		public String changePassword(@Valid ChangePasswordViewModel changePasswordViewModel, BindingResult bindingResult, @AuthenticationPrincipal User curUser, Model model) {
			if (bindingResult.hasErrors()) {
			 	return "changePassword";
			}
			if(!passwordEncoder.matches(changePasswordViewModel.getOldPassword(), curUser.getPassword())) {
			 model.addAttribute("isMatch", true);
			 return "changePassword";
			}
			curUser.setPassword(passwordEncoder.encode(changePasswordViewModel.getNewPassword()));
			userRepo.save(curUser);
			return "redirect:/profile";
		}
		 
		@GetMapping("/chats")
	  public String getChats(Model model, @RequestParam("page") Optional<Integer> page) {
			int currentPage = page.orElse(1);
			Pageable pageable = PageRequest.of(currentPage - 1, 2);
	  	Page<Chat> chats = chatRepo.findAll(pageable);
	  	model.addAttribute("chats", chats);
	    return "chats";
		}
	  
		@GetMapping("/chats/{id}")
	  public String openChat(@PathVariable long id, Model model) {
			Chat chat = chatRepo.findById(id).orElse(null);
			if(chat != null) {
				model.addAttribute("chat", chat);
				return "openChat";
			}
	    return "chats";
		}
		
		@PostMapping("/chats/{id}/createMessage")
	  public String createMessage(@PathVariable long id, @RequestParam(value = "text") String text, @AuthenticationPrincipal User curUser) {
			Chat chat = chatRepo.findById(id).orElse(null);
			if(chat != null) {
				Message message = new Message();
				message.setText(text);
				message.setUser(curUser);
				message.setTimestamp(new Date());
				message.setUserName(curUser.getSurName() + " " + curUser.getName());
				message.setChat(chat);
				chat.getMessages().add(message);
				chatRepo.save(chat);
				return "redirect:/profile/chats/{id}";
			}
	    return "chats";
		}
}
