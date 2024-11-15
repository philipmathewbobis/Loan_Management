package User_Information;

import Database_Manager.DatabaseAdmin;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Register {

    public void completeEmployedAccount(LocalDate startDate,boolean isRetired,LocalDate retirementDate,String jobTitle,double salary,double annualIncome,String employmentStatus,String accountId){
        Employment employment = new Employment(jobTitle,salary,annualIncome,isEmploymentStatus(employmentStatus),hasWorkedOverAYear(startDate,isRetired,retirementDate));
        employment.setAccountId(accountId); // set the employment to your account customerId
        boolean accountIdFound = false;

        ArrayList<Account> accounts = DatabaseAdmin.loadFromAccountDB(); // Load record from database
        for (Account account : accounts){ // Iterate every instance of Persons class in arraylist person
            if (account.getAccountId().equals(employment.getAccountId())) { // Check if every instance of person class customerId matched the user's input of customerId
                accountIdFound = true;
                DatabaseAdmin.registerEmployedCustomer(employment);
                break; // break the loop once the match found to prevent unnecessary iterations and further add to the database
            }
        }
        if (accountIdFound){
            System.out.println("Successfully added to the database");
        }else {
            System.out.println("Incorrect account id.");
        }
    }
    public static boolean isEmploymentStatus(String employmentStatus){
        if (employmentStatus.equalsIgnoreCase("employed")){  // Checks if the employment status is employed return true if employed
            return true;
        }else if (employmentStatus.equalsIgnoreCase("retired")){
            return false;
        }
        return false;
        // otherwise it will return false
    }

    public String employmentStatus(boolean status){
        if (status){
            return "Employed";
        }else {
            return "Retired";
        }
    }

    public static String hasWorkedOverAYear(LocalDate startDate, boolean isRetired, LocalDate retirementDate){
        // Format a specific type of date
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = isRetired ? retirementDate : LocalDate.now();

        // The period.between() method returns a Period object representing the time difference between the two dates

        Period period = Period.between(startDate, endDate);

        int years = period.getYears(); // get period of years
        int months = period.getMonths(); // get period of months
        int days = period.getDays(); // get period of days

        return years + " years, " + months + " months, and " + days + " days"; // returns a formatted string with the specific periods of time
    }

    public boolean isAccountEmployed(String accountId){
        ArrayList<Employment> employedAccounts = DatabaseAdmin.loadEmployedFromDB();
        for (Employment employment : employedAccounts){
            if (employment.getAccountId().equals(accountId)){
                return true;
            }
        }
        return false;
    }

    public boolean checkEmployment(String employeeId, String accountId){
        DatabaseAdmin databaseAdmin = new DatabaseAdmin();
        ArrayList<Employment> employments = DatabaseAdmin.loadEmployedFromDB();
        for (Employment employment : employments){
            if (employment.getEmployedId().equalsIgnoreCase(employeeId) && employment.getAccountId().equalsIgnoreCase(accountId)){
                return true;
            }
        }
        return false;
    }

    public static LocalDate formatDate(String birthdate){
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Parse the string to a local date and return the value
        return LocalDate.parse(birthdate,formatter);
    }

}
