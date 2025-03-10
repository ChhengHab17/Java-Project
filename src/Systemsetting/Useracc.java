package Systemsetting;

public class Useracc {
    protected String username;  // Protected so subclass can access

    public Useracc(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
}
