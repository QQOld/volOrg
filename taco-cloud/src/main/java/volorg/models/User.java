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
	private String email;
	@NonNull
	private String name;
	@NonNull
	private String surName;
	@NonNull
	private String password;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
