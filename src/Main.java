import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Repository repo = new Repository();
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 2; i++) {
            System.out.println("\nEnter details for Student " + i + ":");
            System.out.print("First Name: ");
            String firstName = scanner.next();
            System.out.print("Middle Name: ");
            String middleName = scanner.next();
            System.out.print("Last Name: ");
            String lastName = scanner.next();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            System.out.print("Email: ");
            String email = scanner.next();
            System.out.print("Course: ");
            String course = scanner.next();
            System.out.print("Year Level: ");
            int yearLevel = scanner.nextInt();
            System.out.print("Section: ");
            String section = scanner.next();
            System.out.print("School: ");
            String school = scanner.next();
            System.out.print("Gender: ");
            String gender = scanner.next();

            Student student = new Student.Builder()
                    .setFirstName(firstName)
                    .setMiddleName(middleName)
                    .setLastName(lastName)
                    .setAge(age)
                    .setEmail(email)
                    .setCourse(course)
                    .setYearLevel(yearLevel)
                    .setSection(section)
                    .setSchool(school)
                    .setGender(gender)
                    .build();

            repo.save(student);
        }

        List<Student> allStudents = repo.getAll();
        System.out.println("\nMaster List of Students:");
        for (Student s : allStudents) {
            System.out.println(s);
        }

        scanner.close();
    }
}
