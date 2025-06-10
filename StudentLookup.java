import java.io.*;
import java.util.*;

public class StudentLookup {

    // Inner class to represent a student
    static class Student {
        String id;
        String ci;
        String name;
        int age;
        String career;
        String email;

        public Student(String id, String ci, String name, int age, String career, String email) {
            this.id = id;
            this.ci = ci;
            this.name = name;
            this.age = age;
            this.career = career;
            this.email = email;
        }

        @Override
        public String toString() {
            return String.format("ID: %s | CI: %s | Name: %s | Age: %d | Career: %s | Email: %s",
                    id, ci, name, age, career, email);
        }
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        // Load data
        try (BufferedReader reader = new BufferedReader(new FileReader("<file path>"))) {
            String line;
            reader.readLine();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length != 6) continue;

                String id = parts[0];
                String ci = parts[1];
                String name = parts[2];
                int age = Integer.parseInt(parts[3]);
                String career = parts[4];
                String email = parts[5];

                students.add(new Student(id, ci, name, age, career, email));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading or parsing file: " + e.getMessage());
            return;
        }

        // Search menu
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\nSearch by:");
            System.out.println("1. ID");
            System.out.println("2. CI");
            System.out.println("3. Name");
            System.out.println("4. Age");
            System.out.println("5. Career");
            System.out.println("6. Email");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String choice = input.nextLine();

            if (choice.equals("0")) break;

            System.out.print("Enter value to search: ");
            String query = input.nextLine();

            List<Student> results = new ArrayList<>();
            for (Student s : students) {
                switch (choice) {
                    case "1":
                        if (s.id.equals(query)) results.add(s);
                        break;
                    case "2":
                        if (s.ci.equals(query)) results.add(s);
                        break;
                    case "3":
                        if (s.name.toLowerCase().contains(query.toLowerCase())) results.add(s);
                        break;
                    case "4":
                        try {
                            if (s.age == Integer.parseInt(query)) results.add(s);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid age format.");
                        }
                        break;
                    case "5":
                        if (s.career.toLowerCase().contains(query.toLowerCase())) results.add(s);
                        break;
                    case "6":
                        if (s.email.toLowerCase().contains(query.toLowerCase())) results.add(s);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

            if (results.isEmpty()) {
                System.out.println("No results found.");
            } else {
                System.out.println("\nResults:");
                for (Student s : results) {
                    System.out.println(s);
                }
            }
        }

        input.close();
    }
}