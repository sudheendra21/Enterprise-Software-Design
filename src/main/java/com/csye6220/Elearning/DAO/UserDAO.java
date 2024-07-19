package com.csye6220.Elearning.DAO;

import com.csye6220.Elearning.pojo.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAO {

    public static void saveUser(User stu){

        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(stu);
        tx.commit();

    }


    //Read

    public static void readUser(User stu) {


        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(stu);
        tx.commit();

    }

    //Update

    public static void updateUser(User stu) {

        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(stu);
        tx.commit();

    }

    //Delete
    public static void deleteUser(User stu){


        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(stu);
        tx.commit();

    }

    public static User getUserByUsername(String username) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("firstname"), username));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }






}
