package main;

import main.profile_managment.Profile;

public class Main {
    public static void main(String[] args) {
        
        Profile newUser = new Profile(
            "john_doe",
            "password123",
            "john@example.com",
            "john.backup@example.com"
        );
        
        System.out.println(newUser.toString());
        
        System.out.println("\nAccessing Individual Fields:");
        System.out.println("Username: " + newUser.getUsername());
        System.out.println("Password: " + newUser.getPassword());
        System.out.println("Email: " + newUser.getEmail());
        System.out.println("Backup Email: " + newUser.getBackupEmail());
        System.out.println("Email Verified: " + newUser.isEmailVerified());
    }
}
