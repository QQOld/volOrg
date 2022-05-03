package volorg.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class VolRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
    
    public String phone;
    
    public int age;
    
    public String sex;
    
    public String livArea;
    
    public String status;
    
    @OneToOne
    private User user;

}
