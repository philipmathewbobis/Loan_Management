package Admin_Manager;

public class Admin {

    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String password;
    private String adminId;

    public Admin(String username,String password,String firstName,String middleName,String lastName){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.adminId = generateAdminID();
    }

    public void setAdminId(String adminId){this.adminId = adminId;}

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getMiddleName(){
        return this.middleName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getAdminId(){
        return this.adminId;
    }
    public static String generateAdminID(){
        return java.util.UUID.randomUUID().toString();
    }
}
