package com.csye6220.Elearning.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="payment")

public class Payment {


     @jakarta.persistence.Id
     String Id;
     String description;







}
