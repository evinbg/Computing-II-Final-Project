import java.io.Serializable;

abstract class Question implements Serializable {
    protected String question;
    protected String answer;
    protected int type;  // 1: ShortAnswer, 2: TrueFalse, 3: MultipleChoice

    public Question() {
    }

    public Question (String q, int t) {
        this.question = q;
        this.type = t;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String ans) {
        this.answer = ans;
    }

    public int getType() {
        return this.type;
    }

    abstract public void printQuestion();
}
