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

import lombok.Data;

@Data
@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(optional = false)
	private SearchRequest searchRequest;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy ="chat")
	private List<Message> messages = new ArrayList<Message>();
    
	@ManyToMany
	private List<User> users = new ArrayList<User>();
    
}
