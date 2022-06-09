package com.company;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Podcast {
    public void podcast() throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("-------Choose What You Want------");
        System.out.println("1.Search Podcast By Category And Play.");
        System.out.println("2.Search Podcast By Celebrity And Play");
        int choice = scan.nextInt();
        scan.nextLine();
            if (choice == 1) {
                System.out.print("Enter Category Name : ");
                String categoryName = scan.nextLine();
                SearchPodcast searchPodcast = new SearchPodcast();
                searchPodcast.serachPodcast(choice, categoryName);
            } else if (choice == 2) {
                System.out.print("Enter Celebrity Name : ");
                String celebrityName = scan.nextLine();
                SearchPodcast searchPodcast = new SearchPodcast();
                searchPodcast.serachPodcast(choice, celebrityName);
            } else {
                System.out.println("Invalid Input.");
            }
    }
}
