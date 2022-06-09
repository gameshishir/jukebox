package com.company;

import java.sql.*;
import java.util.Scanner;

public class UserInputForCreatingPlaylist {
    public int userInput() throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "Password@123");
        System.out.print("Enter User Name or Enter Your Email : ");
        String userName = scan.nextLine();
        System.out.print("Enter Your Password : ");
        String password = scan.nextLine();
        PreparedStatement pt = con.prepareStatement("Select * from user where username = ?");
        pt.setString(1, userName);
        String pass = "";
        int userId = 0;
        ResultSet rs = pt.executeQuery();
        if (rs.next()) {
            pass = rs.getString("password");
        }
        if (password.equalsIgnoreCase(pass)) {
            System.out.println("-------------Please Create Your Playlist-------------");
            PreparedStatement pt1 = con.prepareStatement("Select * from user where username = ? ");
            pt1.setString(1, userName);
            ResultSet rs1 = pt1.executeQuery();
            if (rs1.next()) {
                userId = rs1.getInt("userid");
            }
        }
        else {
            System.out.println("User Not Found.");
            //System.out.println("Please Enter Correct Details.");
            return -1;
        }
        return userId;
    }
}
