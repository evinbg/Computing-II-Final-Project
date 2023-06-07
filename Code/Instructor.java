import java.util.*;

public class Instructor extends Account {
    private ArrayList<Course> courses = new ArrayList<Course>();

    public Instructor(String user, String pass) {
        super(user, pass, 1);
        this.options = 5;
        System.out.println("New Instructor account successfully created");
    }

    public void printMenu() {
        System.out.println("[Instructor Menu]");
        System.out.println("1) View courses");
        System.out.println("2) Create a new course");
        System.out.println("3) Enter a course");
        System.out.println("4) Delete a course");
        System.out.println("5) Exit");
    }

    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public Course getCourse(int course) {
        return this.courses.get(course - 1); // course - 1 because indexing starts at 0, and quiz is the user input starts at 1
    }

    public void viewCourses() { // prints all of the Instructor's Courses
        int size = this.courses.size();

        if(size > 0) {
            System.out.println("Your Courses:");
            for(int i = 0; i < size; i++) {
                System.out.println((i + 1) + ") " + this.courses.get(i).getName());
            }
            System.out.println("");
        }
        else {
            System.out.println("You do not have any courses");
            System.out.println("");
        }
    }

    public Course createCourse(String courseName) {
        Course newCourse = new Course(courseName, this);
        this.courses.add(newCourse);
        return newCourse;
    }

    public void deleteCourse(Course courseDelete, int courseNum) {
        courseDelete.delete();
        String courseName = courseDelete.getName();
        this.courses.remove(courseNum - 1);

        System.out.println(courseName + " has been deleted");
        System.out.println("");
    }

    public void createDiscussion(Course course, String discussionQuestion) {
        Discussion newDiscussion = new Discussion(discussionQuestion);
        ArrayList<Discussion> newDiscussions = course.getDiscussions();
        newDiscussions.add(newDiscussion);
        course.setDiscussions(newDiscussions);
    }

    // passing Main through to use checkInput method
    public void createQuiz(Course course, String quizName, Main main) {
        System.out.println("");
        Quiz quiz = new Quiz(quizName);

        boolean keepGoing = true;
        while(keepGoing) {
            quiz.printMenu();
            int input = main.checkInput(4);

            if(input == 4) { // finish
                keepGoing = false;
                System.out.println("Finished quiz creation");
                System.out.println("");
                break;
            }

            else if(input == 1) { // ShortAnswer
                System.out.print("Enter the question: ");
                String question = main.input.nextLine();
                System.out.print("Enter the answer: ");
                String answer = main.input.nextLine();
                ShortAnswer shortAnswer = new ShortAnswer(question);
                shortAnswer.setAnswer(answer.toLowerCase()); // toLowerCase() to avoid incorrect answers due to capitilzation
                quiz.addQuestion(shortAnswer);
            }

            else if(input == 2) { // TrueFalse
                System.out.print("Enter the question: ");
                String question = main.input.nextLine();
                TrueFalse truefalse = new TrueFalse(question);
                System.out.println("1) True");
                System.out.println("2) False");
                System.out.print("Enter the answer: ");
                Integer answer = main.checkInput(2);
                truefalse.setAnswer(answer.toString());
                quiz.addQuestion(truefalse);
            }

            else if(input == 3) { // multiple choice
                System.out.print("Enter the question: ");
                String question = main.input.nextLine();
                MultipleChoice multiplechoice = new MultipleChoice(question);
                boolean keepGoingMC = true;
                Integer optionCount = 1; // keep track which option number we are on

                while(keepGoingMC) { // while loop to keep adding multiple choice options
                    System.out.println("[Multiple Choice Creator]");
                    System.out.println("1) Add correct answer");
                    System.out.println("2) Add incorrect answer");
                    System.out.println("3) Finish this question");
                    System.out.print(": ");
                    Integer checkMC = main.checkInput(3);

                    if(checkMC == 1) { // add correct answer
                        System.out.print("Enter the option: ");
                        String option = main.input.nextLine();
                        multiplechoice.addOption(option);
                        multiplechoice.setAnswer(optionCount.toString());
                    }

                    if(checkMC == 2) { // add incorrect answer
                        System.out.print("Enter the option: ");
                        String option = main.input.nextLine();
                        multiplechoice.addOption(option);
                    }

                    if(checkMC == 3) {
                        keepGoingMC = false;
                        break;
                    }
                    
                    optionCount++;
                    System.out.println("");
                }

                quiz.addQuestion(multiplechoice);
            }
            System.out.println("");
        }

        ArrayList<Quiz> quizzes = course.getQuizzes();
        quizzes.add(quiz);
        course.setQuizzes(quizzes);
    }
}
