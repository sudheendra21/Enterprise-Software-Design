package com.csye6220.Elearning.controller;

import com.csye6220.Elearning.DAO.CourseDAO;
import com.csye6220.Elearning.DAO.PaymentDAO;
import com.csye6220.Elearning.Util.S3Util;
import com.csye6220.Elearning.pojo.Course;
import com.csye6220.Elearning.pojo.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {



    //Controller to handle file upload to S3 bucket and mysql Database
    @PostMapping("/uploadfile")
    public String handleUploadForm(Model model, @RequestParam("description") String description,
                                   @RequestParam("file") MultipartFile multipart,@RequestParam("price") int price,
                                   @RequestParam("duration") int duration
                                   ) {
        String fileName = multipart.getOriginalFilename();

        System.out.println("Description: " + description);
        System.out.println("filename: " + fileName);
        System.out.println("Price: " + price);
        System.out.println("Duration: " + duration);
        System.out.println("AWS_ACCESS_KEY_ID: " + System.getenv("AWS_ACCESS_KEY_ID"));
        System.out.println("AWS_SECRET_ACCESS_KEY: " + System.getenv("AWS_SECRET_ACCESS_KEY"));


        String message = "";

        try {
            S3Util.uploadFile(fileName, multipart.getInputStream());

            message = "Your file has been uploaded successfully!";
            //hibernate Object is created only if upload is successful
            Course crs = new Course(fileName, duration, price , description);

            CourseDAO.savecourse(crs);
        } catch (Exception ex) {
            message = "Error uploading file: " + ex.getMessage();
        }

        model.addAttribute("message", message);



        return "message";
    }



}
