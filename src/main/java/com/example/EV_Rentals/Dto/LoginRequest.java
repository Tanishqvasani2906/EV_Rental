package com.example.EV_Rentals.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@Data
//@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    private String usernameOrEmail;
    private String password;
}