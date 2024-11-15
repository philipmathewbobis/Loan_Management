package Loan_Admin;

import Database_Manager.DatabaseAdmin;
import User_Information.Employment;
import User_Information.Account;

import java.util.ArrayList;

public class Loan_Application {
    public void assessApplication(String loanType, double loanAmount, int loanDuration, String loanPurpose, double totalMonthlyDebts,String accountId){
        ArrayList<Account> accounts = DatabaseAdmin.loadFromAccountDB(); // load and initialize all instance of Person class from database
        ArrayList<Employment> employments = DatabaseAdmin.loadEmployedFromDB(); // loan and initialize all instance of Employment class from database
        boolean isAccountIdFound = false;

        for (Account account : accounts){ // Iterate every instance of Account class
            for (Employment employment : employments) { // Iterate every instance of Employment class
                if (account.getAccountId().equals(accountId) && employment.getAccountId().equalsIgnoreCase(accountId)) { // Check the customerId, if found create instance of Loan details class
                    isAccountIdFound = true; // Valid customer id found
                    LoanDetails loanDetails = new LoanDetails(Loan_Validation.typeOfLoan(loanType,employment), loanAmount, loanPurpose, Loan_Validation.typeLoanDuration(loanType,loanDuration), totalMonthlyDebts);
                    loanDetails.setDtiRatio(loanDetails, employment); // calling the setter for dtiRatio and calculate it's dti ratio
                    loanDetails.setMonthlyPayment(loanDetails); // calling the setter to calculate the monthly payment of customer
                    DatabaseAdmin.insertLoanApplicationToDB(loanDetails, account); // Pass the instance of the following class to the DatabaseAdmin class
                }
            }
        }
        if (!isAccountIdFound){ // Check if customerId is not found
            System.out.println("Incorrect customer id.");
        }
    }

    public void findCustomerApplication(String accountId){
        ArrayList<LoanDetails> loanApplications = DatabaseAdmin.loadLoanApplicationsFromDB(); // Loan and store every instance of LoanDetails class in the arraylist
        for (LoanDetails loanDetails : loanApplications){ // iterate every instance of LoanDetails class in an Arraylist
            if (loanDetails.getAccountId().equals(accountId)){ // Check if the customerId in the LoanDetails class is the one user is finding
                System.out.println(loanDetails.getAccountId());
                System.out.println(loanDetails.getLoanApplicationId());
                System.out.println(loanDetails.getLoanType());
                System.out.println(loanDetails.getLoanAmountRequested());
                System.out.println(loanDetails.getMonthlyPayment());
                System.out.println(loanDetails.getLoanDuration());
                System.out.println(loanDetails.getDtiRatio());
                System.out.println(loanDetails.getTotalMonthlyDebtPayments());
                System.out.println(loanDetails.getLoanPurpose());
                System.out.println(loanDetails.getLoanStatus());
            }
        }
    }

}
