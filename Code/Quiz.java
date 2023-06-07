import java.util.*;
import java.io.Serializable;

public class Quiz implements Serializable{
    private String name;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private HashMap<Account, Float> grades = new HashMap<Account, Float>();

    public Quiz() {
    }

    public Quiz(String quizName) {
        this.name = quizName;
    }

    public void printMenu() {
        System.out.println("[Quiz Creator]");
        System.out.println("1) Add short answer question");
        System.out.println("2) Add true or false question");
        System.out.println("3) Add multiple choice question");
        System.out.println("4) Finish");
    }

    public String getName() {
        return this.name;
    }
    
    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public Question getQuestion(int number) {
        return this.questions.get(number);
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void printGrades() {
        System.out.println("Grades:");
        for (Map.Entry<Account, Float> set : grades.entrySet()) {
		    System.out.println(set.getKey().getUsername() + ": " + (set.getValue() * 100) + "%");
		}
        System.out.println("");
    }

    public void addGrade(Account student, Float grade) {
        this.grades.put(student, grade);
    }
}
