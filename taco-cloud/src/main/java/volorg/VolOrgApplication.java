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
			roleRepo.save(new Role("User"));
			roleRepo.save(new Role("Admin"));
    };
  }
}
