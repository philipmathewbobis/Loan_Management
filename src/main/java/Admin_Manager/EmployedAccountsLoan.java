package Admin_Manager;

public class EmployedAccountsLoan {

    private String accountId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String jobTitle;
    private String employmentPeriod;
    private String loanId;
    private String loanType;
    private String loanPurpose;
    private String loanStatus;
    private String employmentStatus;
    private double salary;
    private double annualIncome;
    private double amountRequested;
    private int durationPaymentMonthly;
    private double monthlyPayment;


    public EmployedAccountsLoan(String accountId,String firstName,String middleName,String lastName,String jobTitle,String employmentPeriod,String loanId,String loanType,String loanPurpose,String loanStatus,double salary,double annualIncome,double amountRequested,int durationPaymentMonthly,double monthlyPayment,String employmentStatus){
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.employmentPeriod = employmentPeriod;
        this.loanId = loanId;
        this.loanType = loanType;
        this.loanPurpose = loanPurpose;
        this.loanStatus = loanStatus;
        this.salary = salary;
        this.annualIncome = annualIncome;
        this.amountRequested = amountRequested;
        this.durationPaymentMonthly = durationPaymentMonthly;
        this.monthlyPayment = monthlyPayment;
        this.employmentStatus = employmentStatus;
        this.middleName = middleName;
    }

    public String getAccountId(){return this.accountId;}
    public String getJobTitle(){return this.jobTitle;}
    public String getFirstName(){return this.firstName;}
    public String getMiddleName(){return this.middleName;}
    public String getLastName(){return this.lastName;}
    public String getEmploymentPeriod(){return this.employmentPeriod;}
    public String getEmploymentStatus(){return this.employmentStatus;}
    public String getLoanType(){return this.loanType;}
    public String getLoanId(){return this.loanId;}
    public String getLoanPurpose(){return this.loanPurpose;}
    public String getLoanStatus(){return this.loanStatus;}
    public double getSalary(){return this.salary;}
    public double getAnnualIncome(){return this.annualIncome;}
    public double getAmountRequested(){return this.amountRequested;}
    public double getMonthlyPayment(){return this.monthlyPayment;}
    public int getDurationPaymentMonthly(){return this.durationPaymentMonthly;}
}
