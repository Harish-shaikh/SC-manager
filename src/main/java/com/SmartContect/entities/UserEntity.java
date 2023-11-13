package com.SmartContect.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uId;

	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	@Column(length = 500)
	private String about;

	private String role;
	private boolean enabled;
	private String imageUrl;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private List<ContectEntity> contectEntity = new ArrayList<>();

	// default Contrutor
	public UserEntity() {

	}

	public UserEntity(int uId, String name, String email, String password, String about, String role, boolean enabled,
			String imageUrl, List<ContectEntity> contectEntity) {
		this.uId = uId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.role = role;
		this.enabled = enabled;
		this.imageUrl = imageUrl;
		this.contectEntity = contectEntity;
	}

	// Gettet Setter of all entities
	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public List<ContectEntity> getContectEntity() {
		return contectEntity;
	}

	public void setContectEntity(List<ContectEntity> contectEntity) {
		this.contectEntity = contectEntity;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "UserEntity [uId=" + uId + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", role=" + role + ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", contectEntity="
				+ contectEntity + "]";
	}

}
