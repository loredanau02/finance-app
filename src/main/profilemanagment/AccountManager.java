package main.profilemanagment;

import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

public class AccountManager {
    private Map<String, Profile> accounts;

    public AccountManager() {
        this.accounts = new HashMap<>();
    }

    public boolean registerAccount(String username, String password, String email, String backupEmail) {
        if (accounts.containsKey(username)) {
            return false;
        }
        
        Profile profile = new Profile(username, password, email, backupEmail);
        accounts.put(username, profile);
        writeToCSV(profile);
        return true;
    }

    public Profile login(String username, String password) {
        Profile profile = accounts.get(username);
        if (profile != null && profile.getPassword().equals(password)) {
            return profile;
        }
        return null;
    }

    private void writeToCSV(Profile profile) {
        try (FileWriter writer = new FileWriter("credentials.csv", true)) {
            writer.append(profile.getUsername())
                  .append(",")
                  .append(profile.getPassword())
                  .append(",")
                  .append(profile.getEmail())
                  .append(",")
                  .append(profile.getBackupEmail())
                  .append("\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }
} 