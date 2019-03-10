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

	//添加用户
    public void addUser(User u) throws Exception{
        Connection con=DBUtilPlus.getConnection();//首先拿到数据库的连接
        String sql="" + 
        		"insert into userinfo"+
        		"(id,user_name,pass_word,update_date,phone_number) "+
                "values("+
                "?,?,?,current_date(),?)";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(1, u.getId());
        psmt.setString(2, u.getUserName());
        psmt.setString(3, u.getPassword());
        psmt.setString(4, u.getPhoneNumber());
        //注意：setDate()函数第二个参数需要的是java.sql.Date类型，我们传进来的是java.util.Date，类型不符，需要做一下转换
        //执行SQL语句
        psmt.execute();
        /**
         * prepareStatement这个方法会将SQL语句加载到驱动程序conn集成程序中，但是并不直接执行
         * 而是当它调用execute()方法的时候才真正执行；
         * 
         * 上面SQL中的参数用?表示，相当于占位符，然后在对参数进行赋值。
         * 当真正执行时，这些参数会加载在SQL语句中，把SQL语句拼接完整才去执行。
         * 这样就会减少对数据库的操作
         */
    }
    
  //更新用户
    public void updateUser(User u) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//首先拿到数据库的连接
        String sql="" + 
                "update userinfo "+
                "set id=?,user_name=?,pass_word=?,"+
                "update_date=current_date(),phone_number=? "+
                "where id=?";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(1, u.getId());
        psmt.setString(2, u.getUserName());
        psmt.setString(3, u.getPassword());
        //注意：setDate()函数第二个参数需要的是java.sql.Date类型，我们传进来的是java.util.Date，类型不符，需要做一下转换
        psmt.setString(4, u.getPhoneNumber());
        psmt.setInt(5, u.getId());
        //执行SQL语句
        psmt.execute();    
    }
    
  //删除用户
    public void delUser(Integer id) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//首先拿到数据库的连接
        String sql="" + 
                "delete from info "+
                "where id=?";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(1, id);
        //执行SQL语句
        psmt.execute();    
    }
    
  //查询单个用户(根据用户名去查询)
    public User get(String userName) throws SQLException{
        User u=null;
        Connection con=DBUtilPlus.getConnection();//首先拿到数据库的连接
        String sql="" + 
                "select * from userinfo "+
                "where user_name=?";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setString(1, userName);
        //执行SQL语句
        /*psmt.execute();*///execute()方法是执行更改数据库操作（包括新增、修改、删除）;executeQuery()是执行查询操作
        ResultSet rs = psmt.executeQuery();//返回一个结果集
        //遍历结果集
        while(rs.next()){
            u=new User();
            u.setId(rs.getInt("id"));
            u.setUserName(rs.getString("user_name"));
            u.setPassWord(rs.getString("pass_word"));
            u.setPhoneNumber(rs.getString("phone_number"));
            //rs.getDate("birthday")获得的是java.sql.Date类型。注意：java.sql.Date类型是java.util.Date类型的子集，所以这里不需要进行转换了。        
            u.setUpDate(rs.getDate("update_date"));
        }
        return u;
    }

  //得到记住的用户名
    public User getRemember() throws SQLException{
        User u=null;
        Connection con=DBUtilPlus.getConnection();//首先拿到数据库的连接
        String sql="" + 
                "select * from rememberusername "+
                "where id=1";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
//        psmt.setString(1, userName);
        //执行SQL语句
        /*psmt.execute();*///execute()方法是执行更改数据库操作（包括新增、修改、删除）;executeQuery()是执行查询操作
        ResultSet rs = psmt.executeQuery();//返回一个结果集
        //遍历结果集
        while(rs.next()){
            u=new User();
//            u.setId(rs.getInt("id"));
            u.setUserName(rs.getString("lastusername"));
//            u.setPassWord(rs.getString("pass_word"));
//            u.setPhoneNumber(rs.getString("phone_number"));
            //rs.getDate("birthday")获得的是java.sql.Date类型。注意：java.sql.Date类型是java.util.Date类型的子集，所以这里不需要进行转换了。        
//            u.setUpDate(rs.getDate("update_date"));
        }
        return u;
    }
    
  //更改记住的用户名
    public void updateRememberUserName(String username) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//首先拿到数据库的连接
        String sql="" + 
                "update rememberusername "+
                "id=1,"+
                "lastusername=?) "+
                "where id=1";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setString(1, username);
        //注意：setDate()函数第二个参数需要的是java.sql.Date类型，我们传进来的是java.util.Date，类型不符，需要做一下转换
        //执行SQL语句
        psmt.execute();    
    }
    
    public List<User> query() throws Exception{
        Connection con=DBUtilPlus.getConnection();
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("select user_name,phone_number from userinfo");
        List<User> us=new ArrayList<User>();
        User u=null;
        while(rs.next()){//如果对象中有数据，就会循环打印出来
            u=new User();
            u.setUserName(rs.getString("user_name"));
            u.setPhoneNumber(rs.getString("phone_number"));
            us.add(u);
        }
        return us;
    }
    
  //查询单个用户(根据用户ID等信息去查询)
    public User get(int id,String mobile) throws SQLException{
        Connection con=DBUtilPlus.getConnection();//首先拿到数据库的连接
        StringBuffer sb=new StringBuffer();
        sb.append("select * from userinfo ");
        sb.append("where id=? and phone_number=?");
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sb.toString());
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(1, id);
        psmt.setString(2, mobile);    
        //执行SQL语句
        /*psmt.execute();*///execute()方法是执行更改数据库操作（包括新增、修改、删除）;executeQuery()是执行查询操作
        ResultSet rs = psmt.executeQuery();//返回一个结果集
        User u=null;
        //遍历结果集
        while(rs.next()){
        	u=new User();
            u.setId(rs.getInt("id"));
            u.setUserName(rs.getString("user_name"));
            u.setPassWord(rs.getString("pass_word"));
            u.setPhoneNumber(rs.getString("phone_number"));
            //rs.getDate("birthday")获得的是java.sql.Date类型。注意：java.sql.Date类型是java.util.Date类型的子集，所以这里不需要进行转换了。               
        }
        return u;
    }
    
    public static void main(String[] args) throws Exception {
        UserInfoOperation us=new UserInfoOperation();
        User u=new User();
//        u.setUserName("小夏");
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