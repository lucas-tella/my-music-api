package br.inatel.mymusicapi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name= "users")
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name= "USER_ID")
	private String userId;
	
	private String name;
	private String email;
	private String password;
	
	@OneToMany
	private List<Playlist> myPlaylists;
	
//	@OneToOne()
//	private List<Playlist> myLikedTracks;
	
	@ManyToMany(fetch = FetchType.EAGER)	
	private List<Profile> profiles;
	
	@OneToMany(mappedBy = "user")
	private List<Like> likes = new ArrayList<>();

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
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
	
	public List<Playlist> getMyPlaylists() {
		return myPlaylists;
	}
	
//	public List<Playlist> getMyLikedTracks(){
//		return myLikedTracks;
//	}
}
