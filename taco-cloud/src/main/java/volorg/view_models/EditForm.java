package volorg.view_models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditForm {
	
	@NotNull
	private long id;
	
	@NotNull(message="Введите email")
	@Size(min=6, message="Введите email")
	private String email;
	
	@NotNull(message="Введите имя")
	@Size(min=2, message="Введите имя")
	private String name;
	
	@NotNull(message="Введите фамилию")
	@Size(min=2, message="Введите фамилию")
	private String surName;

}
