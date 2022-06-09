package com.company;

import java.sql.*;
import java.util.Scanner;

public class CreateNewAccount {
    public int createNewAccount() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "Password@123");
        Scanner scan = new Scanner(System.in);
        System.out.println("---------User Registration----------");
        System.out.print("Enter User Name or Enter Your Email : ");
        String userName1 = scan.nextLine();
        //scan.nextInt();
        System.out.println("--------------Instractuction To Enter A Password-------------");
        System.out.println("1.It must contain at least 8 characters and at most 20 characters.\n" +
                "2.It contains at least one digit.\n" +
                "3.It contains at least one upper case alphabet.\n" +
                "4.It contains at least one lower case alphabet.\n" +
                "5.It contains at least one special character which includes !@#$%&*()-+=^.\n" +
                "6.It doesn’t contain any white space.");
        System.out.println("---------------------------------------------------------------");
        System.out.println("Enter A Password : ");
        String password1 = scan.nextLine();
        boolean isValid;
        isValid = PasswordValidator.isValidPassword(password1);
        while (!isValid)
        {
            System.out.print("Enter A Password As The Instruction Given : ");
            password1 = scan.nextLine();
            isValid = PasswordValidator.isValidPassword(password1);
        }
        int userId1 = 0;
            PreparedStatement pt1 = con.prepareStatement("insert into user(username,password) values (?,?)");
            pt1.setString(1, userName1);
            pt1.setString(2, password1);
            int i = pt1.executeUpdate();
            if (i > 0) {
                PreparedStatement pt2 = con.prepareStatement("select * from user where username = ? ");
                pt2.setString(1, userName1);
                ResultSet rs2 = pt2.executeQuery();
                if (rs2.next()) {
                    userId1 = rs2.getInt("userid");
                }
                System.out.println("-------Registration Successfull-------");
            }
        //System.out.println("Please Follow The Instruction For Creating A Password.");
        return userId1;
    }
}
