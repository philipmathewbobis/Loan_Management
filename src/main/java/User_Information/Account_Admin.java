package User_Information;

import Loan_Admin.Loan_Application;
import org.mindrot.jbcrypt.BCrypt; // Library for BCrypt class for hashing passwords

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Database_Manager.DatabaseAdmin;
public class Account_Admin {
    Scanner scan = new Scanner(System.in);
    Loan_Application loanApplication = new Loan_Application();
    Register register = new Register(); // Create instance of Register class
    public void createAccount(String username, String password, String firstName, String middleName, String lastName, String birthDate,String emailAddress,String nationality,String governmentId){
        Account account = new Account(username,hashPassword(password),firstName,middleName,lastName,register.formatDate(birthDate),emailAddress,nationality,governmentId);
        DatabaseAdmin.accountRegistration(account);
        System.out.println("You successfully registered.");
    }

    public void logIn(String username,String password){
        ArrayList<Account> accounts = DatabaseAdmin.loadFromAccountDB(); // Initialize every instance of Account class in arraylist accounts
        for(Account account : accounts){ // Iterate every instance
            if (account.getUsername().equals(username) && checkPassword(password, account.getPassword())){ // Check if username and password matched the accounts in the database
                System.out.println("Open User dashboard.");
                System.out.println("Welcome " + account.getUsername());
                System.out.println("Your Account id: " + account.getAccountId());
                menu();
                return;
            }
        }
        System.out.println("Login failed: " + username);
    }

    public void menu(){
        System.out.println("1.Register Employment");
        System.out.println("2.Apply Loan Application");
        System.out.print("Choose: ");
        int choose = scan.nextInt();
        scan.nextLine();

        switch (choose){
            case 1 -> registerEmployment();
            case 2 -> {
                int loanDuration = 0;
                System.out.print("Enter account id: ");
                String accountId = scan.nextLine();

                System.out.print("Enter employee id: ");
                String employeeId = scan.nextLine();

                if (register.checkEmployment(employeeId,accountId)){
                    System.out.println("Available loan types: "
                    + "\nPersonal Loan - Auto Loan - Home Loan");

                    System.out.print("Enter loan type: ");
                    String loanType = scan.nextLine();

                    System.out.print("Enter loan amount: ");
                    double loanAmount = scan.nextDouble();
                    scan.nextLine();

                    System.out.print("Enter purpose of loan: ");
                    String loanPurpose = scan.nextLine();

                    System.out.print("Enter other monthly debt payments in total: ");
                    double monthlyDebtPayments = scan.nextDouble();
                    scan.nextLine();

                    if (loanType.equalsIgnoreCase("Personal Loan")){
                        System.out.println("List of Loan Duration period" +
                                "\n1 year to 5 years");
                        System.out.print("Enter loan duration period: ");
                        loanDuration = scan.nextInt();
                        scan.nextLine();
                    } else if (loanType.equalsIgnoreCase("Auto Loan")) {
                        System.out.println("List of Loan Duration period" +
                                "\n3 years to 7 years");
                        System.out.print("Enter loan duration period: ");
                        loanDuration = scan.nextInt();
                        scan.nextLine();
                    }else if (loanType.equalsIgnoreCase("Home Loan")) {
                        System.out.println("List of Loan Duration period" +
                                "\n10 years to 20 years");
                        System.out.print("Enter loan duration period: ");
                        loanDuration = scan.nextInt();
                        scan.nextLine();
                    }
                    loanApplication.assessApplication(loanType,loanAmount,loanDuration,loanPurpose,monthlyDebtPayments,accountId);
                }else {
                    System.out.println("Invalid account id.");
                }
            }
        }
    }

    public void registerEmployment(){
        String jobTitle,employmentStatus,accountId,startEmploymentDate = null,retirementDate = null;
        double salary,annualIncome;
        boolean isRetired = false;

        System.out.print("Enter job title: ");
        jobTitle = scan.nextLine();

        System.out.print("Enter employment status (employed or retired): ");
        employmentStatus = scan.nextLine();

        if (employmentStatus.equalsIgnoreCase("retired")){
            isRetired = true; // update the isRetired to true

            System.out.print("Enter start of employment date(yyyy-mm-dd): ");
            startEmploymentDate = scan.nextLine();

            if (startEmploymentDate == null || startEmploymentDate.trim().isEmpty()){
                System.out.println("Start employment cannot be empty");
                return;
            }

            System.out.print("Enter retirement date(yyyy-mm-dd): ");
            retirementDate = scan.nextLine();

            if (retirementDate == null || retirementDate.trim().isEmpty()){
                System.out.println("Start employment cannot be empty");
                return;
            }
        }else if (register.isEmploymentStatus(employmentStatus)){
            System.out.print("Enter start of employment date(yyyy-mm-dd): ");
            startEmploymentDate = scan.nextLine();

            if (startEmploymentDate == null || startEmploymentDate.trim().isEmpty()){
                System.out.println("Start employment cannot be empty");
                return;
            }
        }else {
            System.out.println("Invalid employment status. If you are currently not employed you are not applicable to apply for this loan.");
            return;
        }

        System.out.print("Enter monthly salary: ");
        salary = scan.nextDouble();
        scan.nextLine();

        System.out.print("Enter annual income(total income in a year): ");
        annualIncome = scan.nextDouble();
        scan.nextLine();

        System.out.print("Enter account id: ");
        accountId = scan.nextLine();

        LocalDate formattedStartDate = Register.formatDate(startEmploymentDate);
        LocalDate formattedRetirementDate = null;

        if (isRetired){
            formattedRetirementDate = Register.formatDate(retirementDate);
        }

        register.completeEmployedAccount(formattedStartDate,isRetired,formattedRetirementDate,jobTitle,salary,annualIncome,employmentStatus,accountId);
    }

    public static String hashPassword(String plainPassword){
        // BYcrypt is a class part of BYcrypt library
        // Bycrypt.haspw() method hashes the provided plain text password using the second parameter called salt
        // By adding gensalt() or salt methods, it will add random value to passwords before they are being hashed
        // It's primary purpose is to enhance security by ensuring that if there are identical passwords,
        // the result will be different hash outputs.
        // this value will store both hashed and salt value to the database
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt()); // return hash password into string
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword){
        // return true if the users input password matched the hashed password from database
        return BCrypt.checkpw(plainPassword,hashedPassword);
    }
}
