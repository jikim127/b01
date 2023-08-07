package org.zerock.b01.crawlingNews;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CrawlingDAO {


    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String id = "webdb";
    private String pwd = "webdb";
    private String sql = "insert into product values (?,?,?,?)";

//    public void productInsert(ProductVO vo){
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection(url,id,pwd);
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1,vo.getNo());
//            pstmt.setString(2,vo.getTitle());
//            pstmt.setInt(3,vo.getPrice());
//            pstmt.setString(4,vo.getImage());
//
//            pstmt.executeUpdate();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try {
//                if(rs != null) rs.close();
//                if(pstmt != null) pstmt.close();
//                if(conn != null) conn.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

}
