package entities;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String roles;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String sexe;
    private int rating;
    private String biography;
    private int strike;
    private String status;
    private String image_name;

    public User() {
        status="not connected";
        strike=0;
        rating=0;
    }

    public User(int id, String username, String email, String password, String roles, String firstname, String lastname, Date birthdate, String sexe, int rating, String biography, int strike, String status, String image_name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.sexe = sexe;
        this.rating = rating;
        this.biography = biography;
        this.strike = strike;
        this.status = status;
        this.image_name = image_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getStrike() {
        return strike;
    }

    public void setStrike(int strike) {
        this.strike = strike;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

}
