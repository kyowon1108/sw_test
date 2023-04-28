package com.swconv.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;


class MysqlConnect {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://sw-database.c70bhcy2r2jo.ap-northeast-2.rds.amazonaws.com:3306/MysqlDB?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul";
    static final String USER = "admin";
    static final String PASS = "asdf";

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