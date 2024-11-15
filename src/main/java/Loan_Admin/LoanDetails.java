package Loan_Admin;

import User_Information.Employment;

public class LoanDetails {
    Loan_Validation loanValidation = new Loan_Validation();

    private String loanType;
    private double loanAmountRequested;
    private String loanPurpose;
    private double totalMonthlyDebtPayments;
    private int loanDuration;
    private double monthlyPayment;
    private double dtiRatio;
    private String loanStatus;
    private String loanApplicationId;
    private String accountId;

    public LoanDetails(String loanType,double loanAmountRequested,String loanPurpose,int loanDuration,double totalMonthlyDebtPayments){
        this.loanType = loanType;
        this.loanAmountRequested = loanAmountRequested;
        this.loanPurpose = loanPurpose;
        this.loanDuration = loanDuration;
        this.totalMonthlyDebtPayments = totalMonthlyDebtPayments;
        this.loanApplicationId = generateApplicationId();
        this.loanStatus = "Pending";
    }

    public String getLoanType(){
        return this.loanType;
    }

    public double getLoanAmountRequested(){
        return this.loanAmountRequested;
    }

    public String getLoanPurpose(){
        return this.loanPurpose;
    }
    public double getDtiRatio(){return this.dtiRatio;}
    public int getLoanDuration(){return this.loanDuration;}
    public double getTotalMonthlyDebtPayments(){return this.totalMonthlyDebtPayments;}
    public String getLoanStatus(){return this.loanStatus;}
    public String getLoanApplicationId(){
        return this.loanApplicationId;
    }
    public String getAccountId(){
        return this.accountId;
    }
    public double getMonthlyPayment(){return this.monthlyPayment;}
    public void setLoanType(String loanType){
        this.loanType = loanType;
    }

    public void setLoanAmountRequested(double loanAmountRequested){
        this.loanAmountRequested = loanAmountRequested;
    }
    public void setLoanPurpose(String loanPurpose){
        this.loanPurpose = loanPurpose;
    }
    public void setDtiRatio(LoanDetails loanDetails,Employment employment){this.dtiRatio = Loan_Validation.dtiRatio(loanDetails,employment);}
    public void setDTIRatio(double dtiRatio){
        this.dtiRatio = dtiRatio;
    }
    public void setLoanStatus(String loanStatus){this.loanStatus = loanStatus;}
    public void setLoanApplicationId(String loanApplicationId){
        this.loanApplicationId = loanApplicationId;
    }
    public void setAccountId(String accountId){
        this.accountId = accountId;
    }
    public void setMonthlyPayment(LoanDetails loanDetails){
        this.monthlyPayment = Loan_Validation.calculateMonthlyPayment(loanDetails);
    }
    public static String generateApplicationId(){
        // The UUID is a built-in class in java.util package for creating unique identifiers,
        // randomUUID() is a static method in the UUID class generates a randomly UUID.
        // Every time you call this method, it generates new and unique 128-bit value
        // and converts the UUID to string by appending the toString() method
        return "LOAN" + java.util.UUID.randomUUID().toString();
    }
}
