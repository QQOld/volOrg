package volorg.view_models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangePasswordViewModel {
	
	@NotNull(message="Введите пароль")
	@Size(min=8, message="Введите пароль(как минимум 8 символов)")
	private String oldPassword;
	
	@NotNull(message="Введите пароль")
	@Size(min=8, message="Введите пароль(как минимум 8 символов)")
	private String newPassword;

}
