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
public class Operation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
    
    public String status;
    
    @OneToOne(optional = false)
    public SearchRequest searchRequest;
    
    @ManyToMany
    public List<User> users = new ArrayList<User>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operation")
    public List<Comment> comments = new ArrayList<Comment>();

}
