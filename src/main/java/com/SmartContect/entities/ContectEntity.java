package com.SmartContect.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ContectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;

	private String name;
	private String nickName;
    private String work;
	private String email;
    @Column(length = 1000)
	private String discription;
	private String profileImage;
	private String phoneNo;
    @ManyToOne
     private UserEntity userEntity;

//default contrustor
public ContectEntity(){
    
}
    public ContectEntity(int cId, String name, String nickName, String work, String email, String discription, String profileImage,
        String phoneNo, UserEntity userEntity) {
    this.cId = cId;
    this.name = name;
    this.nickName = nickName;
    this.work = work;
    this.email = email;
    this.discription = discription;
    this.profileImage = profileImage;
    this.phoneNo = phoneNo;
    this.userEntity = userEntity;
}

    //getter settet
	public int getcId() {
        return cId;
    }
    public void setcId(int cId) {
        this.cId = cId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDiscription() {
        return discription;
    }
    public void setDiscription(String discription) {
        this.discription = discription;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public void setImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public UserEntity getUserEntity() {
        return userEntity;
    }
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
	
    
}
