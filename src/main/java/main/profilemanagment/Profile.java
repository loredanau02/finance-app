package main.profilemanagment;

// mvn clean compile
// java -cp target/classes main.Main

public class Profile {
    private String username;
    private String password;
    private String email;
    private boolean isEmailVerified;
    private String backupEmail;
    private String verificationCode;
    private boolean isPublic;

    public Profile(String username, String password, String email, String backupEmail) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.backupEmail = backupEmail;
        this.isEmailVerified = false;
        this.verificationCode = generateVerificationCode();
        this.isPublic = true;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public boolean isEmailVerified() { return isEmailVerified; }
    public String getBackupEmail() { return backupEmail; }
    public String getVerificationCode() { return verificationCode; }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBackupEmail(String backupEmail) {
        this.backupEmail = backupEmail;
    }

    public void setEmailVerified(boolean verified) {
        this.isEmailVerified = verified;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    private String generateVerificationCode() {
        // Generate a 6-digit code
        return String.format("%06d", new java.util.Random().nextInt(1000000));
    }

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
