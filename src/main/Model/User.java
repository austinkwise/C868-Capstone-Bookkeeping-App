package main.Model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String orgName;
    private String name;
    private String phone;
    private String email;

    public User(int userId, String username, String password, String name, String phone, String email, String orgName){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.orgName = orgName;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public User(){};

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
