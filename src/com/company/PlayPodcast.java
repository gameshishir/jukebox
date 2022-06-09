package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class PlayPodcast {
    public void playPodcast(int episodeId) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","Password@123");
        PreparedStatement pt = con.prepareStatement("select * from episode where episodid = ? ");
        pt.setInt(1,episodeId);
        ResultSet rs = pt.executeQuery();
        Scanner scan = new Scanner(System.in);
        if (rs.next())
        {
            String path = rs.getString("path");
            File file = new File(path);
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
                        System.out.println("Plaese Give A Valid Instraction.");
                }
            }
        }
        else
        {
            System.out.println("Podcast Not Found With Episode Id : "+episodeId);
        }
    }
}
