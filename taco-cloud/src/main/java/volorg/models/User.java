package volorg.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	@NotNull(message="Введите email")
	@Size(min=6, message="Введите email")
	private String email;
	
	@NonNull
	@NotNull(message="Введите имя")
	@Size(min=2, message="Введите имя")
	private String name;
	
	@NonNull
	@NotNull(message="Введите фамилию")
	@Size(min=2, message="Введите фамилию")
	private String surName;
	
	@NonNull
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles = new ArrayList<Role>();
	
	@OneToMany(mappedBy ="user")
	private List<SearchRequest> searchRequest = new ArrayList<SearchRequest>();
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private VolRequest volRequest;
	
	@ManyToMany(mappedBy = "users")
	private List<Operation> operations = new ArrayList<Operation>();
	
	@ManyToMany(mappedBy = "users")
	private List<Chat> chats = new ArrayList<Chat>();
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
	    return true;
		}
	
		if (!(obj instanceof User)) {
		    return false;
		}
		
		User other = (User) obj;

		return id == other.id;
	}
	
	@Override
  public int hashCode() {
	 return this.hashCode();
  }
	
	@Override
  public String toString() {
      return name + surName;
  }

	@Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }
	 
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
 
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
 
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
 
  @Override
  public boolean isEnabled() {
    return true;
  }

	@Override
	public String getUsername() {
		return email;
	}
	
}
