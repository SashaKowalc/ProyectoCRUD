package org.example.sasha.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String JDBC_URL="jdbc:mysql://localhost:3306/pyme";
    private static final String JDBC_USER="root";
    private static final String JDBC_PASSWORD="";
    private static BasicDataSource pool;


    public static BasicDataSource getInstance() throws SQLException {
        if(pool==null){
            pool= new BasicDataSource();
            pool.setUrl(JDBC_URL);
            pool.setUsername(JDBC_USER);
            pool.setPassword(JDBC_PASSWORD);

            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(10);
            pool.setMaxTotal(10);

        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}
