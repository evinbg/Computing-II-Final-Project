public class TrueFalse extends Question {

    public TrueFalse(String question) {
        super(question, 2);
    }

    public void printQuestion() {
        System.out.println(this.question);
        System.out.println("");
        System.out.println("1) True");
        System.out.println("2) False");
        System.out.print("Enter your answer: ");
    }
}
