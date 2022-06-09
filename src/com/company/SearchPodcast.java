package com.company;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.sql.*;
import java.util.Scanner;

public class SearchPodcast {
    public void serachPodcast(int choice,String categoryorCelebrityName) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Password@123");
        Scanner scan = new Scanner(System.in);
        String tableName = "";
        String columnName = "";
        if(choice == 1)
        {
            tableName = "podcastcategory";
            columnName = "podcastcategoryname";
        }
        else if (choice == 2)
        {
            tableName = "celebrity";
            columnName = "celebrityname";
        }
        Statement st = con.createStatement();
        String str = "select * from "+ tableName + " where " + columnName +" = '" +categoryorCelebrityName +"'";
        ResultSet rs = st.executeQuery(str);
        //PreparedStatement pt = con.prepareStatement("Select * from ? where ? = ? ");
        //pt.setString(1,tableName);
        //pt.setString(2,columnName);
        //pt.setString(3,categoryorCelebrityName);
        //ResultSet rs = pt.executeQuery();
        if (choice == 1) {
            if (rs.next()) {
                int podcastCategoryId = rs.getInt("podcastcategoryid");
                PreparedStatement pt2 = con.prepareStatement("Select * from podcast where podcastcategoryid = ?");
                pt2.setInt(1, podcastCategoryId);
                ResultSet rs2 = pt2.executeQuery();
                while (rs2.next()) {
                    int podcastId = rs2.getInt("podcastid");
                    PreparedStatement pt3 = con.prepareStatement("Select * from episode where podcastid = ? ");
                    pt3.setInt(1, podcastId);
                    ResultSet rs3 = pt3.executeQuery();
                    while (rs3.next()) {
                        System.out.println("----------Search Result-----------");
                        System.out.println("Episode Name : " + rs3.getString("episodename"));
                        System.out.println("Episode Length : " + rs3.getString("episodelength"));
                        System.out.println("Episode Number : " + rs3.getInt("episodid"));
                    }
                }
                System.out.print("Enter the Episode Number To Play The Podcast : ");
                int episodeId = scan.nextInt();
                PlayPodcast playPodcast = new PlayPodcast();
                playPodcast.playPodcast(episodeId);
            }
        }
            else if (choice == 2)
            {
                if (rs.next())
                {
                    int celebrityId = rs.getInt("celebrityid");
                    PreparedStatement pt2 = con.prepareStatement("Select * from podcast where celebrityid = ?");
                    pt2.setInt(1,celebrityId);
                    ResultSet rs2 = pt2.executeQuery();
                    while (rs2.next())
                    {
                        int podcastId = rs2.getInt("podcastid");
                        PreparedStatement pt3 = con.prepareStatement("Select * from episode where podcastid = ? ");
                        pt3.setInt(1,podcastId);
                        ResultSet rs3 = pt3.executeQuery();
                        while(rs3.next())
                        {
                            System.out.println("----------Search Result-----------");
                            System.out.println("Episode Name : "+rs3.getString("episodename"));
                            System.out.println("Episode Length : "+rs3.getString("episodelength"));
                            System.out.println("Episode Number : "+rs3.getInt("episodid"));
                        }
                    }
                    System.out.print("Enter the Episode Number To Play The Podcast : ");
                    int episodeId = scan.nextInt();
                    PlayPodcast playPodcast = new PlayPodcast();
                    playPodcast.playPodcast(episodeId);
                }
            }
        }
    }
