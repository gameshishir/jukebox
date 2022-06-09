package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertPodcastToPlaylist {
    public int insertPodcast(int playListId) throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Password@123");
        System.out.println("Enter Podcast Number To Add Podcast to The Playlist : ");
        int podcastId = scan.nextInt();
        PreparedStatement pt = con.prepareCall("insert into playlistpodcast values (?,?)");
        pt.setInt(1,playListId);
        pt.setInt(2,podcastId);
        int i = pt.executeUpdate();
        if (i > 0)
        {
            System.out.println("Podcast Added Successfully");
            System.out.println("Do You Want to Add More Podcast ?.");
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
