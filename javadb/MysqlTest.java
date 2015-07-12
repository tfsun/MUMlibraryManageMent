package javadb;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
 
 
public class MysqlTest {
    public static void main(String[] args) throws Exception {
    	DBManager jdbc = new DBManager();
    	try{		
            String sql = "show databases";
            boolean result = jdbc.execute(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            if (result != false) {
                //System.out.println("创建数据表成功");
//                sql = "insert into student(NO,name) values('2012001','tengfei sun')";
//                result = jdbc.executeUpdate(sql);
//                sql = "insert into student(NO,name) values('2012002','jack')";
//                result = jdbc.executeUpdate(sql);
                sql = "select * from student";
                ResultSet rs = jdbc.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
                System.out.println("ID\t\tname");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.close();
        }
 
    }
 
}