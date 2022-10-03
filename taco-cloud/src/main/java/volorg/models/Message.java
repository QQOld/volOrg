package volorg.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
  private String userName;
  
  private String text;
  
  private LocalDateTime timestamp;
  
  @ManyToOne
  public User user;
  
  @ManyToOne(optional = false)
  public Chat chat;

}
