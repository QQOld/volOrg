package volorg.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude="user")
@Table(name="vol_request")
public class VolRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
	@NotNull(message="Введите номер телефона")
	@Size(min=9, message="Введите номер телефона")
	private String phone;
    
	@NotNull(message="Введите возраст")
	private int age;
    
	@NotNull(message="Укажите пол")
	private String sex;
    
	@NotNull(message="Введите область проживания")
	@Column(name="liv_area")
	private String livArea;
    
	private String status;
    
  @OneToOne(fetch = FetchType.EAGER)
  private User user;

}
