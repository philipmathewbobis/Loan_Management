package Admin_Manager;

import Database_Manager.DatabaseAdmin;
import User_Information.Account_Admin;

import java.util.ArrayList;

public class RegisterLogin {

    public boolean registerAdminAccount(String username,String password,String firstName,String middleName,String lastName){
        Admin admin = new Admin(username,Account_Admin.hashPassword(username) , firstName, middleName, lastName);
        ArrayList<Admin> adminAccounts = DatabaseAdmin.loadAdminAccountsFromDB();

        for (Admin adminAccount : adminAccounts){
            if (!adminAccount.getUsername().equals(admin.getUsername())){
                DatabaseAdmin.insertAdminToDB(admin);
                return true;
            }
        }
        return false;
    }

    public boolean loginAdmin(String username,String password){
        ArrayList<Admin> adminAccounts = DatabaseAdmin.loadAdminAccountsFromDB();
        for(Admin admin : adminAccounts){
            if (admin.getUsername().equals(username) && Account_Admin.checkPassword(password,admin.getPassword())){
                System.out.println("Welcome to admin dashboard");
                return true;
            }
        }
        return false;
    }
}
