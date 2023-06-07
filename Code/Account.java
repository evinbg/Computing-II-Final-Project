import java.io.Serializable;

abstract class Account implements Serializable {
    protected String username;
    protected String password;
    protected int accountType;
    protected int options;

    public Account() {
    }

    public Account(String user, String pass, int accType) {
        this.username = user;
        this.password = pass;
        this.accountType = accType;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getAccountType() {
        return this.accountType;
    }

    public int getOptions() {
        return this.options;
    }

    public abstract void printMenu();

    public abstract void viewCourses();

}
