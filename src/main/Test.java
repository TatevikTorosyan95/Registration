package main;

import model.User;
import service.UserService;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {

        FileService.createFolder("Files");
        FileService.createFile("Files", "database.txt ");

        Scanner s = new Scanner(System.in);

        System.out.println("For registration press 1 \nFor log in press 2");
        int n = s.nextInt();

        if (n == 1) {
            System.out.println("Sign In");
            UserService userService = new UserService();
            User user = userService.create();
            FileService.write("Files\\database.txt", user.toString() + "\n");
        } else if (n == 2) {
            System.out.println("Log in");
            UserService userService = new UserService();
            userService.getForFile();
        }
    }
}
