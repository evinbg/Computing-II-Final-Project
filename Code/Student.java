import java.util.*;

public class Student extends Account {
    private ArrayList<Course> enrolled = new ArrayList<Course>();
    
    public Student(String user, String pass) {
        super(user, pass, 2);
        this.options = 6;
        System.out.println("New Student account successfully created");
    }

    public void printMenu() {
        System.out.println("[Student Menu]");
        System.out.println("1) View courses");
        System.out.println("2) Join a course");
        System.out.println("3) Reply to a discussion");
        System.out.println("4) Take a quiz");
        System.out.println("5) Rate a course");
        System.out.println("6) Exit");
    }

    public void viewCourses() { // prints all of the Student's enrolled Courses
        int size = this.enrolled.size();

        if(size > 0) {
            System.out.println("Your Courses:");
            for(int i = 0; i < size; i++) {
                System.out.println((i + 1) + ") " + this.enrolled.get(i).getName());
            }
            System.out.println("");
        }
        else {
            System.out.println("You aren't enrolled in any courses");
            System.out.println("");
        }
    }

    public ArrayList<Course> getCourses() {
        return this.enrolled;
    }

    public Course getCourse(int course) {
        return this.enrolled.get(course - 1); // course - 1 because indexing starts at 0, and quiz is the user input starts at 1
    }

    public void setCourses(ArrayList<Course> newCourses) {
        this.enrolled = newCourses;
    }

    public void joinCourse(Course course) {
        int size = this.enrolled.size();
        boolean check = true;

        for(int i = 0; i < size; i++) { // checking if Student is already enrolled in Course
            if(this.enrolled.get(i).getName().equals(course.getName())) {
                System.out.println("You are already enrolled in " + course.getName());
                System.out.println("");
                check = false;
                break;
            }
        }

        if(check == true) {
            this.enrolled.add(course); // adding Course to enrolled Courses
            System.out.println("You have been enrolled in: " + course.getName());
            System.out.println("");
        }
    }

    public void replyToDiscussion(Discussion discussion, String reply) {
        String toAdd[] = {this.username, reply};
        ArrayList<String[]> replies = discussion.getReplies();
        replies.add(toAdd);
        discussion.setReplies(replies);
    }

    public void takeQuiz(Quiz quiz, Main main) {
        int correct = 0;
        for(int i = 0; i < quiz.getQuestions().size(); i++) {
            Question question = quiz.getQuestion(i);
            System.out.println("[" + quiz.getName() + "]");
            System.out.println("Question " + (i + 1) + ":");
            if(question.getType() == 1) { // ShortAnswer
                question.printQuestion();
                String answer = main.input.nextLine();

                if(answer.toLowerCase().equals(question.getAnswer())) { // toLowerCase() to avoid incorrect answers due to capitilzation
                    correct = correct + 1;
                }
            }

            else if(question.getType() == 2) { // TrueFalse
                question.printQuestion();
                Integer answer = main.checkInput(2);

                if(answer.toString().equals(question.getAnswer())) {
                    correct = correct + 1;
                }
            }

            else if(question.getType() == 3) { // MultipleChoice
                question.printQuestion();
                Integer answer = main.checkInput(((MultipleChoice)question).getOptions().size()); // casting to MultipleChoice to use getOptions() method
                
                if(answer.toString().equals(question.getAnswer())) {
                    correct = correct + 1;
                }
            }
            System.out.println("");
        }

        float grade = (float)correct / (float)quiz.getQuestions().size(); // must cast ints to floats
        quiz.addGrade(this, grade);
        System.out.println("Your grade: " + (grade * 100) + "%");
    }
}
