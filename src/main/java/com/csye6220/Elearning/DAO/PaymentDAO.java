package com.csye6220.Elearning.DAO;

import com.csye6220.Elearning.pojo.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAO {

    public static void savePayment(Payment pmt){

        Session session =  DAO.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(pmt);
        tx.commit();

    }



}
