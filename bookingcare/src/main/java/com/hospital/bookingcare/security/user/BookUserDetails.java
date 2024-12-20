package com.hospital.bookingcare.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hospital.bookingcare.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookUserDetails implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private Collection<GrantedAuthority> authorities;
	private String firstName;
	private String lastName;

	public BookUserDetails(Long id, String email, String password, List<GrantedAuthority> authorities, String firstName,
			String lastName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static BookUserDetails buildUserDetails(User user) {
        // Giả sử `roleId` là một chuỗi đại diện cho vai trò (ví dụ: "ROLE_USER", "ROLE_ADMIN")
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRoleId());

        return new BookUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                List.of(authority),
                user.getFirstName(), // Lấy firstName từ `User`
                user.getLastName()    // Lấy lastName từ `User`
        );
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
