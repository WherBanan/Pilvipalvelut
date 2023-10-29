package fi.utu.tech.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GradingTask implements Runnable {

    private Random rnd = new Random();
    private List<Submission> ungradedSubmissions; //Store ungraded submissions
    private List<Submission> gradedSubmissions; //Store graded submissions
    
    //Recieve ungraded submissions and initialize graded submissions list
    public GradingTask(List<Submission> ungradedSubmissions) {
        this.ungradedSubmissions = ungradedSubmissions;
        this.gradedSubmissions = new ArrayList<>();
    }

    //Call gradeAll method
    public void run() {
        gradeAll(this.ungradedSubmissions);
    }

    //Retrieve the graded submissions
    public List<Submission> getGradedSubmissions() {
        return gradedSubmissions;
    }

    //Grade the ungraded submissions
    public void gradeAll(List<Submission> submissions) {
        for (var s : submissions) {
            gradedSubmissions.add(grade(s));
        }
    }
    
    

    //Grading
    public Submission grade(Submission s) {
        try {
            Thread.sleep(s.getDifficulty());
        } catch (InterruptedException e) {
            System.err.println("Who dared to interrupt my sleep?!");
        }
        return s.grade(rnd.nextInt(6));
    }
}
