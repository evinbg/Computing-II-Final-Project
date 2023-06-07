import java.util.*;
import java.io.Serializable;

public class Discussion implements Serializable {
    private String question;
    private ArrayList<String[]> replies = new ArrayList<String[]>(); // arraylist of arrays of strings

    public Discussion() {
    }

    public Discussion(String discussionQuestion) {
        this.question = discussionQuestion;
        System.out.println("Discussion successfully created");
    }

    public String getQuestion() {
        return this.question;
    }

    public ArrayList<String[]> getReplies() {
        return this.replies;
    }

    public void setReplies(ArrayList<String[]> newReplies) {
        this.replies = newReplies;
    }

    public void printDiscussion() {
        System.out.println(this.question);
        System.out.println("");

        int size = this.replies.size();
        if(size > 0) {
            for(int i = 0; i < size; i++) {
                String name = this.replies.get(i)[0];
                String reply = this.replies.get(i)[1];
                System.out.println("[" + name + "]: " + reply);
            }
            System.out.println("");
        }
        else {
            System.out.println("No one has replied yet");
            System.out.println("");
        }
    }
}
