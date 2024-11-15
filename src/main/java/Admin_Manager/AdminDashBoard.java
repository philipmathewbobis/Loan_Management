package Admin_Manager;

import Database_Manager.DatabaseAdmin;
import Loan_Admin.LoanDetails;
import Loan_Admin.Loan_Validation;
import User_Information.Account;
import User_Information.Account_Admin;
import User_Information.Employment;
import User_Information.Register;

import java.time.LocalDate;
import java.util.ArrayList;

public class AdminDashBoard {

    public void viewEmployedAccountsLoanApplications(){
        ArrayList<EmployedAccountsLoan> employedAccountsLoans = DatabaseAdmin.loadEmployedAccountsLoan();
        for (EmployedAccountsLoan employedAccountsLoan : employedAccountsLoans){
            System.out.println(employedAccountsLoan.getAccountId());
            System.out.println(employedAccountsLoan.getLoanId());
            System.out.println(employedAccountsLoan.getFirstName());
            System.out.println(employedAccountsLoan.getMiddleName());
            System.out.println(employedAccountsLoan.getLastName());
            System.out.println(employedAccountsLoan.getJobTitle());
            System.out.println(employedAccountsLoan.getEmploymentStatus());
            System.out.println(employedAccountsLoan.getEmploymentPeriod());
            System.out.println(employedAccountsLoan.getLoanType());
            System.out.println(employedAccountsLoan.getLoanPurpose());
            System.out.println(employedAccountsLoan.getLoanStatus());
            System.out.println(employedAccountsLoan.getSalary());
            System.out.println(employedAccountsLoan.getAnnualIncome());
            System.out.println(employedAccountsLoan.getAmountRequested());
            System.out.println(employedAccountsLoan.getMonthlyPayment());
            System.out.println(employedAccountsLoan.getDurationPaymentMonthly());
        }
    }

    public boolean directApplyLoan(String firstName, String middleName, String lastName, String birthDate, String emailAddress, String nationality, String validId, String username, String password, String jobTitle, LocalDate startDate,boolean isRetired,LocalDate retirementDate,double salary,double annualIncome,String employmentStatus,
    String loanType,double amountRequested,String loanPurpose,double monthlyDebt,int loanDurationMonths){
        ArrayList<Account> accounts = DatabaseAdmin.loadFromAccountDB();
        Account account = new Account(username, Account_Admin.hashPassword(password),firstName,middleName,lastName, Register.formatDate(birthDate),emailAddress,nationality,validId);
        Employment employment = new Employment(jobTitle,salary,annualIncome,Register.isEmploymentStatus(employmentStatus),Register.hasWorkedOverAYear(startDate,isRetired,retirementDate));
        LoanDetails loanDetails = new LoanDetails(Loan_Validation.typeOfLoan(loanType,employment),amountRequested,loanPurpose,loanDurationMonths,monthlyDebt);
        loanDetails.setDtiRatio(loanDetails,employment);
        loanDetails.setMonthlyPayment(loanDetails);
        employment.setAccountId(account.getAccountId());
        loanDetails.setAccountId(account.getAccountId());

        for (Account account1 : accounts){
            if (!account1.getUsername().equals(account.getUsername())){
                DatabaseAdmin.accountRegistration(account);
                DatabaseAdmin.registerEmployedCustomer(employment);
                DatabaseAdmin.insertLoanApplicationToDB(loanDetails,account);
                System.out.println("Successfully registered");
                return true;
            }
        }
        return false;
    }

}
