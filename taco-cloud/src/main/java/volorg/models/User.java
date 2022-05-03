package volorg.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min=2, message="Введите имя")
	private String name;
	
	@NotNull
	@Size(min=5, message="Введите фамилию")
	private String surName;
	
	@OneToMany(mappedBy ="user")
	private List<SearchRequest> searchRequest = new ArrayList<SearchRequest>();
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private VolRequest volRequest;
	
	@ManyToMany(mappedBy = "users")
	private List<Operation> operations = new ArrayList<Operation>();
	
	@ManyToMany(mappedBy = "users")
	private List<Chat> chats = new ArrayList<Chat>();

}
