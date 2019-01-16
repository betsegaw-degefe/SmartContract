package com.gebeya.smartcontract.model.data.objectBox;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class UserLoginData {
    @Id
    private long id;
    @Unique
    private String userId;
    @Index
    private String firstName;
    @Index
    private String lastName;
    @Index
    private String phoneNo;

    private String token;
    private String registered;
    private String profilePic;

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public UserLoginData() {

    }

    public UserLoginData(String userId,String profilePic, String firstName,
                         String lastName, String phoneNo,String registered, String token) {
        this.userId = userId;
        this.profilePic = profilePic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.registered = registered;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String tocken) {
        this.token = tocken;
    }
}
