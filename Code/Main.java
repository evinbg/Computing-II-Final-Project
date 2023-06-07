import java.util.*;
import java.io.*;

public class Main {

    Scanner input = new Scanner(System.in);
    HashMap<String, Account> accounts = new HashMap<String, Account>(); // all Accounts
    HashMap<String, Course> courses = new HashMap<String, Course>(); // all Courses
    Account account; // the logged in account
    int menuLevel; // keeps track of how deep you are into the multi-level menu
    Course course; // the course selected by Instructor

    public static void main(String args[]) {
        new Main();
    }

    public Main() {
        loadData();

        account = login();
        int accountType = account.getAccountType();
        menuLevel = 1;
        course = null;

        boolean keepGoing = true;

        while(keepGoing) {
            if(accountType == 1) {
                doActionInstructor();
            }
            else if(accountType == 2) {
                doActionStudent();
            }
        }
    }

    public void saveData() { // saves the hashmaps of accounts and courses to files using serialization
        try { // saving accounts
            FileOutputStream fileOutAccounts = new FileOutputStream("accounts.dat");
            ObjectOutputStream objectOutAccounts = new ObjectOutputStream(fileOutAccounts);
            objectOutAccounts.reset();
            objectOutAccounts.writeObject(accounts);
            objectOutAccounts.close();
        }
        catch (Exception e) {
            System.out.println("");
            System.out.println("Something went wrong while saving accounts");
            System.out.println("");
        }

        try { // saving courses
            FileOutputStream fileOutCourses = new FileOutputStream("courses.dat");
            ObjectOutputStream objectOutCourses = new ObjectOutputStream(fileOutCourses);
            objectOutCourses.reset();
            objectOutCourses.writeObject(courses);
            objectOutCourses.close();
        }
        catch (Exception e) {
            System.out.println("");
            System.out.println("Something went wrong while saving courses");
            System.out.println("");
        }
    }

    public void loadData() { // loads the hashmaps of accounts and courses from files using serialization
        try { // loading accounts
            FileInputStream fileInAccounts = new FileInputStream("accounts.dat");
            ObjectInputStream objectInAccounts = new ObjectInputStream(fileInAccounts);
            accounts = (HashMap<String, Account>)objectInAccounts.readObject();
            objectInAccounts.close();
        }
        catch (FileNotFoundException e) {
        }
        catch (Exception e) {
            System.out.println("");
            System.out.println("Something went wrong while loading accounts");
            System.out.println("");
        }

        try { // loading courses
            FileInputStream fileInCourses = new FileInputStream("courses.dat");
            ObjectInputStream objectInCourses = new ObjectInputStream(fileInCourses);
            courses = (HashMap<String, Course>)objectInCourses.readObject();
            objectInCourses.close();
        }
        catch (FileNotFoundException e) {
        }
        catch (Exception e) {
            System.out.println("");
            System.out.println("Something went wrong while loading courses");
            System.out.println("");
        }
    }

    public Account login() {
        System.out.println("[CourseM]");
        System.out.println("Welcome! Login to your existing account, or sign up for a new account");
        System.out.println("1) Login");
        System.out.println("2) Sign Up");
        System.out.print(": ");
        int loginCheck = checkInput(2);
        String username;

        if(loginCheck == 1) { // existing account
            System.out.println("");
            System.out.print("Enter your username: ");
            username = input.nextLine();

            if(accounts.containsKey(username)) { // if the account name exists
                System.out.print("Enter your password: ");
                String password = input.nextLine();

                Account account = accounts.get(username);
                String accountPassword = account.getPassword();
                if(password.equals(accountPassword) == true) { // check if password is correct
                    System.out.println("Successfully logged in");
                    System.out.println("");
                    return account;
                }
                else { // if password is incorrect
                    System.out.println("");
                    System.out.println("Password is incorrect");
                    System.exit(1);
                }
            }
            else { // if account doesn't exist
                System.out.println("");
                System.out.println("An account with that username doesn't exist");
                System.exit(1);
            }
        }

        if(loginCheck == 2) { // signing up
            System.out.println("");
            username = "";

            boolean keepGoing = true;
            while(keepGoing) { // checking if username already exists
                System.out.print("Enter a username: ");
                username = input.nextLine();

                if(accounts.containsKey(username)) { // if the account name exists
                    System.out.println("Username already taken, try a different one");
                    continue;
                }
                else {
                    keepGoing = false;
                }
            }

            System.out.print("Enter a password: ");
            String password = input.nextLine();
            System.out.println("Is this an Instructor or Student account?");
            System.out.println("1) Instructor");
            System.out.println("2) Student");
            System.out.print(": ");
            int accountType = checkInput(2);
            
            if(accountType == 1) {
                Account account = new Instructor(username, password);
                System.out.println("");
                accounts.put(username, account); // adding new account to hashmap
                return account;
            }
            if(accountType == 2) {
                Account account = new Student(username, password);
                System.out.println("");
                accounts.put(username, account); // adding new account to the accounts hashmap
                return account;
            }
        }

        return null; // should never return this, but have to return something
    }

