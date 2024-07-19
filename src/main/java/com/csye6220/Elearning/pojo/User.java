package com.csye6220.Elearning.pojo;

import com.csye6220.Elearning.DAO.CourseDAO;
import jakarta.persistence.*;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
public class User {


    //setting primary key
    @Id
    int id;
   private String firstname;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role='" + role + '\'' +
//                ", course=" + course +
                '}';
    }

    private String lastname;
    @Column(name = "role")

    private String role;


//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private Course course;






    public User(String firstname, String lastname, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }


    public User( String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getRole() {

        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
