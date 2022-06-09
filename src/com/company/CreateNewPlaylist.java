package com.company;

import java.sql.*;
import java.util.Scanner;

public class CreateNewPlaylist {
    public void createNewPlaylist(int userId) throws SQLException {
        Scanner scan = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Password@123");
        System.out.print("Enter Your Playlist Name : ");
        String playlistName = scan.nextLine();
        PreparedStatement pt = con.prepareStatement("insert into playlist (playlistname,userid) values (?,?)");
        pt.setString(1,playlistName);
        pt.setInt(2,userId);
        int i = pt.executeUpdate();
        int choice = 0;
        if (i > 0)
        {
            do {
                System.out.println("Playlist Successfully Created.");
                System.out.println("1.Do You Want To Add Song To The Same Playlist.");
                System.out.println("2.Do You Want To Add Podcast To The Same Playlist.");
                System.out.println("3.Back");
                System.out.print("Enter Your Choice : ");
                //System.out.println("If Yes Press 1.");
                //System.out.println("If No Press 2.");
                choice = scan.nextInt();
                if (choice == 1)
                {
                    PreparedStatement pt1 = con.prepareStatement("select * from playlist where userid = ? ");
                    pt1.setInt(1,userId);
                    int playListId = 0;
                    ResultSet rs = pt1.executeQuery();
                    while (rs.next())
                    {
                        playListId = rs.getInt("playlistid");
                    }
                    int yesNo;
                    do {
                        PreparedStatement pt9 = con.prepareStatement("select * from songs");
                        ResultSet rs1 = pt9.executeQuery();
                        while (rs1.next())
                        {
                            System.out.println("----------------Songs-----------------");
                            System.out.println("Song Name : " +rs1.getString("songname"));
                            System.out.println("Duration : " + rs1.getString("song_length"));
                            System.out.println("Song Number : " + rs1.getInt("songid"));
                            System.out.println("-----------------------------------------");
                        }
                        InsertSongsToPlaylist insertSongsToPlaylist = new InsertSongsToPlaylist();
                        yesNo = insertSongsToPlaylist.insertSongsToPlaylist(playListId);
                    }while (yesNo != 2);
                    System.out.println("Your Songs Are Added Successfully.");
                }
                else if (choice == 2)
                {
                    PreparedStatement pt1 = con.prepareStatement("select * from playlist where userid = ? ");
                    pt1.setInt(1,userId);
                    int playListId = 0;
                    ResultSet rs = pt1.executeQuery();
                    while (rs.next())
                    {
                        playListId = rs.getInt("playlistid");
                    }
                    int yesNo;
                    do {
                        PreparedStatement pt10 = con.prepareStatement("select * from episode");
                        ResultSet rs10 = pt10.executeQuery();
                        while (rs10.next())
                        {
                            System.out.println("----------Podcast-----------");
                            System.out.println("Episode Name : "+rs10.getString("episodename"));
                            System.out.println("Episode Length : "+rs10.getString("episodelength"));
                            System.out.println("Episode Number : "+rs10.getInt("episodid"));
                        }
                        InsertPodcastToPlaylist insertPodcastToPlaylist = new InsertPodcastToPlaylist();
                        yesNo = insertPodcastToPlaylist.insertPodcast(playListId);
                    }while (yesNo != 2);
                    System.out.println("Your Podcasts Are Added Successfully.");
                }
            }while (choice != 3);
        }
        else
        {
            System.out.println("Sorry.Your Playlist Is Not Created.");
            return;
        }
    }
}
