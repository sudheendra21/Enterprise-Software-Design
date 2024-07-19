package com.csye6220.Elearning.DAO;


import com.csye6220.Elearning.pojo.Course;
import com.csye6220.Elearning.pojo.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAO {

    public static void savecourse(Course crs){

        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(crs);
        tx.commit();

    }


    public static void updatecourse(Course crs){


        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(crs);
        tx.commit();
    }
    public static List<Course> getAllCourses() {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Course";
            Query<Course> query = session.createQuery(hql, Course.class);
            return query.list();
        }
    }
    public static Course getCourseByName(String courseName) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Course WHERE name = :courseName";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("courseName", courseName);
            return query.uniqueResult();
        }
    }




    public static void deletecourse(int courseId){

        Session session =  DAO.getSessionFactory().openSession();

        try {

            Transaction tx = session.beginTransaction();
            String hql1 = "DELETE FROM Course WHERE id = :courseId";
            Query<?> query = session.createQuery(hql1);
            query.setParameter("courseId", courseId);
            query.executeUpdate();

            tx.commit();

        }


        catch(Exception e){
            e.printStackTrace();
        }

    }


    public static Course getCourseById(int courseId) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Course WHERE id = :courseId";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("courseId", courseId);
            return query.uniqueResult();
        }
    }

}
