import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static final String DB_URL = "jdbc:sqlite:students.db";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public Repository() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tbl_students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    first_name TEXT NOT NULL,
                    middle_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    age INTEGER NOT NULL,
                    email TEXT NOT NULL,
                    course TEXT NOT NULL,
                    year_level INTEGER NOT NULL,
                    section TEXT NOT NULL,
                    school TEXT NOT NULL,
                    gender TEXT NOT NULL
                )
                """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Table checked/created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public Student save(Student student) {

        String sql = """
                INSERT INTO tbl_students 
                (first_name, middle_name, last_name, age, email, course, year_level, section, school, gender)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getMiddleName());
            pstmt.setString(3, student.getLastName());
            pstmt.setInt(4, student.getAge());
            pstmt.setString(5, student.getEmail());
            pstmt.setString(6, student.getCourse());
            pstmt.setInt(7, student.getYearLevel());
            pstmt.setString(8, student.getSection());
            pstmt.setString(9, student.getSchool());
            pstmt.setString(10, student.getGender());

            int rowsInserted = pstmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);

            if (rowsInserted > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);

                        return new Student.Builder()
                                .setId(generatedId)
                                .setFirstName(student.getFirstName())
                                .setMiddleName(student.getMiddleName())
                                .setLastName(student.getLastName())
                                .setAge(student.getAge())
                                .setEmail(student.getEmail())
                                .setCourse(student.getCourse())
                                .setYearLevel(student.getYearLevel())
                                .setSection(student.getSection())
                                .setSchool(student.getSchool())
                                .setGender(student.getGender())
                                .build();
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error saving student: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM tbl_students";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Student student = new Student.Builder()
                        .setId(rs.getInt("id"))
                        .setFirstName(rs.getString("first_name"))
                        .setMiddleName(rs.getString("middle_name"))
                        .setLastName(rs.getString("last_name"))
                        .setAge(rs.getInt("age"))
                        .setEmail(rs.getString("email"))
                        .setCourse(rs.getString("course"))
                        .setYearLevel(rs.getInt("year_level"))
                        .setSection(rs.getString("section"))
                        .setSchool(rs.getString("school"))
                        .setGender(rs.getString("gender"))
                        .build();

                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }
}