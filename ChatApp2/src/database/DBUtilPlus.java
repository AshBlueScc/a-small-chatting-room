package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtilPlus {
    
	private static final String URL="jdbc:mysql://172.21.69.49:3306/anonymouschat";//������IP��ַ*******************************************
    private static final String NAME="root";
    private static final String PASSWORD="mysql";
    
    private static Connection conn=null;
    //��̬����飨�������������������ݿ���뾲̬���У�
    static{
        try {
            //1.������������
            Class.forName("com.mysql.jdbc.Driver");
            //2.������ݿ������
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //�����ṩһ����������ȡ���ݿ�����
    public static Connection getConnection(){
        return conn;
    }
    
//    public static void main(String[] args) throws Exception{
//        
//        //3.ͨ�����ݿ�����Ӳ������ݿ⣬ʵ����ɾ�Ĳ�
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("select user_name,age from imooc_goddess");//ѡ��import java.sql.ResultSet;
//        while(rs.next()){//��������������ݣ��ͻ�ѭ����ӡ����
//            System.out.println(rs.getString("user_name")+","+rs.getInt("age"));
//        }
//    }
}