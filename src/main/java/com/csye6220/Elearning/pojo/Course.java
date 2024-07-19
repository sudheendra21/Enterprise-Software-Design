package com.csye6220.Elearning.pojo;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="course")
public class Course {

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Id
    int id;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String name;

    int duration;
    int price;

   @Transient
   String link;

    String description;



    public Course(String name, int duration, int price , String descr) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.description = descr;
    }


    public Course() {

    }


}
