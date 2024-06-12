package org.acme.DTO.UserDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserPostDTO {
    @Schema(example = "Julius")
    private String firstName;

    @Schema(example = "Caesar")
    private String lastName;

    @Schema(example = "eng")
    private String language;

    @Schema(example = "2002-10-01")
    private LocalDate birthday;

    @Schema(example = "julius@rome.com")
    private String email;

    @Schema(example = "CaesarCipher")
    private String userPassword;

    @Schema(example = "987654321")
    private String phone;

    @Schema(example = "México")
    private String country;

    @Schema(example = "admin")
    private String userType;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
