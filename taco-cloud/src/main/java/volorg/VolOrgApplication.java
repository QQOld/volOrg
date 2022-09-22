package volorg;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import volorg.models.Role;
import volorg.repositories.RoleRepository;

@SpringBootApplication
public class VolOrgApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolOrgApplication.class, args);
	}
	
	@Bean
  public CommandLineRunner dataLoader(RoleRepository roleRepo) {
		return args -> {
			
			if(roleRepo.findByName("User") == null) roleRepo.save(new Role("User"));
			if(roleRepo.findByName("Admin") == null) roleRepo.save(new Role("Admin"));
			if(roleRepo.findByName("Volunteer") == null) roleRepo.save(new Role("Volunteer"));
    };
  }
}
