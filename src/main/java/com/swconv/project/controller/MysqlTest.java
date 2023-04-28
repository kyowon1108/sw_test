package com.swconv.project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;


class MysqlConnect {
    @Value("${spring.datasource.driver-class-namel}")
    static String JDBC_DRIVER;
    @Value("${spring.datasource.url}")
    static String DB_URL;
    @Value("${spring.datasource.usernam}")
    static String USER;
    @Value("${spring.datasource.password}")
    static String PASS;

    static String connect() {
        Connection conn = null;
        Statement stmt = null;
        String res = "";
        try{

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT postIdx, text, userIdx, createdAt FROM Post";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int postIdx  = rs.getInt("postIdx");
                String text = rs.getString("text");
                int userIdx = rs.getInt("userIdx");
                String createdAt = rs.getString("createdAt");


                res += "postIdx: " + postIdx;
                res += ", text: " + text;
                res += ", userIdx: " + userIdx;
                res += ", createdAt: " + createdAt;
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return res;
    }
}

@RestController
public class MysqlTest {

    @GetMapping(value = "/mysql")
    public String test_mysql(){
        return MysqlConnect.connect();
    }
}