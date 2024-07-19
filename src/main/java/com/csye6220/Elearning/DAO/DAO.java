package com.csye6220.Elearning.DAO;

//import org.apache.catalina.User;
import com.csye6220.Elearning.pojo.Course;
import com.csye6220.Elearning.pojo.Payment;
import com.csye6220.Elearning.pojo.User;

import com.csye6220.Elearning.pojo.UserCourse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;


public class DAO {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){

        if (sessionFactory == null) {

            try{


                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL,"jdbc:mysql://localhost:3306/coopsapp");
                settings.put(Environment.USER,"root");
                settings.put(Environment.PASS,"Ardneehdus12");
                settings.put(Environment.DIALECT,"org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL,"true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
                settings.put(Environment.HBM2DDL_AUTO,"update");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Course.class);
                configuration.addAnnotatedClass(Payment.class);
                configuration.addAnnotatedClass(UserCourse.class);
               // configuration.addAnnotatedClass(Payment1.class);





                sessionFactory = configuration.buildSessionFactory();


                return sessionFactory;




            }
            catch (Exception e) {
                e.printStackTrace();
            }


        }
        return sessionFactory;

    }
}
