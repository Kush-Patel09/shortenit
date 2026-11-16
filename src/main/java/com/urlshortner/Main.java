
package com.urlshortner;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UrlService service = new UrlService();

        while (true) {
            System.out.println("\n--- URL Shortener ---");
            System.out.println("1. Shorten URL");
            System.out.println("2. Open short code");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String input = sc.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            try {
                if (choice == 1) {
                    System.out.print("Enter original URL: ");
                    String url = sc.nextLine();

                    String code = service.shortenUrl(url);
                    System.out.println("Short code: " + code);

                } else if (choice == 2) {
                    System.out.print("Enter short code: ");
                    String code = sc.nextLine();

                    String url = service.getOriginalUrl(code);

                    if (url != null) {
                        System.out.println("Original URL: " + url);
                    } else {
                        System.out.println("Short code not found!");
                    }

                } else if (choice == 3) {
                    System.out.println("Goodbye!");
                    break;

                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        sc.close();
    }
}
