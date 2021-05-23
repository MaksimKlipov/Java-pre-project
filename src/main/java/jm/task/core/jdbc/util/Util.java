package jm.task.core.jdbc.util;


import java.sql.*;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "374456Mgts@";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.username", USERNAME)
                        .setProperty("hibernate.connection.password", PASSWORD)
                        .setProperty("hibernate.connection.autocommit", "false")
                        .setProperty("hibernate.show_sql", "true")
                        .addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception h) {
                h.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeSession(Session s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (Exception e) {
        }
    }

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            con.setAutoCommit(false);
            return con;
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return getConnection();
    }

    public static void rollbackQuietly() {
        try {
            getConnection().rollback();
        } catch (SQLException s) {
        }
    }

    public static void closeQuietly(PreparedStatement st) {
        try {
            st.close();
        } catch (SQLException s) {
        }
    }
}
