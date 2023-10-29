package fi.utu.tech.assignment4;

import java.util.ArrayList;
import java.util.List;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;


public class App4 {
    public static void main( String[] args )
    {
        // Otetaan funktion aloitusaika talteen suoritusajan laskemista varten
        long startTime = System.currentTimeMillis();

        // Generoidaan kasa esimerkkitehtäväpalautuksia
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);

        // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        // Luodaan 
        List<GradingTask> gradingTasks = TaskAllocator.sloppyAllocator(ungradedSubmissions);
        List<Thread> gradingThreads = new ArrayList<>();

        // Luodaan uusi arviointitehtävä
        //GradingTask gradingTask = new GradingTask(ungradedSubmissions);

        // Luodaan uudet säikeet jokaista GradinTask kohden
        for (GradingTask gradingTask : gradingTasks) {
            Thread gradingThread = new Thread(gradingTask); // Luodaan uudet säikeet
            gradingThreads.add(gradingThread); // Lisätään säikeet listaan
            gradingThread.start(); // Käynnistetään säikeet

        }
        

        // Käynnistetään säie
        //gradingThread.start();

        // Odota säikeiden suoritusta
        for (Thread gradingThread: gradingThreads) {
            try { 
                //TEST for printing Thread name
                //System.out.println(gradingThread);
                gradingThread.join();
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }

        // Haetaan kaikki arvioidut palautukset
        List<Submission> gradedSubmissions = new ArrayList<>(); // Luodaan lista arvioiduista palautuksista
        for (GradingTask gradingTask : gradingTasks) {
            gradedSubmissions.addAll(gradingTask.getGradedSubmissions()); // Haetaan ja lisätään kaikki arvioidut palautukset
        }
        

        //  Tulostetaan arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradedSubmissions) {
            System.out.println(gs);
        }
        
        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
