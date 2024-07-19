package com.csye6220.Elearning.controller;


import com.csye6220.Elearning.DAO.CourseDAO;
import com.csye6220.Elearning.DAO.DAO;
import com.csye6220.Elearning.DAO.UserDAO;
import com.csye6220.Elearning.DAO.User_CourseDAO;
import com.csye6220.Elearning.pojo.Course;
import com.csye6220.Elearning.pojo.User;
import com.csye6220.Elearning.service.UserCourseService;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class CourseController {

    private static final String BUCKET = "springboot-s3002";
    private static final Region REGION = Region.US_EAST_2;
    private final S3Client s3Client = S3Client.builder().region(REGION).build();

   //redirects to a view where user can give course details to upload to E-learning Platform
    @GetMapping("/uploadcourse")
    public String showHome(){

        return "upload";
    }

    //redirects to a view where user can give course details to delete from E-learning Platform
    @GetMapping("/deletecourse")
    public String showDelete(){

        return "delete";
    }


    //Controller to show list of all the courses available in S3 Bucket
    @GetMapping("/showcourses")
    public String showCourses(Model model) {
        // Create a map to hold course names and video URLs
        Map<String, String> courseInfoMap = new HashMap<>();

        // Retrieve all objects from S3
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(BUCKET).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Object[]> result = User_CourseDAO.getCoursesEnrolledByUsers(username);

        // Courses enrolled by the current user
        List<String> enrolledCourses = result.stream()
                .map(row -> (String) row[1]) // Assuming course name is at index 1
                .collect(Collectors.toList());

        System.out.println(enrolledCourses);

        // Filter for video files and populate the courseInfoMap for courses not enrolled by the user
        response.contents().stream()
                .filter(s -> s.key().endsWith(".mp4"))
                .filter(s -> !enrolledCourses.contains(getCourseNameFromKey(s.key())))
                .forEach(s -> {
                    String courseName = getCourseNameFromKey(s.key());
                    String videoUrl = s3Client.utilities().getUrl(builder -> builder.bucket(BUCKET).key(s.key())).toString();
                    courseInfoMap.put(courseName, videoUrl);
                });

        // Add the courseInfoMap to the model
        model.addAttribute("courseInfoMap", courseInfoMap);

        return "showcourses";
    }

    //Controller to handle adding course to user or course registration
    @PostMapping("/addCourses")

    public String addCourses( @RequestParam String selectedCourse, @RequestParam String videoUrl,Model model) {



        String C =   selectedCourse;
        Course course = CourseDAO.getCourseByName(C);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = UserDAO.getUserByUsername(username);
        UserCourseService.addUsertoCourse(user,course);

        System.out.println("Selected Course from Register "+ selectedCourse);





        return "addcourse";


    }

    //Controller which fetches course details from S3 cloud storage to present to admin ,letting him select courses to delete
    @GetMapping("/deletecourses")
    public String deleteCourse(Model model){

        Map<String, String> courseInfoMap = new HashMap<>();

        // Retrieve all objects from S3
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(BUCKET).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        // Filter for video files and populate the courseInfoMap
        response.contents().stream()
                .filter(s -> s.key().endsWith(".mp4"))
                .forEach(s -> {
                    String courseName = getCourseNameFromKey(s.key());
                    String videoUrl = s3Client.utilities().getUrl(builder -> builder.bucket(BUCKET).key(s.key())).toString();
                    courseInfoMap.put(courseName, videoUrl);
                });

        // Add the courseInfoMap to the model



        model.addAttribute("courseInfoMap", courseInfoMap);




        return "delete";
    }



    //Controller to handle delete course from E-learning platform and Database
    @PostMapping("/deletefromdatabase")
    public String deletefromdatabase(@RequestParam String selectedCourse,Model model){

        System.out.println("Selected Course for Deletion:"+ selectedCourse);

        Course course = CourseDAO.getCourseByName(selectedCourse);
        int ID = course.getId();
        System.out.println(course.getId());
        //delete from database
        User_CourseDAO.deleteUserCourse(ID);
        CourseDAO.deletecourse(ID);

       //delete from S3 cloud
        String s3Key = selectedCourse;
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(BUCKET).key(s3Key).build());


        return "deletedatabasemessage";


    }


    //Controller to update course details in database
    @PostMapping("/updatecourse2")

    public String updateCourse2(@RequestParam int courseid, @RequestParam String courseName, @RequestParam int price, @RequestParam int duration, @RequestParam String description, Model model){


         Course d = CourseDAO.getCourseById(courseid);
         d.setName(courseName);
         d.setPrice(price);
         d.setDuration(duration);
         d.setDescription(description);

         CourseDAO.updatecourse(d);


         return "update-course2";



    }


    //handler method which redirects to "update-course1" view where user can fill in updated details
    @GetMapping("/updatecourse1")

    public String updateCourse1(){


        return "update-course1";

    }


   //helper method to give course name from key
    private String getCourseNameFromKey(String key) {
        int lastSlashIndex = key.lastIndexOf('/');
        int lastDotIndex = key.lastIndexOf('.');
        return key.substring(lastSlashIndex + 1, lastDotIndex);
    }

}
