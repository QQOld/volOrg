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
	private Long id;
    
	private String status;
  
  @OneToOne(optional = false)
  private SearchRequest searchRequest;
  
  @ManyToMany
  private List<User> users = new ArrayList<User>();

  @OneToOne(cascade = CascadeType.ALL, mappedBy ="operation")
  private Chat chat;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "operation")
  private List<Comment> comments = new ArrayList<Comment>();

  @Override
  public String toString() {
      return status;
  }
}
