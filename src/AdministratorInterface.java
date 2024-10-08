import java.util.*;

// Student Class Definition
class Student {
    private String name;
    private int studentId;
    private Map<Course, Double> enrolledCourses;  // Course and their respective grades

    // Constructor
    public Student(String name, int studentId) {
        this.name = name;
        this.studentId = studentId;
        this.enrolledCourses = new HashMap<>();
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Method to enroll in a course
    public void enrollInCourse(Course course) {
        if (course.getEnrolledStudents() >= course.getMaxCapacity()) {
            System.out.println("Cannot enroll in " + course.getCourseName() + ". Maximum capacity reached.");
            return;
        }
        
        if (enrolledCourses.containsKey(course)) {
            System.out.println(name + " is already enrolled in this course.");
        } else {
            enrolledCourses.put(course, null);  // Initial grade is set to null
            course.incrementEnrolledStudents();  // Update course enrollment count
            System.out.println(name + " successfully enrolled in " + course.getCourseName());
        }
    }

    // Method to assign a grade
    public void assignGrade(Course course, double grade) {
        if (enrolledCourses.containsKey(course)) {
            enrolledCourses.put(course, grade);
            System.out.println("Grade " + grade + " assigned to " + name + " for " + course.getCourseName());
        } else {
            System.out.println(name + " is not enrolled in this course.");
        }
    }

    // Display student information and enrolled courses
    public void displayStudentDetails() {
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + studentId);
        System.out.println("Enrolled Courses and Grades:");
        for (Map.Entry<Course, Double> entry : enrolledCourses.entrySet()) {
            System.out.println(entry.getKey().getCourseName() + ": " + (entry.getValue() != null ? entry.getValue() : "No grade assigned"));
        }
    }
}

// Course Class Definition
class Course {
    private String courseCode;
    private String courseName;
    private int maxCapacity;
    private int enrolledStudents;

    // Static variable to track total enrolled students
    private static int totalEnrolledStudents = 0;

    // Constructor
    public Course(String courseCode, String courseName, int maxCapacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = 0;
    }

    // Getter methods
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    // Increment enrolled students
    public void incrementEnrolledStudents() {
        if (enrolledStudents < maxCapacity) {
            enrolledStudents++;
            totalEnrolledStudents++;
        } else {
            System.out.println("Course capacity reached. Cannot enroll more students.");
        }
    }

    // Static method to retrieve total enrolled students
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }
}

// Course Management Class Definition
class CourseManagement {
    private static List<Course> courses = new ArrayList<>();
    private static Map<Student, Double> studentGrades = new HashMap<>();

    // Add a new course
    public static void addCourse(String code, String name, int maxCapacity) {
        courses.add(new Course(code, name, maxCapacity));
        System.out.println("Course added: " + name);
    }

    // Retrieve a course by its code
    public static Course getCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    // Enroll a student in a course
    public static void enrollStudent(Student student, Course course) {
        student.enrollInCourse(course);
    }

    // Assign a grade to a student
    public static void assignGrade(Student student, Course course, double grade) {
        student.assignGrade(course, grade);
    }

    // Calculate overall grade
    public static void calculateOverallGrade(Student student) {
        double total = 0;
        int count = 0;
        for (Double grade : student.enrolledCourses.values()) {
            if (grade != null) {
                total += grade;
                count++;
            }
        }
        double overallGrade = (count > 0) ? total / count : 0;
        System.out.println("Overall Grade for " + student.getName() + ": " + overallGrade);
    }
}

// Main Class: Administrator Interface
public class AdministratorInterface {
    public static void main(String[] args) {
        // Adding courses using CourseManagement
        CourseManagement.addCourse("CS101", "Introduction to Computer Science", 3);
        CourseManagement.addCourse("MATH101", "Calculus I", 2);

        // Creating student objects
        Student student1 = new Student("Abdullah", 1);
        Student student2 = new Student("Badmus", 2);

        // Retrieve courses from CourseManagement
        Course cs101 = CourseManagement.getCourseByCode("CS101");
        Course math101 = CourseManagement.getCourseByCode("MATH101");

        // Enroll students in courses
        CourseManagement.enrollStudent(student1, cs101);
        CourseManagement.enrollStudent(student2, math101);

        // Assign grades
        CourseManagement.assignGrade(student1, cs101, 90.0);
        CourseManagement.assignGrade(student2, math101, 85.0);

        // Calculate overall grades
        CourseManagement.calculateOverallGrade(student1);
        CourseManagement.calculateOverallGrade(student2);
    }
}
