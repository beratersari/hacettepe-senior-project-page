package seniorproject.support;

import seniorproject.models.concretes.Cluster;
import seniorproject.models.concretes.Professor;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.Student;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadJson {

    private static long groupIdCounter = 1;  // Counter for group IDs
    private static long studentIdCounter = 1;  // Counter for student IDs
    private static long professorIdCounter = 1;  // Counter for professor IDs
    private static long projectIdCounter = 1;  // Counter for project IDs
    private Connection connection;
    public static void main(String[] args) {
        String filePath = "/Users/erturkmens/hacettepe-senior-project-page/src/main/java/com/example/bitirmeprojesi/support/senior_projects.json";

        try {
            File jsonFile = new File(filePath);
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(jsonFile);
            if (jsonNode.isArray()) {
                Iterator<JsonNode> elements = jsonNode.elements();
                while (elements.hasNext()) {
                    JsonNode projectNode = elements.next();
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/HacettepeSeniorProjectPage_Dummy", "postgres", "berAt20190909");

                    String projectName = projectNode.get("project_name").asText();
                    String projectTerm = projectNode.get("project_term").asText();
                    String youtubeLink = projectNode.get("youtube_link").asText();
                    String reportLink = projectNode.get("report_link").asText();
                    String abstractText = projectNode.get("abstract").asText();
                    String[] students = objectMapper.convertValue(projectNode.get("students"), String[].class);
                    String supervisor = objectMapper.convertValue(projectNode.get("supervisor"), String[].class)[0];

                    Project project = new Project();
                    project.setId(generateProjectId());
                    project.setName(projectName);
                    project.setTerm(projectTerm);
                    project.setYoutubeLink(youtubeLink);
                    project.setReportLink(reportLink);
                    project.setDescription(abstractText);

                    Cluster group = new Cluster();
                    group.setId(generateGroupId());  // Generate unique ID for the group
                    List<Student> studentList = new ArrayList<>();
                    for (String student : students) {
                        Student studentObject = new Student();
                        studentObject.setId(generateStudentId());  // Generate unique ID for the student
                        studentObject.setName(student);
                        studentObject.setEmail(student + "@cs.hacettepe.edu.tr");
                        studentObject.setPassword("123456");
                        studentList.add(studentObject);
                    }
                    group.setStudents(studentList);
                    project.setCluster(group);

                    Professor professor = new Professor();
                    professor.setName(supervisor);
                    professor.setEmail(supervisor + "@cs.hacettepe.edu.tr");
                    professor.setPassword("123456");



                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cluster (ID, NAME)  VALUES (?, ?)");
                        preparedStatement.setLong(1, group.getId());
                        preparedStatement.setString(2, group.getName());
                        preparedStatement = connection.prepareStatement("SELECT id FROM professor WHERE email = ?");
                        preparedStatement.setString(1, professor.getEmail());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            long existingProfessorId = resultSet.getLong("id");
                            if (existingProfessorId != 0) {
                                professor.setId(existingProfessorId);
                            } else {
                                professor.setId(generateProfessorId());
                            }
                        }
                        else {
                            professor.setId(generateProfessorId());
                        }
                        project.setProfessor(professor);

                        // Insert students with generated IDs
                        preparedStatement = connection.prepareStatement("INSERT INTO student (id, name, email, password) VALUES (?, ?, ?, ?)");
                        for (Student student : studentList) {
                            preparedStatement.setLong(1, student.getId());
                            preparedStatement.setString(2, student.getName());
                            preparedStatement.setString(3, student.getEmail());
                            preparedStatement.setString(4, student.getPassword());
                            preparedStatement.executeUpdate();
                        }

                        preparedStatement = connection.prepareStatement("INSERT INTO student_cluster (student_id, cluster_id) VALUES (?, ?)");

                        for (Student student : studentList) {
                            preparedStatement.setLong(1, student.getId());
                            preparedStatement.setLong(2, group.getId());
                            preparedStatement.executeUpdate();
                        }

                        // Insert professor with generated ID
                        preparedStatement = connection.prepareStatement("INSERT INTO professor (id, name, email, password) VALUES (?, ?, ?, ?)");
                        preparedStatement.setLong(1, professor.getId());
                        preparedStatement.setString(2, professor.getName());
                        preparedStatement.setString(3, professor.getEmail());
                        preparedStatement.setString(4, professor.getPassword());
                        preparedStatement.executeUpdate();

                        // Insert project with generated IDs
                        preparedStatement = connection.prepareStatement("INSERT INTO project (id, name, term, youtube_link, report_link, professor_id, cluster_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
                        preparedStatement.setLong(1, project.getId());
                        preparedStatement.setString(2, project.getName());
                        preparedStatement.setString(3, project.getTerm());
                        preparedStatement.setString(4, project.getYoutubeLink());
                        preparedStatement.setString(5, project.getReportLink());
                        preparedStatement.setLong(6, professor.getId());
                        preparedStatement.setLong(7, group.getId());
                        preparedStatement.executeUpdate();

                        connection.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private Long getExistingProfessorIdByEmail(String professorEmail) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/HacettepeSeniorProjectPage_Dummy", "postgres", "berAt20190909");
            }


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Professor doesn't exist
        return null;
    }
    // Helper methods to generate sequential IDs
    private static synchronized long generateGroupId() {
        return groupIdCounter++;
    }

    private static synchronized long generateStudentId() {
        return studentIdCounter++;
    }

    private static synchronized long generateProfessorId() {
        return professorIdCounter++;
    }

    private static synchronized long generateProjectId() {
        return projectIdCounter++;
    }
}
