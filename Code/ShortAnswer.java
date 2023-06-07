public class ShortAnswer extends Question {

    public ShortAnswer(String question) {
        super(question, 1);
    }

    public void printQuestion() {
        System.out.println(this.question);
        System.out.println("");
        System.out.print("Enter your answer: ");
    }
}