    public int checkInput(int options) {
        boolean checkInput = false;

        while (!checkInput){
            try {
                checkInput = true;
                String in = input.nextLine();
                int check = Integer.parseInt(in);

                if (check < 0 || check > options) { // checks to see if integer input is not an option
                    System.out.println("Please enter a valid option");
                    System.out.print(": ");
                    checkInput = false;
                    continue;
                }
                return check;
            }
            catch (NumberFormatException e){
                System.out.println("Please enter an integer");
                System.out.print(": ");
                checkInput = false;
            }
            catch (Exception e) {
                System.out.println("Something went wrong");
                System.out.print(": ");
            }
        }

        return 0; // should never return this, but have to have it
    }

    /*
    object serialization does not update objects that it thinks it has already saved in its cache,
    so we must manually update all of the Courses and Accounts we change so that they are globally updated
    */
    public void updateCourses(Course updateCourse) {
        // updating courses
        courses.replace(updateCourse.getName(), updateCourse);

        // updating accounts
        ArrayList<Student> students = updateCourse.getStudents();
        for(int i = 0; i < students.size(); i++) {
            accounts.replace(students.get(i).getUsername(), (Account)students.get(i));
        }

        Instructor instructor = updateCourse.getInstructor();
        accounts.replace(instructor.getUsername(), (Account)instructor);
    }

    public void doActionInstructor() {
        if(menuLevel == 1) {
            doActionInstructorMain();
        }
        else if(menuLevel == 2) {
            doActionInstructorCourse();
        }
    }

    public void doActionInstructorMain() {
        account.printMenu();
        System.out.print(": ");
        int action = checkInput(account.getOptions());
        Instructor instructor = (Instructor)account;
        int numOfCourses = instructor.getCourses().size();

        switch(action) {
            case 1: // view courses
                instructor.viewCourses();

                break;


            case 2: // create a course
                System.out.print("Enter a name for the course: ");
                String courseName = input.nextLine();

                if(courses.containsKey(courseName)) { // if the course name exists
                    System.out.println("Course name already taken, try a different one");
                    System.out.println("");
                    return;
                }
                else { // if the course name doesn't exist
                    Course newCourse = instructor.createCourse(courseName);
                    courses.put(courseName, newCourse); // adding the new course to the courses hashmap
                    System.out.println("");
                }

                break;


            case 3: // enter a course
                if(numOfCourses <= 0) {
                    System.out.println("You don't have any courses");
                    System.out.println("");
                    return;
                }
                else {
                    instructor.viewCourses();
                    System.out.print("Select a course: ");
                    int selectedCourse = checkInput(numOfCourses);
                    Course getCourse = instructor.getCourse(selectedCourse);
                    course = getCourse;
                    menuLevel = 2; // updating menuLevel
                    System.out.println("");
                }

                break;

            
            case 4: // delete a course
                if(numOfCourses <= 0) {
                    System.out.println("You don't have any courses");
                    System.out.println("");
                    return;
                }
                else {
                    instructor.viewCourses();
                    System.out.print("Select a course: ");
                    int selectedCourse = checkInput(numOfCourses);
                    Course getCourse = instructor.getCourse(selectedCourse);
                    instructor.deleteCourse(getCourse, selectedCourse);
                    courses.remove(getCourse.getName());
                }

                break;


            case 5: // exit
                System.out.println("Exiting...");
                saveData(); // saves before exiting
                System.exit(0);

                break;


            default:
                System.out.println("Something went wrong");
                System.exit(0);
                break;
            }

    }

    public void doActionInstructorCourse() {
        course.printMenu();
        System.out.print(": ");
        int action = checkInput(course.getOptions());
        Instructor instructor = (Instructor)account;

        switch(action) {
            case 1: // create discussion
                System.out.print("Enter a the discussion question: ");
                String discussionQuestion = input.nextLine();
                instructor.createDiscussion(course, discussionQuestion);
                updateCourses(course);
                System.out.println("");

                break;


            case 2: // view discussions
                boolean anyDiscussions = course.printDiscussions();
                if(anyDiscussions == true) {
                    System.out.print("Select the discussion: ");
                    int selectedDiscussion = checkInput(course.getDiscussions().size());
                    Discussion discussion = course.getDiscussion(selectedDiscussion); // getting the specific discussion of the selected course
                    System.out.print("");
                    discussion.printDiscussion();
                }

                break;


            case 3: // delete a discussion
                boolean anyDiscussionsDelete = course.printDiscussions();
                if(anyDiscussionsDelete == true) {
                    System.out.print("Select the discussion: ");
                    int selectedDiscussion = checkInput(course.getDiscussions().size());
                    course.deleteDiscussion(selectedDiscussion);
                    updateCourses(course);
                }

                break;


            case 4: // create quiz
                System.out.print("Enter a the quiz name: ");
                String quizName = input.nextLine();
                instructor.createQuiz(course, quizName, this);
                updateCourses(course);

                break;


            case 5: // view quizzes
                boolean anyQuizzes = course.printQuizzes();
                if(anyQuizzes == true) {
                    System.out.print("Select the quiz: ");
                    int selectedQuiz = checkInput(course.getQuizzes().size());
                    Quiz quiz = course.getQuiz(selectedQuiz); // getting the specific discussion of the selected course
                    System.out.print("");
                    quiz.printGrades();
                }

                break;


            case 6: // delete a quiz
                boolean anyQuizzesDelete = course.printQuizzes();
                if(anyQuizzesDelete == true) {
                    System.out.print("Select the quiz: ");
                    int selectedQuiz = checkInput(course.getQuizzes().size());
                    course.deleteQuiz(selectedQuiz);
                    updateCourses(course);
                }

                break;


            case 7: // view course rating
                course.getRating();

                break;


            case 8: // view students
                course.viewStudents();
                
                break;


            case 9: // going back to Instructor menu
                menuLevel = 1;
                System.out.println("");

                break;

            
            default:
                System.out.println("Something went wrong");
                System.exit(0);
                break;

        }
    }

