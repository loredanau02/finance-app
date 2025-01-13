package main.profilemanagment;

// mvn clean compile
// java -cp target/classes main.Main

public class Profile {
    private String username;
    private String password;
    private String email;
    private boolean isEmailVerified;
    private String backupEmail;

    public Profile(String username, String password, String email, String backupEmail) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.backupEmail = backupEmail;
        this.isEmailVerified = false;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public boolean isEmailVerified() { return isEmailVerified; }
    public String getBackupEmail() { return backupEmail; }
    
    @Override
    public String toString() {
        return "User Profile Details:\n" +
               "Username: " + username + "\n" +
               "Password: " + password + "\n" +
               "Email: " + email + "\n" +
               "Backup Email: " + backupEmail + "\n" +
               "Email Verified: " + isEmailVerified;
    }

}
