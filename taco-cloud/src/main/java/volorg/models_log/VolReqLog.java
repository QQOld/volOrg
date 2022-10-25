package volorg.models_log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="volreqLog")
public class VolReqLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
	private String name;
	
	private String phone;
	
	private String email;
    
	@Column(name="liv_area")
	private String livArea;
	
	private Date timestamp;
    
}
