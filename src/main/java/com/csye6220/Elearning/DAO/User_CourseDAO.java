package com.csye6220.Elearning.DAO;

import com.csye6220.Elearning.pojo.UserCourse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class User_CourseDAO {

    public static void saveUser_Course(UserCourse ucs){


        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(ucs);
        tx.commit();




    }


    public static void updateUser_Course(UserCourse ucs){

        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(ucs);
        tx.commit();

    }

    public static List<Object[]> getCoursesEnrolledByUsers(String username) {
        String hql = "SELECT uc.course.id, uc.course.name FROM UserCourse uc WHERE uc.user.firstname=:username";

        Session session =  DAO.getSessionFactory().openSession();

        Query query = session.createQuery(hql);
        query.setParameter("username", username);

        return query.getResultList();
    }

    public static void deleteUserCourse(int courseId){


        Session session =  DAO.getSessionFactory().openSession();

        try {

            Transaction tx = session.beginTransaction();
            System.out.println("CourseId:"+courseId);
            String sql = "DELETE FROM usercourse WHERE course_id = :courseId";
            Query<?> query = session.createNativeQuery(sql);
            query.setParameter("courseId", courseId);
            int rowsAffected = query.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) deleted from UserCourse table.");
            } else {
                System.out.println("No rows deleted from UserCourse table for courseId: " + courseId);
            }

            tx.commit();
        }


        catch(Exception e){
            e.printStackTrace();
        }




    }

}
