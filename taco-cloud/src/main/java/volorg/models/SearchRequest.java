package volorg.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class SearchRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
    
    private String fullName;
    
    private int age;
    
    private String sex;
    
    private String missArea;
    
    private String missTime;
    
    private String addInf;
    
    private String photo;
   
    private String status;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "searchRequest")
    private Operation operation;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy ="searchRequest")
    private Chat chat;
    
    @ManyToOne
    private User user;
}
