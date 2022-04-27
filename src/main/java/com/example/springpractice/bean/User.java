package com.example.springpractice.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(description = "All details about users.")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(notes = "Name should have atleast 2 characters.")
    @Size(min = 2, message = "Name should have atleast 2 characters")
    private String firstName;

    private String lastName;
    private String role;
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Exam> exam;

    public User() {
    }

    public User(String firstName, String lastName, String role, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Exam> getExam() {
        return exam;
    }

    public void setExam(List<Exam> exam) {
        this.exam = exam;
    }
}
