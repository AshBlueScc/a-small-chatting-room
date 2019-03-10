package database;

import java.awt.print.Printable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class UserInfoOperation {

	//����û�
    public void addUser(User u) throws Exception{
        Connection con=DBUtilPlus.getConnection();//�����õ����ݿ������
        String sql="" + 
        		"insert into userinfo"+
        		"(id,user_name,pass_word,update_date,phone_number) "+
                "values("+
                "?,?,?,current_date(),?)";//������?��ʾ���൱��ռλ��;��mysql�����ں���current_date()����ȡ��ǰ����
        //Ԥ����sql���
        PreparedStatement psmt = con.prepareStatement(sql);
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setInt(1, u.getId());
        psmt.setString(2, u.getUserName());
        psmt.setString(3, u.getPassword());
        psmt.setString(4, u.getPhoneNumber());
        //ע�⣺setDate()�����ڶ���������Ҫ����java.sql.Date���ͣ����Ǵ���������java.util.Date�����Ͳ�������Ҫ��һ��ת��
        //ִ��SQL���
        psmt.execute();
        /**
         * prepareStatement��������ὫSQL�����ص���������conn���ɳ����У����ǲ���ֱ��ִ��
         * ���ǵ�������execute()������ʱ�������ִ�У�
         * 
         * ����SQL�еĲ�����?��ʾ���൱��ռλ����Ȼ���ڶԲ������и�ֵ��
         * ������ִ��ʱ����Щ�����������SQL����У���SQL���ƴ��������ȥִ�С�
         * �����ͻ���ٶ����ݿ�Ĳ���
         */
    }
    
  //�����û�
    public void updateUser(User u) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//�����õ����ݿ������
        String sql="" + 
                "update userinfo "+
                "set id=?,user_name=?,pass_word=?,"+
                "update_date=current_date(),phone_number=? "+
                "where id=?";//������?��ʾ���൱��ռλ��;��mysql�����ں���current_date()����ȡ��ǰ����
        //Ԥ����sql���
        PreparedStatement psmt = con.prepareStatement(sql);
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setInt(1, u.getId());
        psmt.setString(2, u.getUserName());
        psmt.setString(3, u.getPassword());
        //ע�⣺setDate()�����ڶ���������Ҫ����java.sql.Date���ͣ����Ǵ���������java.util.Date�����Ͳ�������Ҫ��һ��ת��
        psmt.setString(4, u.getPhoneNumber());
        psmt.setInt(5, u.getId());
        //ִ��SQL���
        psmt.execute();    
    }
    
  //ɾ���û�
    public void delUser(Integer id) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//�����õ����ݿ������
        String sql="" + 
                "delete from info "+
                "where id=?";//������?��ʾ���൱��ռλ��;��mysql�����ں���current_date()����ȡ��ǰ����
        //Ԥ����sql���
        PreparedStatement psmt = con.prepareStatement(sql);
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setInt(1, id);
        //ִ��SQL���
        psmt.execute();    
    }
    
  //��ѯ�����û�(�����û���ȥ��ѯ)
    public User get(String userName) throws SQLException{
        User u=null;
        Connection con=DBUtilPlus.getConnection();//�����õ����ݿ������
        String sql="" + 
                "select * from userinfo "+
                "where user_name=?";//������?��ʾ���൱��ռλ��;��mysql�����ں���current_date()����ȡ��ǰ����
        //Ԥ����sql���
        PreparedStatement psmt = con.prepareStatement(sql);
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setString(1, userName);
        //ִ��SQL���
        /*psmt.execute();*///execute()������ִ�и������ݿ�����������������޸ġ�ɾ����;executeQuery()��ִ�в�ѯ����
        ResultSet rs = psmt.executeQuery();//����һ�������
        //���������
        while(rs.next()){
            u=new User();
            u.setId(rs.getInt("id"));
            u.setUserName(rs.getString("user_name"));
            u.setPassWord(rs.getString("pass_word"));
            u.setPhoneNumber(rs.getString("phone_number"));
            //rs.getDate("birthday")��õ���java.sql.Date���͡�ע�⣺java.sql.Date������java.util.Date���͵��Ӽ����������ﲻ��Ҫ����ת���ˡ�        
            u.setUpDate(rs.getDate("update_date"));
        }
        return u;
    }

  //�õ���ס���û���
    public User getRemember() throws SQLException{
        User u=null;
        Connection con=DBUtilPlus.getConnection();//�����õ����ݿ������
        String sql="" + 
                "select * from rememberusername "+
                "where id=1";//������?��ʾ���൱��ռλ��;��mysql�����ں���current_date()����ȡ��ǰ����
        //Ԥ����sql���
        PreparedStatement psmt = con.prepareStatement(sql);
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
//        psmt.setString(1, userName);
        //ִ��SQL���
        /*psmt.execute();*///execute()������ִ�и������ݿ�����������������޸ġ�ɾ����;executeQuery()��ִ�в�ѯ����
        ResultSet rs = psmt.executeQuery();//����һ�������
        //���������
        while(rs.next()){
            u=new User();
//            u.setId(rs.getInt("id"));
            u.setUserName(rs.getString("lastusername"));
//            u.setPassWord(rs.getString("pass_word"));
//            u.setPhoneNumber(rs.getString("phone_number"));
            //rs.getDate("birthday")��õ���java.sql.Date���͡�ע�⣺java.sql.Date������java.util.Date���͵��Ӽ����������ﲻ��Ҫ����ת���ˡ�        
//            u.setUpDate(rs.getDate("update_date"));
        }
        return u;
    }
    
  //���ļ�ס���û���
    public void updateRememberUserName(String username) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//�����õ����ݿ������
        String sql="" + 
                "update rememberusername "+
                "id=1,"+
                "lastusername=?) "+
                "where id=1";//������?��ʾ���൱��ռλ��;��mysql�����ں���current_date()����ȡ��ǰ����
        //Ԥ����sql���
        PreparedStatement psmt = con.prepareStatement(sql);
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setString(1, username);
        //ע�⣺setDate()�����ڶ���������Ҫ����java.sql.Date���ͣ����Ǵ���������java.util.Date�����Ͳ�������Ҫ��һ��ת��
        //ִ��SQL���
        psmt.execute();    
    }
    
    public List<User> query() throws Exception{
        Connection con=DBUtilPlus.getConnection();
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("select user_name,phone_number from userinfo");
        List<User> us=new ArrayList<User>();
        User u=null;
        while(rs.next()){//��������������ݣ��ͻ�ѭ����ӡ����
            u=new User();
            u.setUserName(rs.getString("user_name"));
            u.setPhoneNumber(rs.getString("phone_number"));
            us.add(u);
        }
        return us;
    }
    
  //��ѯ�����û�(�����û�ID����Ϣȥ��ѯ)
    public User get(int id,String mobile) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//�����õ����ݿ������
        StringBuffer sb=new StringBuffer();
        sb.append("select * from userinfo ");
        sb.append("where id=? and phone_number=?");
        //Ԥ����sql���
        PreparedStatement psmt = con.prepareStatement(sb.toString());
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setInt(1, id);
        psmt.setString(2, mobile);    
        //ִ��SQL���
        /*psmt.execute();*///execute()������ִ�и������ݿ�����������������޸ġ�ɾ����;executeQuery()��ִ�в�ѯ����
        ResultSet rs = psmt.executeQuery();//����һ�������
        User u=null;
        //���������
        while(rs.next()){
        	u=new User();
            u.setId(rs.getInt("id"));
            u.setUserName(rs.getString("user_name"));
            u.setPassWord(rs.getString("pass_word"));
            u.setPhoneNumber(rs.getString("phone_number"));
            //rs.getDate("birthday")��õ���java.sql.Date���͡�ע�⣺java.sql.Date������java.util.Date���͵��Ӽ����������ﲻ��Ҫ����ת���ˡ�               
        }
        return u;
    }
    
    public static void main(String[] args) throws Exception {
        UserInfoOperation us=new UserInfoOperation();
        User u=new User();
//        u.setUserName("С��");
//        u.setId(422126199);
//        u.setPassWord("xiaoxia");
//        u.setPhoneNumber(1234567891);
//        us.addUser(u);
        
//        ArrayList<User> uu = (ArrayList<User>) us.query();
//        for(User uus : uu){
//        	 System.out.println(uus.getPhoneNumber());
//        }
        
        u=us.get(404, "15810305248");
        if(u!=null){
        	System.out.println(u.getUserName());
        	System.out.println(u.getPhoneNumber());
        	System.out.println(u.getPassword());
        	System.out.println(u.getId());
        	System.out.println((java.sql.Date)u.getUpDate());
        }else{
        	System.out.println("empty!!");
        }
    }
}