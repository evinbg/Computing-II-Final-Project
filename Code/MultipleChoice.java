import java.util.*;

public class MultipleChoice extends Question{
    private ArrayList<String> all = new ArrayList<String>();

    public MultipleChoice(String question) {
        super(question, 3);
    }

    public ArrayList<String> getOptions() {
        return this.all;
    }

    public void addOption(String option) {
        this.all.add(option);
    }

    public void printQuestion() {
        System.out.println(this.question);
        System.out.println("");
        for(int i = 0; i < this.all.size(); i++) {
            System.out.println((i + 1) + ") " + all.get(i));
        }
        System.out.print("Enter your answer: ");
    }
}
