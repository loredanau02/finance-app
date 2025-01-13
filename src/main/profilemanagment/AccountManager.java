package main.profilemanagment;

import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private Map<String, Profile> accounts;

    public AccountManager() {
        this.accounts = new HashMap<>();
        loadAccountsFromCSV();
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

    private void loadAccountsFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String username = data[0];
                    String password = data[1];
                    String email = data[2];
                    String backupEmail = data[3];
                    Profile profile = new Profile(username, password, email, backupEmail);
                    accounts.put(username, profile);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading accounts from the CSV file: " + e.getMessage());
        }
    }

    public Profile getProfile(String username) {
        return accounts.get(username);
    }

    public void updateProfileInCSV(Profile profile, String oldUsername) {
        accounts.remove(oldUsername);
        accounts.put(profile.getUsername(), profile);
        
        try {
            List<Profile> profiles = new ArrayList<>(accounts.values());
            try (FileWriter writer = new FileWriter("credentials.csv")) {
                for (Profile p : profiles) {
                    writer.append(p.getUsername())
                          .append(",")
                          .append(p.getPassword())
                          .append(",")
                          .append(p.getEmail())
                          .append(",")
                          .append(p.getBackupEmail())
                          .append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating the CSV file: " + e.getMessage());
        }
    }

    public boolean isUsernameTaken(String username) {
        return accounts.containsKey(username);
    }
} 