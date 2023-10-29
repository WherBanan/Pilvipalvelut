package fi.utu.tech.common;

import java.util.ArrayList; //Hyödynnetään ArrayListä olioiden säilyttämisenä
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * You need to modify this file
 */


public class TaskAllocator {

    public static List<GradingTask> sloppyAllocator(List<Submission> submissions) {
        // Otetaan tehtävien määrä ylös
        int totalSubmissions = submissions.size();
        int halfOfSubmissions = totalSubmissions / 2;

        // Luodaan lista johon lisätään 2 oliota
        List<GradingTask> gradingTasks = new ArrayList<>();

        // Luodaan ensimmäinen olio
        GradingTask firstHalf = new GradingTask(submissions.subList(0, halfOfSubmissions));
        gradingTasks.add(firstHalf);

        // Luodaan toinen olio
        GradingTask secondHalf = new GradingTask(submissions.subList(halfOfSubmissions, totalSubmissions));
        gradingTasks.add(secondHalf);
    
        // Palautetaan lista, jossa 2 puolikasta
        return gradingTasks;
    }


    
    
    public static List<GradingTask> allocate(List<Submission> submissions, int taskCount) {
        List<GradingTask> gradingTasks = new ArrayList<>();

        // Lasketaan kuinka monta tehtävää yksi säie tekee vähintään ja jakojäämä
        int submissionsPerTask = submissions.size() / taskCount;
        int remainingSubmissions = submissions.size() % taskCount;
        
        int firstIndex = 0;

        for (int i = 0; i < taskCount; i++) {
            int lastIndex = firstIndex + submissionsPerTask + (remainingSubmissions > 0 ? 1 : 0); // Varmistetaan, että remainingSubmnisions ei ole > 0
            

            List<Submission> taskSubmissions = submissions.subList(firstIndex, lastIndex);

            // Lisätään subList olio GradingTasks listaan
            gradingTasks.add(new GradingTask(taskSubmissions));

            firstIndex = lastIndex;
            remainingSubmissions--;
        }         
        return gradingTasks;


        
    }

    
}
