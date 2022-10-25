package volorg.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="search_request")
public class SearchRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
	@NotNull(message="Введите ФИО")
	@Size(min=4, message="Введите ФИО")
	@Column(name="full_name")
  private String fullName;
    
	@NotNull(message="Введите возраст")
  private int age;
    
	@NotNull(message="Укажите пол")
  private String sex;
    
	@NotNull(message="Введите область пропажи")
	@Size(min=4, message="Введите область пропажи")
	@Column(name="miss_area")
  private String missArea;
    
	@NotNull(message="Введите время пропажи")
	@Size(min=4, message="Введите время пропажи")
	@Column(name="miss_time")
  private String missTime;
  
	@Column(name="add_inf")
  private String addInf;
  
  private String photo;
 
  private String status;
  
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "searchRequest")
  private Operation operation;
  
  @ManyToOne
  private User user;
}
