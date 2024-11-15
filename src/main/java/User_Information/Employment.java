package User_Information;

public class Employment{
    Register register = new Register();

    private String jobTitle;
    private String employmentPeriod;
    private double salary;
    private double annualIncome;
    private String employmentStatus;
    private String accountId;
    private String employedId;
    public Employment(String jobTitle,double salary,double annualIncome,boolean employmentStatus,String employmentPeriod){
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.annualIncome = annualIncome;
        this.employmentPeriod = employmentPeriod;
        this.employmentStatus = register.employmentStatus(employmentStatus);
        this.employedId = generateEmployeeId();
    }

    public void setJobTitle(String jobTitle){
        this.jobTitle = jobTitle;
    }
    public void setSalary(double salary){
        this.salary = salary;
    }

    public void setAnnualIncome(double annualIncome){
        this.annualIncome = annualIncome;
    }
    public void setAccountId(String accountId){this.accountId = accountId;}
    public void setEmployedId(String employedId){this.employedId = employedId;}
    public String getJobTitle(){
        return this.jobTitle;
    }

    public String getEmploymentPeriod(){
        return this.employmentPeriod;
    }

    public double getSalary(){
        return this.salary;
    }

    public double getAnnualIncome(){
        return this.annualIncome;
    }

    public String getEmploymentStatus(){
        return this.employmentStatus;
    }
    public String getAccountId(){return this.accountId;}
    public String getEmployedId(){return this.employedId;}

    public static String generateEmployeeId(){
        return "EM" + java.util.UUID.randomUUID().toString();
    }
}
