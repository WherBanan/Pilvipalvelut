package fi.utu.tech.assignment3;

import java.util.List;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App3 {
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

        // Luodaan uusi arviointitehtävä
        GradingTask gradingTask = new GradingTask(ungradedSubmissions);

        // Luodaan uusi säie ja annetaan sen tehtäväksi gradingTask
        Thread gradingThread = new Thread(gradingTask);

        // Käynnistetään säie
        gradingThread.start();

        // Odota säikeen suoritusta
        try { 
            //TEST for printing Thread name
            //System.out.println(gradingThread);
            gradingThread.join();
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
        
        // Haetaan arvioidut palautukset
        List<Submission> gradedSubmissions = gradingTask.getGradedSubmissions();
        

        //  Tulostetaan arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradedSubmissions) {
            System.out.println(gs);
        }
        
        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
