package com.company;

import java.sql.*;
import java.util.Scanner;

public class UserRegistration {
    public void userRegistration() throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "Password@123");
        System.out.println("For Creating A Playlist You Must have An Account.");
        System.out.println("If You Already Have An Account Press 1.");
        System.out.println("If You Don't Have An Account Press 2.");
        int option = scan.nextInt();
        scan.nextLine();
        if (option == 1) {
            UserInputForCreatingPlaylist userInputForCreatingPlaylist = new UserInputForCreatingPlaylist();
            int userId2 = userInputForCreatingPlaylist.userInput();
            if (userId2 > -1) {
                CreateNewPlaylist createNewPlaylist = new CreateNewPlaylist();
                createNewPlaylist.createNewPlaylist(userId2);
            } else {
                System.out.println("--------------------User Not Found----------------------------------");
                System.out.println("Either You Don't Have Account or You Entered Wrong Email or Password.");
                System.out.println("1.Do You Want To Create A New Account ?");
                System.out.println("2.Do You Want to Re-Enter Your Details ?");
                int choice = scan.nextInt();
                if (choice == 1) {
                    CreateNewAccount createNewAccount = new CreateNewAccount();
                    int userId1 = createNewAccount.createNewAccount();
                    CreateNewPlaylist createNewPlaylist = new CreateNewPlaylist();
                    createNewPlaylist.createNewPlaylist(userId1);
                } else if (choice == 2) {
                    int userId = 0;
                    do {
                        //UserInputForCreatingPlaylist userInputForCreatingPlaylist1 = new UserInputForCreatingPlaylist();
                        //userId = userInputForCreatingPlaylist1.userInput();
                        CreateNewAccount createNewAccount1 = new CreateNewAccount();
                        userId = createNewAccount1.createNewAccount();
                    } while (userId == -1);
                    CreateNewPlaylist createNewPlaylist = new CreateNewPlaylist();
                    createNewPlaylist.createNewPlaylist(userId);
                }
            }
        } else if (option == 2) {
            CreateNewAccount createNewAccount = new CreateNewAccount();
            int userId = createNewAccount.createNewAccount();
            CreateNewPlaylist createNewPlaylist = new CreateNewPlaylist();
            createNewPlaylist.createNewPlaylist(userId);
        }
    }
}
