package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class PlaySongs  {
    public void playSongs() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Scanner scan = new Scanner(System.in);
        System.out.println("What You Want To Do");
        System.out.println("1.Search Songs By Name.");
        System.out.println("2.Search Songs By Album.");
        System.out.println("3.Search Songs By Genre.");
        System.out.println("4.Search Songs By Artist.");
        System.out.println("5.Show All Songs.");
        System.out.print("Enter Your Choice : ");
        int choice = scan.nextInt();
        scan.nextLine();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Password@123");
        if (choice == 1)
        {
            System.out.print("Enter Song Name: ");
            String songName = scan.nextLine();
            PreparedStatement st = con.prepareStatement("Select songname,song_length,songid from songs where songname like ? ");
            st.setString(1,songName+"%");
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                System.out.println("-------Search Result-------");
                System.out.println("Song Name : " + rs.getString("songname"));
                System.out.println("Duration : " + rs.getString("song_length"));
                System.out.println("song Number : " + rs.getInt("songid"));
            }
            System.out.print("Enter The Song Number To Play The Song : ");
            int songId = scan.nextInt();
            PreparedStatement st1 = con.prepareStatement("select path from songs where songid = ? ");
            st1.setInt(1,songId);
            ResultSet rs1 = st1.executeQuery();
            String path = "";
            while (rs1.next())
            {
                path = rs1.getString("path");
            }
            File file = new File(path);
            AudioInputStream audioInputStream =  AudioSystem.getAudioInputStream(file);
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
                        System.out.println("Plaese Give A Valid Instraction.");
                }
            }
        }
        else if (choice == 2)
        {
            System.out.println("Enter the Album name : ");
            String albumName = scan.nextLine();
            PreparedStatement pt = con.prepareStatement("select albumid from album where albumname like ?");
            pt.setString(1,albumName+"%");
            ResultSet rs = pt.executeQuery();
            int albumId = 0;
            while (rs.next())
            {
                albumId = rs.getInt("albumid");
                //System.out.println("Album Name : " + rs.getString(1));
                //System.out.println("Album Id : " + rs.getInt(2));
            }
            PreparedStatement pt1 = con.prepareStatement("select songname,song_length,songid from songs where albumid = ? ");
            pt1.setInt(1,albumId);
            ResultSet rs1 = pt1.executeQuery();
            while (rs1.next())
            {
                System.out.println("-------Search Result-------");
                System.out.println("Song Name : " + rs1.getString("songname"));
                System.out.println("Duration : " + rs1.getString("song_length"));
                System.out.println("song Number : " + rs1.getInt("songid"));
            }
            System.out.print("Enter The Song Number To Play : ");
            int songId = scan.nextInt();
            PreparedStatement pt2 = con.prepareStatement("select path from songs where songid = ?");
            pt2.setInt(1,songId);
            ResultSet rs2 = pt2.executeQuery();
            String path = "";
            while (rs2.next())
            {
                path = rs2.getString("path");
            }
            File file = new File(path);
            AudioInputStream audioInputStream =  AudioSystem.getAudioInputStream(file);
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
                        System.out.println("Plaese Give A Valid Instraction.");
                }
            }
        }
        else if (choice==3)
        {
            System.out.print("Enter the Genre : ");
            String genreName = scan.nextLine();
            PreparedStatement pt = con.prepareStatement("Select genreid from genre where genrename like ?");
            pt.setString(1,genreName+"%");
            ResultSet rs = pt.executeQuery();
            int genreid = 0;
            while (rs.next())
            {
                genreid = rs.getInt("genreid");
            }
            PreparedStatement pt1 = con.prepareStatement("Select * from songs where genreid = ? ");
            pt1.setInt(1,genreid);
            ResultSet rs1 = pt1.executeQuery();
            while (rs1.next())
            {
                System.out.println("-------Search Result-------");
                System.out.println("Song Name : " + rs1.getString("songname"));
                System.out.println("Duration : " + rs1.getString("song_length"));
                System.out.println("song Number : " + rs1.getInt("songid"));
            }
            System.out.print("Enter The Song Number To Play : ");
            int songId = scan.nextInt();
            PreparedStatement pt2 = con.prepareStatement("select path from songs where songid = ?");
            pt2.setInt(1,songId);
            ResultSet rs2 = pt2.executeQuery();
            String path = "";
            while (rs2.next())
            {
                path = rs2.getString("path");
            }
            File file = new File(path);
            AudioInputStream audioInputStream =  AudioSystem.getAudioInputStream(file);
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
                        System.out.println("Plaese Give A Valid Instraction.");
                }
            }
        }
        else if (choice == 4)
        {
            System.out.println("Enter the Artist's Name : ");
            String artistName = scan.nextLine();
            PreparedStatement pt = con.prepareStatement("Select * from artist where artistname like ?");
            pt.setString(1,artistName+"%");
            ResultSet rs = pt.executeQuery();
            int artistId = 0;
            while (rs.next())
            {
                artistId = rs.getInt("artistid");
            }
            PreparedStatement pt1 = con.prepareStatement("select * from songs where artistid = ? ");
            pt1.setInt(1,artistId);
            ResultSet rs1 = pt1.executeQuery();
            while (rs1.next())
            {
                System.out.println("-------Search Result-------");
                System.out.println("Song Name : " + rs1.getString("songname"));
                System.out.println("Duration : " + rs1.getString("song_length"));
                System.out.println("song Number : " + rs1.getInt("songid"));
            }
            System.out.print("Enter The Song Number To Play : ");
            int songId = scan.nextInt();
            PreparedStatement pt2 = con.prepareStatement("select path from songs where songid = ?");
            pt2.setInt(1,songId);
            ResultSet rs2 = pt2.executeQuery();
            String path = "";
            while (rs2.next())
            {
                path = rs2.getString("path");
            }
            File file = new File(path);
            AudioInputStream audioInputStream =  AudioSystem.getAudioInputStream(file);
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
                        System.out.println("Plaese Give A Valid Instraction.");
                }
            }

        }
        else if (choice == 5)
        {
            PreparedStatement pt1 = con.prepareStatement("select * from songs");
            ResultSet rs1 = pt1.executeQuery();
            while (rs1.next())
            {
                //System.out.println("-----------------------------------------");
                System.out.println("Song Name : " +rs1.getString("songname"));
                System.out.println("Duration : " + rs1.getString("song_length"));
                System.out.println("Song Number : " + rs1.getInt("songid"));
                System.out.println("-----------------------------------------");
            }
            System.out.print("Enter The Song Number To Play : ");
            int songId = scan.nextInt();
            PreparedStatement pt2 = con.prepareStatement("select path from songs where songid = ?");
            pt2.setInt(1,songId);
            ResultSet rs2 = pt2.executeQuery();
            String path = "";
            while (rs2.next())
            {
                path = rs2.getString("path");
            }
            File file = new File(path);
            AudioInputStream audioInputStream =  AudioSystem.getAudioInputStream(file);
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
                        System.out.println("Plaese Give A Valid Instraction.");
                }
            }
        }
    }
}
