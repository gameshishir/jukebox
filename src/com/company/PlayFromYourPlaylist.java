package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class PlayFromYourPlaylist {
    public void playFromPlaylist() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Your User Name or Email Id To Search Your Playlist : ");
        String userNameOrEmail = scan.nextLine();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Password@123");
        PreparedStatement pt = con.prepareStatement("Select * from user where username = ? ");
        pt.setString(1,userNameOrEmail);
        ResultSet rs = pt.executeQuery();
        int userId = 0;
        if (rs.next())
        {
            userId = rs.getInt("userid");
        }
        else {
            System.out.println("User Not Found.");
            System.out.println("You Need To Create An Account First");
            CreateNewAccount createNewAccount = new CreateNewAccount();
            createNewAccount.createNewAccount();
            CreateNewPlaylist createNewPlaylist = new CreateNewPlaylist();
            createNewPlaylist.createNewPlaylist(userId);

        }
        PreparedStatement pt1 = con.prepareStatement("select * from playlist where userid = ? ");
        pt1.setInt(1,userId);
        ResultSet rs1 = pt1.executeQuery();
        while (rs1.next())
        {
            System.out.println("Playlist Name : "+rs1.getString("playlistname"));
            System.out.println("Playlist Number : "+rs1.getInt("playlistid"));
            System.out.println("---------------------------------------");
        }
        System.out.print("Enter Playlist Number To View Your Songs On that Playlist : ");
        int playlistId = scan.nextInt();
        PreparedStatement pt3 = con.prepareStatement("select * from playlistsong where playlistid = ? ");
        pt3.setInt(1,playlistId);
        ResultSet rs3 = pt3.executeQuery();
        PreparedStatement pt6 = con.prepareStatement("select * from playlistpodcast where playlistid = ? ");
        pt6.setInt(1,playlistId);
        ResultSet rs6 = pt6.executeQuery();
        while (rs6.next())
        {
            int podcastId = rs6.getInt("podcastid");
            PreparedStatement pt7 = con.prepareStatement("Select * from episode where podcastid = ?");
            pt7.setInt(1,podcastId);
            ResultSet rs7 = pt7.executeQuery();
            while (rs7.next())
            {
                System.out.println("----------Podcasts-----------");
                System.out.println("Episode Name : "+rs7.getString("episodename"));
                System.out.println("Episode Length : "+rs7.getString("episodelength"));
                System.out.println("Episode Number : "+rs7.getInt("episodid"));
            }
        }
        while (rs3.next())
        {
            int songId = rs3.getInt("songid");
            PreparedStatement pt4 = con.prepareStatement("Select * from songs where songid = ? ");
            pt4.setInt(1,songId);
            ResultSet rs4 = pt4.executeQuery();
            while (rs4.next())
            {
                System.out.println("----------Songs-----------");
                System.out.println("Song Name : " + rs4.getString("songname"));
                System.out.println("Duration : " + rs4.getString("song_length"));
                System.out.println("song Number : " + rs4.getInt("songid"));
                System.out.println("------------------------------------------------");
            }
        }
        System.out.println("Press 1 To Play Podcast.");
        System.out.println("Press 2 Tp Play Songs.");
        int podcastOrSongs = scan.nextInt();
        if (podcastOrSongs == 1)
        {
            System.out.print("Enter The Episode Number : ");
            int episodeNumber = scan.nextInt();
            PlayPodcast playPodcast = new PlayPodcast();
            playPodcast.playPodcast(episodeNumber);
        }
        else if (podcastOrSongs == 2)
        {
            System.out.print("Enter The Song Number To Play The Song : ");
            int songNumber = scan.nextInt();
            PreparedStatement pt5 = con.prepareStatement("select * from songs where songid = ? ");
            pt5.setInt(1,songNumber);
            ResultSet rs5 = pt5.executeQuery();
            String path = "";
            while (rs5.next())
            {
                path = rs5.getString("path");
            }
            File file =new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            int instruction = 0;
            long position = 0;
            while(instruction != 6)
            {
                System.out.println("Press 1 to Play, 2 To Stop, 3 for Restart, 4 to Pause, 5 to Resume, 6 to Exit. ");
                instruction = scan.nextInt();
                switch (instruction)
                {
                    case 1:
                        clip.start();
                        break;
                    case 2:
                        clip.setMicrosecondPosition(0);
                        clip.stop();
                        break;
                    case 3:
                        clip.setMicrosecondPosition(0);
                        break;
                    case 4:
                        clip.stop();
                        position = clip.getMicrosecondPosition();
                        System.out.println("Your Song Is On Pause........");
                        break;
                    case 5:
                        clip.setMicrosecondPosition(position);
                        clip.start();
                        break;
                    case 6:
                        clip.close();
                        break;
                    default:
                        System.out.println("Plaese Give A Valid Instruction.");
                }
            }
        }
    }
}