    public void doActionStudent() {
        account.printMenu();
        System.out.print(": ");
        int action = checkInput(account.getOptions());
        Student student = (Student)account;
        int numOfCourses = student.getCourses().size();

        switch(action) {
            case 1: // view courses
                student.viewCourses();

                break;


            case 2: // join a course
                System.out.print("Enter the name of the course you want to enroll in: ");
                String courseName = input.nextLine();

                if(courses.containsKey(courseName)) { // if the course name exists
                    Course courseJoin = courses.get(courseName);
                    student.joinCourse(courseJoin);
                    courseJoin.addStudent(student);
                    updateCourses(courseJoin);
                    
                    return;
                }
                else { // if the course name doesn't exist
                    System.out.println("A course with that name doesn't exist");
                    System.out.println("");
                }

                break;


            case 3: // reply to discussion
                if(numOfCourses <=  0) { // can't have discussions if you aren't enrolled in any courses
                    System.out.println("You aren't enrolled in any courses");
                    System.out.println("");
                    return;
                }
                else {
                    student.viewCourses();
                    System.out.print("Select a course: ");
                    int selectedCourse = checkInput(numOfCourses);
                    Course courseDiscussion = student.getCourse(selectedCourse);

                    boolean anyDiscussions = courseDiscussion.printDiscussions();
                    if(anyDiscussions == true) {
                        System.out.print("Select a discussion: ");
                        int selectedDiscussion = checkInput(courseDiscussion.getDiscussions().size());
                        Discussion discussion = courseDiscussion.getDiscussion(selectedDiscussion); // getting the specific discussion of the selected course
                        System.out.print("");
                        discussion.printDiscussion();

                        System.out.print("Enter your reply: ");
                        String discussionReply = input.nextLine();

                        student.replyToDiscussion(discussion, discussionReply);
                        updateCourses(courseDiscussion);
                        System.out.println("");
                    }
                }

                break;


            case 4: // take quiz
                if(numOfCourses <=  0) { // can't take quiz if you aren't enrolled in any courses
                    System.out.println("You aren't enrolled in any courses");
                    System.out.println("");
                    return;
                }
                else {
                    student.viewCourses();
                    System.out.print("Select a course: ");
                    int selectedCourse = checkInput(numOfCourses);
                    Course courseQuiz = student.getCourse(selectedCourse);

                    boolean anyQuizzes = courseQuiz.printQuizzes();
                    if(anyQuizzes == true) {
                        System.out.print("Select a quiz: ");
                        int selectedQuiz = checkInput(courseQuiz.getQuizzes().size());
                        Quiz quiz = courseQuiz.getQuiz(selectedQuiz); // getting the specific Quiz of the selected Course
                        System.out.print("");
                        student.takeQuiz(quiz, this);

                        updateCourses(courseQuiz);
                        System.out.println("");
                    }
                }

                break;


            case 5: // rate a course
                if(numOfCourses <=  0) { // can't rate a course if you aren't enrolled in any courses
                    System.out.println("You aren't enrolled in any courses");
                    System.out.println("");
                    return;
                }
                else {
                    student.viewCourses();
                    System.out.print("Select a course: ");
                    int selectedCourse = checkInput(numOfCourses);
                    Course courseRating = student.getCourse(selectedCourse);

                    System.out.print("Enter your rating (1-10): ");
                    int rating = checkInput(10);
                    courseRating.addRating(this.account, rating);
                    updateCourses(courseRating);

                    System.out.println("");
                }

                break;


            case 6: // exit
                System.out.println("Exiting...");
                saveData(); // saves before exiting
                System.exit(0);

                break;


            default:
                System.out.println("Something went wrong");
                System.exit(0);
                break;
        }
    }
}