package database;
import java.util.Date;
public class User {
    private Integer id;
    private String userName;
    private String passWord;
    private String phoneNumber;
    private Date upDate;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }   
    public Date getUpDate(){
        return upDate;
    }
    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }
}