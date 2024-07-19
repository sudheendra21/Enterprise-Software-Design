package com.csye6220.Elearning.service;

import com.csye6220.Elearning.DAO.User_CourseDAO;
import com.csye6220.Elearning.pojo.Course;
import com.csye6220.Elearning.pojo.User;
import com.csye6220.Elearning.pojo.UserCourse;

public class UserCourseService {


    public static void addUsertoCourse(User user, Course course) {
        // Create a UserCourse instance
        UserCourse userCourse = new UserCourse();

        // Set the User and Course for the UserCourse
        userCourse.setUser(user);
        userCourse.setCourse(course);

        // Save the UserCourse instance
        User_CourseDAO.saveUser_Course(userCourse);
    }




}
