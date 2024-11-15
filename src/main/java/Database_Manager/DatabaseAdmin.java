package Database_Manager;

import Admin_Manager.Admin;
import Admin_Manager.EmployedAccountsLoan;
import Loan_Admin.LoanDetails;
import Loan_Admin.Loan_Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import User_Information.Employment;
import User_Information.Register;
import User_Information.Account;
public class DatabaseAdmin {
    Loan_Validation loanValidation = new Loan_Validation(); // Create instance of Loan_Validation Class
    private static String db_url = "jdbc:mariadb://127.0.0.1:3306/loan_management";
    private static String user = "user";
    private static String pass = "";

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAdmin.class);
    public static void accountRegistration(Account account) {
        String sqlQuery = "INSERT INTO accounts (AccountId,First_Name,Middle_Name,Last_Name,BirthDate,Age,EmailAddress,nationality,Valid_Id,username,password)" +
                "values (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(db_url,user,pass)){
            // Creating or preparing the sql query with the database
            // Connection class allows you to interact with the database allowing you to execute queries and retrieve data
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1,account.getAccountId());
            preparedStatement.setString(2,account.getFirstName());
            preparedStatement.setString(3,account.getMiddleName());
            preparedStatement.setString(4,account.getLastName());
            preparedStatement.setDate(5,Date.valueOf(account.getDateOfBirth()));
            preparedStatement.setInt(6,account.getAge());
            preparedStatement.setString(7,account.getEmailAddress());
            preparedStatement.setString(8,account.getNationality());
            preparedStatement.setString(9,account.getValidId());
            preparedStatement.setString(10,account.getUsername());
            preparedStatement.setString(11,account.getPassword());

            //This method used for executing sql statement like inserting and updating data in the database
            preparedStatement.executeUpdate();
            System.out.println("Successfully added to the database.");
            System.out.println("Please take note of your Id: " + account.getAccountId());
        }catch (SQLException e){
            logger.error("Error establishing connection with the database." + e.getMessage());
        }
    }

    public static void registerEmployedCustomer(Employment employment){
        String sqlQuery = "INSERT INTO employed_accounts (AccountId,Job_Title,Employment_Period,Salary,Annual_Income,Employment_Status,Employee_Id)" +
                "values (?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(db_url,user,pass)){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1,employment.getAccountId());
            preparedStatement.setString(2,employment.getJobTitle());
            preparedStatement.setString(3,employment.getEmploymentPeriod());
            preparedStatement.setDouble(4,employment.getSalary());
            preparedStatement.setDouble(5,employment.getAnnualIncome());
            preparedStatement.setString(6,employment.getEmploymentStatus());
            preparedStatement.setString(7,employment.getEmployedId());

            preparedStatement.executeUpdate();
            System.out.println("Successfully added to database.");
            System.out.println("Employee id: " + employment.getEmployedId());
        }catch (SQLException e){
            logger.error("Error establishing connection with database." + e.getMessage());
        }
    }

    public static ArrayList<Employment> loadEmployedFromDB(){
        ArrayList<Employment> employments = new ArrayList<>();
        Register register = new Register(); // Create instance of Register Class

        try(Connection connection = DriverManager.getConnection(db_url,user,pass)){
            String query = "SELECT * FROM employed_accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String accountId = resultSet.getString("AccountId");
                String jobTitle = resultSet.getString("Job_Title");
                String employmentPeriod = resultSet.getString("Employment_Period");
                double salary = resultSet.getDouble("Salary");
                double annualIncome = resultSet.getDouble("Annual_Income");
                String employmentStatus = resultSet.getString("Employment_Status");
                String employeeId = resultSet.getString("Employee_Id");

                Employment employment = new Employment(jobTitle,salary,annualIncome, Register.isEmploymentStatus(employmentStatus), employmentPeriod);
                employment.setAccountId(accountId); // set customer id of customer for employment
                employment.setEmployedId(employeeId);
                employments.add(employment);
            }
        }catch (SQLException e){
            logger.error("Error establishing connection with database.");
        }
        return employments;
    }

    public static void insertLoanApplicationToDB(LoanDetails loanDetails, Account account){
        String sqlQuery = "INSERT INTO loan_application (AccountId,Loan_Id,Loan_Type,Amount_Requested,Duration_Months,Monthly_Payment,DTI_Ratio,TotalMonthly_DebtPayments,Loan_Purpose,Loan_Status)" +
                "values (?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(db_url,user,pass)) {
            if (loanDetails.getLoanType().equalsIgnoreCase("personal loan") ||
                    loanDetails.getLoanType().equalsIgnoreCase("home loan") ||
                    loanDetails.getLoanType().equalsIgnoreCase("auto loan")) {
                if (Loan_Validation.assessDtiRatio(loanDetails.getDtiRatio())) { // if true means the customers dti ratio is sufficient in loan program
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                    preparedStatement.setString(1,account.getAccountId());
                    preparedStatement.setString(2,loanDetails.getLoanApplicationId());
                    preparedStatement.setString(3,loanDetails.getLoanType());
                    preparedStatement.setDouble(4,loanDetails.getLoanAmountRequested());
                    preparedStatement.setInt(5,loanDetails.getLoanDuration());
                    preparedStatement.setDouble(6,loanDetails.getMonthlyPayment());
                    preparedStatement.setDouble(7,loanDetails.getDtiRatio());
                    preparedStatement.setDouble(8,loanDetails.getTotalMonthlyDebtPayments());
                    preparedStatement.setString(9,loanDetails.getLoanPurpose());
                    preparedStatement.setString(10,loanDetails.getLoanStatus());

                    // Execute preparedStatement method for inserting and updating data in database
                    preparedStatement.executeUpdate();
                    System.out.println("Successfully added to the database.");
                    System.out.println("Check Again your application if approve. Thanks!!");
                } else {
                    System.out.println("You did not pass the dti ratio, you are not suitable for loan program.");
                }
            } else {
                System.out.println("Not Applicable for any loan, please choose other types of loans.");
            }
        }catch (SQLException e){
            logger.error("Error establishing connection with the database.");
        }
    }

    // Getting Records from database and return as an Arraylist with set of instance of Employment class
    public static ArrayList<Account> loadFromAccountDB(){
        ArrayList<Account> person = new ArrayList<>(); // Initialize Arraylist employments with type of Employment class properties and methods
        try(Connection connection = DriverManager.getConnection(db_url,user,pass)){
            String query = "SELECT * FROM accounts"; // Define the query
            PreparedStatement preparedStatement = connection.prepareStatement(query); // Initialize the connect.preparedStatement method to create an object of PreparedStatement
            ResultSet resultSet = preparedStatement.executeQuery(); // execute query and receive the result

            while (resultSet.next()){ // Retrieve every row or iterate every row in database
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String accountId = resultSet.getString("AccountId");
                String firstName = resultSet.getString("First_Name");
                String middleName = resultSet.getString("Middle_Name");
                String lastName = resultSet.getString("Last_Name");
                Date birthDate = resultSet.getDate("BirthDate");
                String emailAddress = resultSet.getString("EmailAddress");
                String nationality = resultSet.getString("nationality");
                String governmentId = resultSet.getString("Valid_Id");

                Account account = new Account(username,password,firstName,middleName,lastName,birthDate.toLocalDate(),emailAddress,nationality,governmentId);
                account.setAccountId(accountId); // set customerId from database
                person.add(account); // Add every instance in the arraylist
            }

        }catch (SQLException e){
            logger.error("Error establishing connection with database.");
        }
        return person; // return the arraylist
    }

    public static ArrayList<LoanDetails> loadLoanApplicationsFromDB(){
        ArrayList<LoanDetails> loanDetails = new ArrayList<>(); // Initialize arraylist of LoanDetails class
        try(Connection connection = DriverManager.getConnection(db_url,user,pass)){
            String query = "SELECT * FROM loan_application";
            PreparedStatement preparedStatement = connection.prepareStatement(query);// use the connection class to create a prepared statement and interact with the database
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute query and retrieve the records

            while (resultSet.next()){ // iterate every row in the database or the result set from database
                String accountId = resultSet.getString("accountId");
                String loanId = resultSet.getString("Loan_Id");
                String loanType = resultSet.getString("Loan_Type");
                double amountRequested = resultSet.getDouble("Amount_Requested");
                int durationMonths = resultSet.getInt("Duration_Months");
                double dtiRatio = resultSet.getDouble("DTI_Ratio");
                double totalMonthlyDebtPayments = resultSet.getDouble("TotalMonthly_DebtPayments");
                String loanPurpose = resultSet.getString("Loan_Purpose");
                String loanStatus = resultSet.getString("Loan_Status");

                LoanDetails loanDetails1 = new LoanDetails(loanType,amountRequested,loanPurpose,durationMonths,totalMonthlyDebtPayments);
                loanDetails1.setLoanApplicationId(loanId); // Set the loanId from the database for every instance of the class
                loanDetails1.setAccountId(accountId); //  set also the customerId of customer for his/her loan applications
                loanDetails1.setLoanStatus(loanStatus); // set also the loanStatus from database
                loanDetails1.setDTIRatio(dtiRatio); // set the dtiRatio from database which is already computed before

                loanDetails.add(loanDetails1); // add every instance in the arraylist
            }


        }catch (SQLException e){
            logger.error("Error establishing connection with database.");
        }
        return loanDetails; // return arraylist
    }

    public static void insertAdminToDB(Admin admin){
        String sqlQuery = "insert into table_name (Admin_Id,First_Name,Middle_Name,Last_Name,username,password)" +
                "values (?,?,?,?,?,?)";
        try(Connection connection = DriverManager.getConnection(db_url,user,pass)){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, admin.getAdminId());
            preparedStatement.setString(2,admin.getFirstName());
            preparedStatement.setString(3,admin.getMiddleName());
            preparedStatement.setString(4,admin.getLastName());
            preparedStatement.setString(5,admin.getUsername());
            preparedStatement.setString(6,admin.getPassword());
            preparedStatement.executeUpdate();

            System.out.println("Successfully insert in th database");
        }catch (SQLException e){
            logger.error("Error establishing connection with database.");
        }
    }

    public static ArrayList<Admin> loadAdminAccountsFromDB(){
        ArrayList<Admin> adminAccounts = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(db_url,user,pass)){
            String sql = "SELECT * FROM table_name";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();;

            while (resultSet.next()){
                String adminId = resultSet.getString("Admin_Id");
                String firstName = resultSet.getString("First_Name");
                String middleName = resultSet.getString("Middle_Name");
                String lastName = resultSet.getString("Last_Name");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");

                Admin admin = new Admin(userName,password,firstName,middleName,lastName);
                admin.setAdminId(adminId);
                adminAccounts.add(admin);
            }
        }catch (SQLException e){
            logger.error("Error establishing connection with database.");
        }
        return adminAccounts;
    }

    public static ArrayList<EmployedAccountsLoan> loadEmployedAccountsLoan(){
        ArrayList<EmployedAccountsLoan> employedAccountsLoan = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(db_url,user,pass)){
            String sqlQuery = "select employed_accounts.AccountId,accounts.First_Name,accounts.Middle_Name,accounts.Last_Name," +
                    "employed_accounts.Job_Title,employed_accounts.Employment_Period,employed_accounts.Salary," +
                    "employed_accounts.Annual_Income,loan_application.Loan_Id,loan_application.Loan_Type,loan_application.Amount_Requested," +
                    "loan_application.Duration_Months,loan_application.Monthly_Payment,loan_application.Loan_Purpose,loan_application.Loan_Status,employed_accounts.Employment_Status" +
                    "FROM employed_accounts JOIN accounts" +
                    "ON employed_accounts.AccountId = accounts.AccountId" +
                    "JOIN loan_application" +
                    "ON accounts.AccountId = loan_application.AccountId";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String accountId = resultSet.getString("AccountId");
                String firstName = resultSet.getString("First_Name");
                String middleName = resultSet.getString("Middle_Name");
                String lastName = resultSet.getString("Last_Name");
                String jobTitle = resultSet.getString("Job_Title");
                String employmentPeriod = resultSet.getString("Employment_Period");
                double salary = resultSet.getDouble("Salary");
                double annualIncome = resultSet.getDouble("Annual_Income");
                String loanId = resultSet.getString("Loan_Id");
                String loanType = resultSet.getString("Loan_Type");
                double amountRequested = resultSet.getDouble("Amount_Requested");
                int durationPayment = resultSet.getInt("Duration_Months");
                double monthlyPayment = resultSet.getDouble("Monthly_Payment");
                String loanPurpose = resultSet.getString("Loan_Purpose");
                String loanStatus = resultSet.getString("Loan_Status");
                String employmentStatus = resultSet.getString("Employment_Status");

                EmployedAccountsLoan employedAccountsLoanApplication = new EmployedAccountsLoan(accountId,firstName,middleName,lastName,jobTitle,employmentPeriod,loanId,loanType,loanPurpose,loanStatus,salary,annualIncome,amountRequested,durationPayment,monthlyPayment,employmentStatus);
                employedAccountsLoan.add(employedAccountsLoanApplication);
            }
        }catch (SQLException e){
            logger.error("Error establishing connection with database.");
        }
        return employedAccountsLoan;
    }
}
