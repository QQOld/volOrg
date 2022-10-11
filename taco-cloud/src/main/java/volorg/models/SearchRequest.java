package volorg.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class SearchRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
	@NotNull(message="Введите ФИО")
	@Size(min=4, message="Введите ФИО")
  private String fullName;
    
	@NotNull(message="Введите возраст")
  private int age;
    
	@NotNull(message="Укажите пол")
  private String sex;
    
	@NotNull(message="Введите область пропажи")
	@Size(min=4, message="Введите область пропажи")
  private String missArea;
    
	@NotNull(message="Введите время пропажи")
	@Size(min=4, message="Введите время пропажи")
  private String missTime;
  
  private String addInf;
  
  private String photo;
 
  private String status;
  
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "searchRequest")
  private Operation operation;
  
  @ManyToOne
  private User user;
}
