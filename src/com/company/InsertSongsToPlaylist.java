package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertSongsToPlaylist {
    public int insertSongsToPlaylist(int playListId) throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Password@123");
        System.out.println("Enter Song Number To Add Song to The Playlist : ");
        int songId = scan.nextInt();
        PreparedStatement pt = con.prepareCall("insert into playlistsong values (?,?)");
        pt.setInt(1,playListId);
        pt.setInt(2,songId);
        int i = pt.executeUpdate();
        if (i > 0)
        {
            System.out.println("Song Added Successfully");
            System.out.println("Do You Want to Add More Song ?.");
            System.out.println("If Yes Press 1,");
            System.out.println("If No Press 2.");
            int choice = scan.nextInt();
            if (choice == 1 )
            {
                return 1;
            }
        }
        return 2;
    }
}
