package main.notifications;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        NotificationRepository repository = new NotificationRepository();
        NotificationService service = new NotificationService(repository);
        NotificationController controller = new NotificationController(service);

        controller.displayMenu(); // Show menu to user
    }
}

