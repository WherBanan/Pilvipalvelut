package fi.utu.tech.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App6 {
    public static void main(String[] args) {


        long startTime = System.currentTimeMillis();

        // Generoidaan kasa esimerkkitehtäväpalautuksia
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.UNFAIR);

        // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        int taskCount = 8; // TÄHÄN SÄIKEIDEN MÄÄRÄ!!!

        // Luodaan "thread pool", jossa "fixed number of threads = taskCount"
        ExecutorService executorService = Executors.newFixedThreadPool(taskCount);

        // Allokaattori, joka jakaa tehtävät
        List<GradingTask> gradingTasks = TaskAllocator.allocate(ungradedSubmissions, taskCount);

        // Laitetaan tehtävät ExecutorServicen suoritettavaksi
        for (GradingTask gradingTask : gradingTasks) {
            executorService.execute(gradingTask);
        }
        
        // Sammutetaan ExecutorService
        executorService.shutdown();
        
        
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("Oops, was that me?" + e);
        }

        // Haetaan kaikki arvioidut palautukset
        List<Submission> gradedSubmissions = new ArrayList<>();
        for (GradingTask gradingTask : gradingTasks) {
            gradedSubmissions.addAll(gradingTask.getGradedSubmissions());
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
