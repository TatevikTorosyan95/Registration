package service;

import main.FileService;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static User convert(String data) {
        String[] split = data.split(",");
        User u = new User();
        u.setFullName(split[0]);
        u.setUserName(split[1]);
        u.setEmail(split[2]);
        u.setPassword(split[3]);
        return u;
    }

    public User create() {
        Scanner s = new Scanner(System.in);
        String email = "";
        String fullName = "";
        String username = "";
        String password = "";
        boolean valid = false;

        System.out.println("Enter full name");

        while (valid == false) {
            fullName = s.nextLine();
            Pattern VALID_FULL_NAME_REGEX =
                    Pattern.compile("^[A-Z]{1}[a-z]{1,}[\s]{1}[A-Z]{1}[a-z]{1,}$");
            Matcher matcher = VALID_FULL_NAME_REGEX.matcher(fullName);
            if (matcher.find() == true) {
                valid = true;
            } else {
                System.out.println("~~~~~~~~~~~~~~~~~ Enter Right Full Name ~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }

        System.out.println("Enter username");
        valid = false;
        while (valid == false) {
            int duplicate = 0;
            username = s.next();
            String[] userTxt = FileService.readLines("Files\\database.txt");
            User[] users = new User[userTxt.length];
            for (int i = 0; i < userTxt.length; i++) {
                users[i] = convert(userTxt[i]);
                if (username.equals(users[i].getUsername())) {
                    duplicate++;
                }
            }
            if (username.length() < 10) {
                System.out.println("The length should contain more than 10");
            } else if (duplicate > 0) {
                System.out.println("Please enter new username");
            } else {
                valid = true;
            }
        }


        System.out.println("Enter email address");
        valid = false;
        while (valid == false) {
            email = s.next();
            Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            if (matcher.find() == true) {
                valid = true;
            } else {
                System.out.println("~~~~~~~~~~~~~~~~~ Enter new Email ~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }

        System.out.println("Enter password");
        valid = false;
        while (valid == false) {
            password = s.next();
            int countNumber = 0;
            int countUppercase = 0;
            char ch;
            if (password == null || password.length() < 8) {
                System.out.println("Password should be less than 15 and more than 8 characters in length.");

            }

            for (int i = 0; i < password.length(); i++) {
                ch = password.charAt(i);
                if (Character.isDigit(ch)) {
                    countNumber++;
                }
                if (Character.isUpperCase(ch)) {
                    countUppercase++;
                }
            }
            if (countNumber >= 3 && countUppercase >= 2) {
                password = MD5(password);
                valid = true;
            } else {
                System.out.println("Enter new password");
            }

        }

        User user = new User();
        user.setFullName(fullName);
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    public void getForFile() {
        Scanner s = new Scanner(System.in);
        boolean validUser = false;
        int userID = -1;
        System.out.println("Please enter User Name");

        String[] userTxt = FileService.readLines("Files\\database.txt");
        User[] users = new User[userTxt.length];
        while (validUser == false) {
            String name = s.nextLine();
            for (int i = 0; i < userTxt.length; i++) {
                users[i] = convert(userTxt[i]);
                if (name.equals(users[i].getUsername())) {
                    System.out.println("Please enter password");
                    userID = i;
                    break;
                }

            }

            if (userID >= 0) {
                String pass = s.next();
                if (!MD5(pass).equals(users[userID].getPassword())) {
                    System.out.println("Invalid password");
                } else {
                    System.out.println("Login success");
                    validUser = true;
                }
            } else {
                System.out.println("Enter right user name");
            }
        }

    }
}
