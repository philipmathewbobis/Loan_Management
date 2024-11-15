package User_Information;

import java.time.LocalDate;
import java.time.Period;

public class Account {

        private String username;
        private String password;
        private String firstName;
        private String middleName;
        private String lastName;
        private LocalDate dateOfBirth;
        private int age;
        private String emailAddress;
        private String nationality;
        private String validId;
        private String accountId;
    public Account(String username, String password, String firstName, String middleName, String lastName, LocalDate dateOfBirth, String emailAddress, String nationality, String governmentId){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.nationality = nationality;
        this.validId = governmentId;
        this.accountId = generateCustomerId();
        this.age = convertAge(dateOfBirth);
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public void setValidId(String validId){
        this.validId = validId;
    }
    public void setAccountId(String accountId){this.accountId = accountId;}
    public void setAge(int age){this.age = age;}
    public String getFirstName(){
        return this.firstName;
    }

    public String getMiddleName(){
        return this.middleName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public LocalDate getDateOfBirth(){
        return this.dateOfBirth;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public String getNationality(){
        return this.nationality;
    }

    public String getValidId(){
        return this.validId;
    }
    public String getAccountId(){
        return this.accountId;
    }
    public int getAge(){
        return this.age;
    }
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}

    public static String generateCustomerId(){
        // The UUID is a built-in class in java.util package for creating unique identifiers,
        // randomUUID() is a static method in the UUID class generates a randomly UUID.
        // Every time you call this method, it generates new and unique 128-bit value
        // and converts the UUID to string by appending the toString() method
        return "ACC" + java.util.UUID.randomUUID().toString();
    }

    public int convertAge(LocalDate dateOfBirth){
        Period period = Period.between(dateOfBirth, LocalDate.now());

        return period.getYears(); // return the age
    }
}
