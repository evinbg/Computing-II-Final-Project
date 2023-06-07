import java.util.*;
import java.io.Serializable;

public class Course implements Serializable {
    private String name;
    private Instructor instructor;
    private ArrayList<Student> students = new ArrayList<Student>(); 
    private ArrayList<Quiz> quizzes = new ArrayList<Quiz>(); 
    private ArrayList<Discussion> discussions = new ArrayList<Discussion>(); 
    private HashMap<Account, Integer> rating = new HashMap<Account, Integer>();
    private int options;

    public Course() {
    }

    public Course(String courseName, Instructor instruct) {
        this.name = courseName;
        this.instructor = instruct;
        this.options = 9;
        System.out.println("Course successfully created");
    }

    public void printMenu() {
        System.out.println("[" + this.name + "]");
        System.out.println("1) Create a discussion");
        System.out.println("2) View discussions");
        System.out.println("3) Delete a discussion");
        System.out.println("4) Create a quiz");
        System.out.println("5) View quiz grades");
        System.out.println("6) Delete a quiz");
        System.out.println("7) View course rating");
        System.out.println("8) View students");
        System.out.println("9) Back");
    }

    public int getOptions() {
        return this.options;
    }

    public String getName() {
        return this.name;
    }

    public Instructor getInstructor() {
        return this.instructor;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void addStudent(Student name) {
        this.students.add(name);
        this.rating.put(name, null); // adding student to rating hashmap with null because they haven't rated yet
    }

    public void viewStudents() {
        int size = this.students.size();
        if(size > 0) {
            System.out.println("Students:");
            for(int i = 0; i < size; i++) {
                Student student = this.students.get(i);
                System.out.println(student.getUsername());
            }
            System.out.println("");
        }
        else {
            System.out.println("You do not have any students enrolled in this course");
            System.out.println("");
        }
    }

    public ArrayList<Quiz> getQuizzes() {
        return this.quizzes;
    }

    public Quiz getQuiz(int quiz) {
        return this.quizzes.get(quiz - 1); // quiz - 1 because indexing starts at 0, and quiz is the user input starts at 1
    }

    public void setQuizzes(ArrayList<Quiz> newQuizzes) {
        this.quizzes = newQuizzes;
    }

    public void deleteQuiz(int quizNum) {
        quizzes.remove(quizNum - 1);

        System.out.println("Quiz successfully deleted");
        System.out.println("");
    }

    public boolean printQuizzes() {
        int size = this.quizzes.size();
        if(size > 0) {
            System.out.println("Quizzes:");
            for(int i = 0; i < size; i++) {
                System.out.println((i + 1) + ") " + quizzes.get(i).getName());
            }
            System.out.println("");
            return true;
        }
        else {
            System.out.println("You do not have any quizzes posted for this course");
            System.out.println("");
            return false;
        }
    }

    public ArrayList<Discussion> getDiscussions() {
        return this.discussions;
    }

    public Discussion getDiscussion(int discussion) {
        return this.discussions.get(discussion - 1); // discussion - 1 because indexing starts at 0, and quiz is the user input starts at 1
    }

    public void setDiscussions(ArrayList<Discussion> newDiscussions) {
        this.discussions = newDiscussions;
    }

    public void deleteDiscussion(int discussionNum) {
        discussions.remove(discussionNum - 1);

        System.out.println("Discussion successfully deleted");
        System.out.println("");
    }

    public boolean printDiscussions() {
        int size = this.discussions.size();
        if(size > 0) {
            System.out.println("Discussions:");
            for(int i = 0; i < size; i++) {
                System.out.println((i + 1) + ") " + discussions.get(i).getQuestion());
            }
            System.out.println("");
            return true;
        }
        else {
            System.out.println("You do not have any discussions posted for this course");
            System.out.println("");
            return false;
        }
    }

    public void getRating() {
        int ratingCount = 0;
        int ratings = 0;
        for (Integer value : this.rating.values()) { // iterating through hashmap for all values
            if(value == null) { // skipping if null
                continue;
            }
            else {
                ratings += value; // adding value to ratings
                ratingCount++;
            }
        }
        if(ratingCount == 0) {
            System.out.println("No one has rated this course yet");
        }
        else {
            Integer averageRating = ratings / ratingCount;
            System.out.println("Rating:");
            System.out.println(averageRating.toString() + " / 10");
        }
        System.out.println("");
    }

    public void addRating(Account student, int newRating) {
        this.rating.put(student, newRating); // updating rating hashmap if Student rates Course
    }

    public void delete() {
        // deleting the Course from the students
        int size = this.students.size();
    
        for(int i = 0; i < size; i++) {
            Student student = this.students.get(i);
            ArrayList<Course> studentCourses = student.getCourses();
            for(int j = 0; j < studentCourses.size(); j++) {
                if(student.getCourse(j + 1).getName().equals(this.name)) {
                    studentCourses.remove(j);
                    break;
                }
            }
        }
    }
}
