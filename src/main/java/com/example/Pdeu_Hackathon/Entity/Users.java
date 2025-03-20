package com.example.Pdeu_Hackathon.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
//@Data
public class Users {

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String user_id;
    @Column(name = "username" , nullable = true, unique = true)
    private String username;
    @Column(name = "password" , nullable = true , unique = true)
    private String password;
    @Column(name = "email" , nullable = false , unique = true)
    private String email;
    @Column(name = "phone" , nullable = true , unique = true)
    private String phone;
//    @Column(name = "first_name")
//    private String firstName;

    @Column(name = "temp_token")
    private String tempToken;

    public LocalDateTime getTempTokenExpiry() {
        return tempTokenExpiry;
    }

    public void setTempTokenExpiry(LocalDateTime tempTokenExpiry) {
        this.tempTokenExpiry = tempTokenExpiry;
    }

    public String getTempToken() {
        return tempToken;
    }

    public void setTempToken(String tempToken) {
        this.tempToken = tempToken;
    }

    @Column(name = "temp_token_expiry")
    private LocalDateTime tempTokenExpiry;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private Role role = Role.USER;

}
