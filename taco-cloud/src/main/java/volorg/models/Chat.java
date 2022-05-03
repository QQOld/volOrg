package volorg.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	@OneToOne(optional = false)
    public SearchRequest searchRequest;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy ="chat")
    public List<Message> messages = new ArrayList<Message>();
    
	@OneToMany
    public List<User> users = new ArrayList<User>();
    
}
