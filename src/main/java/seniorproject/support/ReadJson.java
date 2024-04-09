package seniorproject.support;

import seniorproject.models.concretes.Group;
import seniorproject.models.concretes.Professor;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.Student;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadJson {

    private static long groupIdCounter = 1;  // Counter for group IDs
    private static long userIdCounter = 1;  // Counter for professor IDs
    private static long projectIdCounter = 1;  // Counter for project IDs
    public static void main(String[] args) {
        String filePath = "/Users/erturkmens/hacettepe-senior-project-page/src/main/java/seniorproject/support/senior_projects.json";

        try {
            File jsonFile = new File(filePath);
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(jsonFile);
            if (jsonNode.isArray()) {
                Iterator<JsonNode> elements = jsonNode.elements();
                while (elements.hasNext()) {
                    JsonNode projectNode = elements.next();
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/HacettepeSeniorProjectPage_Dummy", "postgres", "123");

                    String projectName = projectNode.get("project_name").asText();
                    String projectTerm = projectNode.get("project_term").asText();
                    String youtubeLink = projectNode.get("youtube_link").asText();
                    String reportLink = projectNode.get("report_link").asText();
                    String abstractText = projectNode.get("abstract").asText();
                    String[] students = objectMapper.convertValue(projectNode.get("students"), String[].class);
                    String[] supervisors = objectMapper.convertValue(projectNode.get("supervisor"), String[].class);

                    Project project = new Project();
                    project.setId(generateProjectId());
                    project.setName(projectName);
                    project.setTerm(projectTerm);
                    project.setYoutubeLink(youtubeLink);
                    project.setReportLink(reportLink);
                    project.setDescription(abstractText);

                    Group group = new Group();
                    group.setId(generateGroupId());
                    List<Student> studentList = new ArrayList<>();
                    for (String student : students) {
                        Student studentObject = new Student();
                        studentObject.setId(generateUserId());
                        studentObject.setName(student);
                        studentObject.setEmail(student + "@cs.hacettepe.edu.tr");
                        studentObject.setPassword("123456");
                        studentList.add(studentObject);
                    }
                    group.setStudents(studentList);
                    project.setGroup(group);

                    List<Professor> professorList = new ArrayList<>();
                    for(String supervisor : supervisors) {
                        String[] professorNames = supervisor.split(" ");
                        String professorNameLower = "" ;
                        for(int i = 0; i < professorNames.length; i++){
                            if(i==0){
                                String name = TurkishCharacterToEnglish(professorNames[i]);

                                name = Normalizer.normalize(name, Normalizer.Form.NFD);
                                professorNameLower = name.toLowerCase();
                            }
                            if(i==professorNames.length-1){
                                String surname = TurkishCharacterToEnglish(professorNames[i].split("-")[professorNames[i].split("-").length-1]);

                                surname = Normalizer.normalize(surname, Normalizer.Form.NFD);
                                professorNameLower = professorNameLower + surname.toLowerCase();
                            }
                        }

                        Professor professor = new Professor();
                        professor.setName(supervisor);
                        professor.setEmail( professorNameLower+ "@cs.hacettepe.edu.tr");
                        professor.setPassword("123456");
                        professorList.add(professor);
                    }

                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO groups (group_id)  VALUES (?)");
                        preparedStatement.setLong(1, group.getId());
                        preparedStatement.executeUpdate();

                        for(Professor professor: professorList){
                            preparedStatement = connection.prepareStatement("SELECT user_id FROM professors WHERE email = ?");
                            preparedStatement.setString(1, professor.getEmail());
                            ResultSet resultSet = preparedStatement.executeQuery();

                            if (resultSet.next()) {
                                long existingProfessorId = resultSet.getLong("user_id");
                                if (existingProfessorId != 0) {
                                    professor.setId(existingProfessorId);
                                } else {
                                    professor.setId(generateUserId());
                                }
                            }
                            else {
                                professor.setId(generateUserId());
                            }

                        }

                        project.setProfessors(professorList);

                        preparedStatement = connection.prepareStatement("INSERT INTO users (user_id,username,password) VALUES (?,?,'123456')");
                        for (Student student : studentList) {
                            preparedStatement.setLong(1, student.getId());
                            preparedStatement.setString(2, student.getEmail());
                            preparedStatement.executeUpdate();
                        }

                        preparedStatement = connection.prepareStatement("INSERT INTO students (user_id, name, email) VALUES (?, ?, ?)");
                        for (Student student : studentList) {
                            preparedStatement.setLong(1, student.getId());
                            preparedStatement.setString(2, student.getName());
                            preparedStatement.setString(3, student.getEmail());
                            preparedStatement.executeUpdate();
                        }

                        preparedStatement = connection.prepareStatement("INSERT INTO student_group (user_id, group_id) VALUES (?, ?)");

                        for (Student student : studentList) {
                            preparedStatement.setLong(1, student.getId());
                            preparedStatement.setLong(2, group.getId());
                            preparedStatement.executeUpdate();
                        }

                        for (Professor professor : professorList) {
                            try {
                                preparedStatement = connection.prepareStatement("INSERT INTO users (user_id,username,password) VALUES (?,?,'123456')");
                                preparedStatement.setLong(1, professor.getId());
                                preparedStatement.setString(2, professor.getEmail());
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Professor already exists:" + professor.getId());
                            }
                        }


                        for (Professor professor : professorList) {
                            try {
                                preparedStatement = connection.prepareStatement("INSERT INTO professors (user_id, name, email) VALUES (?, ?, ?)");
                                preparedStatement.setLong(1, professor.getId());
                                preparedStatement.setString(2, professor.getName());
                                preparedStatement.setString(3, professor.getEmail());
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Professor already exists:" + professor.getId());
                            }
                        }


                        preparedStatement = connection.prepareStatement("INSERT INTO projects (project_id, name, term, youtube_link, report_link, group_id,isworking) VALUES (?, ?, ?, ?, ?, ?,'true')");
                        preparedStatement.setLong(1, project.getId());
                        preparedStatement.setString(2, project.getName());
                        preparedStatement.setString(3, project.getTerm());
                        preparedStatement.setString(4, project.getYoutubeLink());
                        preparedStatement.setString(5, project.getReportLink());
                        preparedStatement.setLong(6, group.getId());
                        preparedStatement.executeUpdate();

                        preparedStatement = connection.prepareStatement("INSERT INTO project_professor (project_id, user_id) VALUES (?, ?)");
                        for (Professor professor : professorList) {
                            preparedStatement.setLong(1, project.getId());
                            preparedStatement.setLong(2, professor.getId());
                            preparedStatement.executeUpdate();
                        }

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
    private static synchronized long generateGroupId() {
        return groupIdCounter++;
    }

    private static synchronized long generateUserId() {
        return userIdCounter++;
    }

    private static synchronized long generateProjectId() {
        return projectIdCounter++;
    }
    public static String TurkishCharacterToEnglish(String text)
    {
        char[] turkishChars = {'ı', 'ğ', 'İ', 'Ğ', 'ç', 'Ç', 'ş', 'Ş', 'ö', 'Ö', 'ü', 'Ü'};
        char[] englishChars = {'i', 'g', 'I', 'G', 'c', 'C', 's', 'S', 'o', 'O', 'u', 'U'};

        for (int i = 0; i < turkishChars.length; i++){
            text = text.replace(turkishChars[i], englishChars[i]);
        }
        return text;
    }
}
