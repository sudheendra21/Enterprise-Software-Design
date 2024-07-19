package com.csye6220.Elearning.controller;


import com.csye6220.Elearning.DAO.UserDAO;
import com.csye6220.Elearning.DAO.User_CourseDAO;
import com.csye6220.Elearning.pojo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import org.springframework.ui.Model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    private static final String BUCKET = "springboot-s3002";
    private static final Region REGION = Region.US_EAST_2;
    private final S3Client s3Client = S3Client.builder().region(REGION).build();

    //Controller to handle the home page of the platform
    @GetMapping("/home")
    public String showHome(){


        return "home";
    }

    //Controller to show the login page
    @GetMapping("/login")
    public String showLogin(){

        return "login";
    }



    //Controller to show the home page

    @GetMapping("/")
    public String showHomee(){

        return "home";
    }

    //Controller which returns a view which has Links/API hits to various Admin Operations
    @GetMapping("/console")
    public String showConsole(){

        return "console";
    }

   //Controller which fetches the courses enrolled by user and gets the corresponding links from S3 Cloud Storage
    @GetMapping("/hello")
    public String showHello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Fetch courses enrolled by the user
        List<Object[]> result = User_CourseDAO.getCoursesEnrolledByUsers(username);

        // Extract course names from the result
        List<String> enrolledCourses = result.stream()
                .map(row -> (String) row[1]) // Assuming course name is at index 1
                .collect(Collectors.toList());

        System.out.println("Enrolled Courses:" + enrolledCourses);

        // Fetch video URLs from S3
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(BUCKET).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        System.out.println("S3 Objects:");
        response.contents().forEach(s3Object -> System.out.println("Key: " + s3Object.key()));

        // Create a HashMap to hold course names and video URLs
        Map<String, String> courseInfoMap = new HashMap<>();

        // Populate the courseInfoMap
        response.contents().stream()
                .filter(s -> {
                    String courseName = s.key().substring(s.key().lastIndexOf('/') + 1, s.key().lastIndexOf('.'));
                    courseName = courseName + ".mp4";
                    System.out.println("Processing key: " + s.key() + ", Extracted courseName: " + courseName);
                    boolean isMp4 = s.key().endsWith(".mp4");
                    boolean isEnrolled = enrolledCourses.contains(courseName);
                    return isMp4 && isEnrolled;
                })
                .forEach(s -> {
                    String courseName = s.key().substring(s.key().lastIndexOf('/') + 1, s.key().lastIndexOf('.'));
                    courseName = courseName + ".mp4";
                    String videoUrl = s3Client.utilities().getUrl(builder -> builder.bucket(BUCKET).key(s.key())).toString();
                    courseInfoMap.put(courseName, videoUrl);
                });

        System.out.println("Urls: " + courseInfoMap);

        model.addAttribute("courseInfoMap", courseInfoMap);

        return "hello";
    }


    //helper method to get course name from s3 key
    private String getCourseNameFromKey(String key) {
        int lastSlashIndex = key.lastIndexOf('/');
        int lastDotIndex = key.lastIndexOf('.');
        return key.substring(lastSlashIndex + 1, lastDotIndex);
    }







}
