package javadb;


import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.List;
  
public class DBManager {  
    //public static final String url = "jdbc:mysql://127.0.0.1/javademo";  
    public static final String url = "jdbc:mysql://127.0.0.1/library_db";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "1234";  
  
    private Connection conn = null;  
    private PreparedStatement pst = null; 
    private Statement stmt = null;
  
    public DBManager(/*String sql*/) {  
        try {  
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            //pst = conn.prepareStatement(sql);
            stmt = conn.createStatement();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public ResultSet executeQuery(String sql) {
    	ResultSet rs =null;
		try {
			rs = stmt.executeQuery(sql);
//			System.out.println(rs.getString(2));
//			System.out.println(rs.getString(3));
//			System.out.println(rs.getString(4));
//			System.out.println(rs.getString("title"));
//			System.out.println(rs.getString(5));
//			System.out.println(rs.getInt(5));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rs;
    }
    
    public int executeUpdate(String sql) {
    	int rs =-1;
		try {
			rs = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rs;
    }
    
    public boolean execute(String sql) {
    	boolean rs =false;
		try {
			rs = stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rs;
    }
    
    public void processTransaction(List<String> sqls) throws Exception {  
        
        if (sqls == null) {  
            return;  
        }  
        try {  
            conn.setAutoCommit(false);   

            for (int i = 0; i < sqls.size(); i++) {  
            	stmt.execute(sqls.get(i));
            }  
            System.out.println("commit transactionï¼�");  
               
            conn.commit(); 
               
            System.out.println("transaction overï¼�");  
   
        } catch (SQLException e) {  
            try {  
                System.out.println("transcation failedï¼Œrollbackï¼�\n");  
                conn.rollback(); 
            } catch (SQLException e1) {  
                e1.printStackTrace();  
            }  
        } finally {  
        	stmt.close();  
        }  
    }
    public void close() {  
        try {  
            this.conn.close();  
            this.stmt.close();
            //this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}
