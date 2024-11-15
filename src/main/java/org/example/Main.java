package org.example;

import java.util.Scanner;
import User_Information.Account_Admin;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Account_Admin accountAdmin = new Account_Admin();

        while (true){
            System.out.println("Loan Management System");
            System.out.println("1.Create account");
            System.out.println("2.Log In");
            System.out.print("Choose: ");
            int choose = scan.nextInt();
            scan.nextLine();

            switch (choose){
                case 1 ->{
                    System.out.print("Create username: ");
                    String username = scan.nextLine();

                    System.out.print("Create password: ");
                    String password = scan.nextLine();

                    System.out.print("Enter first name: ");
                    String firstName = scan.nextLine();

                    System.out.print("Enter middle name: ");
                    String middleName = scan.nextLine();

                    System.out.print("Enter last name: ");
                    String lastName = scan.nextLine();

                    System.out.print("Enter date of birth (yyyy-mm-dd): ");
                    String dateOfBirth = scan.nextLine();

                    System.out.print("Enter email address: ");
                    String emailAddress = scan.nextLine();

                    System.out.print("Enter nationality: ");
                    String nationality = scan.nextLine();

                    System.out.print("Enter government id: ");
                    String governmentId = scan.nextLine();

                    accountAdmin.createAccount(username,password,firstName,middleName,lastName,dateOfBirth,emailAddress,nationality,governmentId);
                }
               case 2 -> {
                   System.out.print("Enter username: ");
                   String username = scan.nextLine();

                   System.out.print("Enter password: ");
                   String password = scan.nextLine();

                   accountAdmin.logIn(username,password);
               }
            }
        }
    }
}