package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtilPlus {
    
	private static final String URL="jdbc:mysql://172.21.69.49:3306/anonymouschat";//局域网IP地址*******************************************
    private static final String NAME="root";
    private static final String PASSWORD="mysql";
    
    private static Connection conn=null;
    //静态代码块（将加载驱动、连接数据库放入静态块中）
    static{
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库的连接
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //对外提供一个方法来获取数据库连接
    public static Connection getConnection(){
        return conn;
    }
    
//    public static void main(String[] args) throws Exception{
//        
//        //3.通过数据库的连接操作数据库，实现增删改查
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("select user_name,age from imooc_goddess");//选择import java.sql.ResultSet;
//        while(rs.next()){//如果对象中有数据，就会循环打印出来
//            System.out.println(rs.getString("user_name")+","+rs.getInt("age"));
//        }
//    }
}