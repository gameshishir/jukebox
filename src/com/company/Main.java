package com.company;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        while (choice != 5)
        {
            System.out.println("---------------------JukeBox---------------------");
            System.out.println("Select The Option Given Below");
            System.out.println("1.Play Songs.");
            System.out.println("2.Play Podcast.");
            System.out.println("3.Play From Your Playlist.");
            System.out.println("4.Create New Playlist.");
            System.out.println("5.Exit");
            choice = scan.nextInt();
            if(choice == 1)
            {
                PlaySongs playSongs = new PlaySongs();
                playSongs.playSongs();

            }
            else if (choice ==2)
            {
                Podcast podcast = new Podcast();
                podcast.podcast();
            }
            else if (choice ==3)
            {
                PlayFromYourPlaylist playFromYourPlaylist = new PlayFromYourPlaylist();
                playFromYourPlaylist.playFromPlaylist();
            }
            else if (choice==4)
            {
                UserRegistration userRegistration = new UserRegistration();
                userRegistration.userRegistration();
            }
        }
    }
}
