package Loan_Admin;


import User_Information.Employment;


public class Loan_Validation {
    public static String typeOfLoan(String loanType,Employment employment){
        if (loanType.equalsIgnoreCase("home loan") && incomeAssessmentHomeLoan(employment.getSalary())){
            return "home Loan";
        } else if (loanType.equalsIgnoreCase("auto loan") && incomeAssessmentAutoLoan(employment.getSalary())) {
            return "auto Loan";
        }else if (loanType.equalsIgnoreCase("personal loan") && incomeAssessmentPersonalLoan(employment.getSalary())){
            return "personal Loan";
        }
        return "Not applicable in any types of Loans.";
    }

    public static boolean incomeAssessmentPersonalLoan(double salary){
        // return true if income is equal or greater than 10k
        // Applicable for personal loan
        return salary >= 10000;
    }

    public static boolean incomeAssessmentAutoLoan(double salary){
        // return true if income is greater than 30k
        // Applicable for Auto-Loan
        return salary >= 30000;
    }

    public static boolean incomeAssessmentHomeLoan(double salary){
        // return true if income is greater than 50k
        // Applicable for Home Loan or Mortgage
        return salary >= 50000;
    }

    public static double dtiRatio(LoanDetails loanDetails, Employment employment){
        // returns the dti ratio percentage to assess if the customers monthly income is sufficient
       return (loanDetails.getTotalMonthlyDebtPayments() / employment.getAnnualIncome()) * 100;
    }

    public static int typeLoanDuration(String loanType,int duration){
        // Check the loan type and duration for the type of loan and convert duration from years to months
        if (loanType.equalsIgnoreCase("personal loan") && duration >= 1 && duration <= 5){
            return duration * 12;
        } else if (loanType.equalsIgnoreCase("auto loan") && duration >= 3 && duration <= 7){
            return duration * 12;
        } else if (loanType.equalsIgnoreCase("home loan") && duration >= 10 && duration <= 20){
            return duration * 12;
        }
        return 0; // return 0 if loan type or duration is invalid
    }

    public static double calculateInterestRate(LoanDetails loanDetails){
        if (loanDetails.getLoanType().equalsIgnoreCase("personal loan")){
            if (loanDetails.getDtiRatio() <= 20) return 0.055;
            if (loanDetails.getDtiRatio() <= 30) return 0.085;
            if (loanDetails.getDtiRatio() <= 35) return 0.12;
        } else if (loanDetails.getLoanType().equalsIgnoreCase("auto loan")) {
            if (loanDetails.getDtiRatio() <= 20) return 0.045;
            if (loanDetails.getDtiRatio() <= 30) return 0.075;
            if (loanDetails.getDtiRatio() <= 35) return 0.095;
        } else if (loanDetails.getLoanType().equalsIgnoreCase("home loan")) {
            if (loanDetails.getDtiRatio() <= 20) return 0.035;
            if (loanDetails.getDtiRatio() <= 30) return 0.045;
            if (loanDetails.getDtiRatio() <= 35) return 0.06;
        }
        return 0; // return 0 for invalid loan type
    }

    public static double calculateMonthlyPayment(LoanDetails loanDetails){
        double interestRate = calculateInterestRate(loanDetails); // get the annual interest rate of customer depending on his/her dti ratio
        double monthlyInterestRate = interestRate / 12; // divide the annual interest rate by 12 to get the monthly interest rate
        int loanDurationMonths = loanDetails.getLoanDuration(); // get the loan duration of customer
        double loanAmount = loanDetails.getLoanAmountRequested(); // get the loan amount request by customer

        if (monthlyInterestRate == 0){
            return  loanAmount / loanDurationMonths; // if 0% interest, simply divide principal by months
        }

        // Return the monthly interest payment
        return (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanDurationMonths)) /
                (Math.pow(1 + monthlyInterestRate, loanDurationMonths) - 1);
    }

    public static boolean assessDtiRatio(double dtiRatio){
        if (dtiRatio >= 36.0){ // Above or equal to 36% is risky and may not approve
            return false;
        } else if (dtiRatio >= 30.0 && dtiRatio <= 35.0) { // Considered acceptable but still raises financial red flags
            return true;
        }else if (dtiRatio > 20.0 && dtiRatio < 30.0){ // Considered Acceptable, but may still evaluate with other factors
            return true;
        }
        return true; // or else below 20% return true, Generally considered as good.
    }



    public static String approvalByAdmin(boolean assessment){
        if (assessment){
            return "Approved";
        }
        return "Reject";
    }

}

