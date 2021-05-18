package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "374456Mgts@";

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USERNAME,PASSWORD);
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
        } catch (SQLException s) {}
    }

    public static void closeQuietly(PreparedStatement st) {
        try {
            st.close();
        } catch (SQLException s) {}
    }
}
