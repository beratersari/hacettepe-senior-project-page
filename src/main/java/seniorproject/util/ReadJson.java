package seniorproject.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import seniorproject.models.concretes.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import seniorproject.models.concretes.enums.EProjectStatus;
import seniorproject.models.concretes.enums.EProjectTypeStatus;

import java.io.File;
import java.sql.*;
import java.text.Normalizer;
import java.util.*;

@RestController
public class ReadJson {

    private static long groupIdCounter = 1;  // Counter for group IDs
    private static long userIdCounter = 1;  // Counter for professor IDs
    private static long projectIdCounter = 1;  // Counter for project IDs
    private static long projectTypeIdCounter = 1;  // Counter for project type IDs

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String hashedPassword = passwordEncoder.encode(("123456"));
    public static void main(String[] args) {
        String filePath = "../hacettepe-senior-project-page/src/main/java/seniorproject/util/senior_projects.json";

        try {
            File jsonFile = new File(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/HacettepeSeniorProjectPage_Dummy", "postgres", "123");

            JsonNode jsonNode = objectMapper.readTree(jsonFile);


            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO roles (id, name) VALUES (?, ?)");
            preparedStatement.setObject(1, generateGeneralId());
            preparedStatement.setString(2, "ROLE_ADMIN");
            preparedStatement.executeUpdate();


            preparedStatement = connection.prepareStatement("INSERT INTO roles (id, name)  VALUES (?, ?)");
            preparedStatement.setObject(1, generateGeneralId());
            preparedStatement.setString(2, "ROLE_PROFESSOR");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO roles (id, name)  VALUES (?, ?)");
            preparedStatement.setObject(1, generateGeneralId());
            preparedStatement.setString(2, "ROLE_STUDENT");
            preparedStatement.executeUpdate();
            if (jsonNode.isArray()) {
                Iterator<JsonNode> elements = jsonNode.elements();
                while (elements.hasNext()) {
                    JsonNode projectNode = elements.next();
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/HacettepeSeniorProjectPage_Dummy", "postgres", "123");


                    String projectName = projectNode.get("project_name").asText();
                    String projectTerm = projectNode.get("project_term").asText();
                    String youtubeLink = projectNode.get("youtube_link").asText();
                    String reportLink = projectNode.get("report_link").asText();
                    String abstractText = projectNode.get("abstract").asText();
                    String[] students = objectMapper.convertValue(projectNode.get("students"), String[].class);
                    String[] supervisors = objectMapper.convertValue(projectNode.get("supervisor"), String[].class);

                    Project project = new Project();
                    project.setId(generateGeneralId());
                    project.setTitle(projectName);
                    try{
                        preparedStatement = connection.prepareStatement("SELECT id FROM project_types WHERE name = ?");
                        preparedStatement.setString(1, "Senior Project" + " - " + projectTerm);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        SeniorProject seniorProject = new SeniorProject();
                        seniorProject.setTerm(projectTerm);
                        seniorProject.setName("Senior Project" + " - " + projectTerm);
                        if(Objects.equals(projectTerm, "2022-2023")){
                            seniorProject.setActiveness(EProjectTypeStatus.ACTIVE);
                        }
                        else{
                            seniorProject.setActiveness(EProjectTypeStatus.ARCHIVED);
                        }

                        if (resultSet.next()) {
                            UUID existingProjectTypeId = resultSet.getObject("id", UUID.class);
                            if (existingProjectTypeId != null) {
                                seniorProject.setId(existingProjectTypeId);
                            } else {
                                seniorProject.setId(generateGeneralId());
                            }
                        }
                        else {
                            seniorProject.setId(generateGeneralId());
                        }
                        project.setProjectType(seniorProject);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    project.setYoutubeLink(youtubeLink);
                    project.setReportLink(reportLink);
                    project.setDescription(abstractText);
                    project.setEProjectStatus(EProjectStatus.WORKING);


                    try{
                        preparedStatement = connection.prepareStatement("INSERT INTO project_types (id, name, activeness) VALUES (?, ?, ?)");
                        preparedStatement.setObject(1, project.getProjectType().getId());
                        preparedStatement.setString(2, "Senior Project" + " - " + projectTerm);
                        preparedStatement.setString(3, String.valueOf(project.getProjectType().getActiveness()));
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Project Type already exists:" + project.getProjectType().getId());
                    }

                    try{
                        preparedStatement = connection.prepareStatement("INSERT INTO senior_projects (id, term) VALUES (?, ?)");
                        preparedStatement.setObject(1, project.getProjectType().getId());
                        preparedStatement.setString(2, ((SeniorProject) project.getProjectType()).getTerm());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Senior Projects already exists:" + project.getProjectType().getId());

                    }

                    Group group = new Group();
                    group.setId(generateGeneralId());
                    List<Student> studentList = new ArrayList<>();
                    for (String student : students) {
                        Student studentObject = new Student();
                        studentObject.setId(generateGeneralId());
                        studentObject.setEmail(student);
                        studentObject.setPassword(hashedPassword);
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
                        professor.setEmail( professorNameLower);
                        professor.setPassword(hashedPassword);
                        professorList.add(professor);
                    }

                    try {
                        preparedStatement = connection.prepareStatement("INSERT INTO groups (group_id,name)  VALUES (?,?)");
                        preparedStatement.setObject(1, group.getId());
                        preparedStatement.setString(2,group.getName());
                        preparedStatement.executeUpdate();

                        for(Professor professor: professorList){
                            preparedStatement = connection.prepareStatement("SELECT p.id FROM users left join public.professors p on users.id = p.id WHERE email = ?");
                            preparedStatement.setString(1, professor.getEmail());
                            ResultSet resultSet = preparedStatement.executeQuery();

                            if (resultSet.next()) {
                                UUID existingProfessorId = resultSet.getObject("id", UUID.class);
                                if (existingProfessorId != null) {
                                    professor.setId(existingProfessorId);
                                } else {
                                    professor.setId(generateGeneralId());
                                }
                            }
                            else {
                                professor.setId(generateGeneralId());
                            }

                        }

                        project.setProfessors(professorList);

                        preparedStatement = connection.prepareStatement("INSERT INTO users (id,email,password,username) VALUES (?,?, ?, ?)");
                        for (Student student : studentList) {
                            preparedStatement.setObject(1, student.getId());
                            preparedStatement.setString(2, student.getEmail());
                            preparedStatement.setString(3, student.getPassword());
                            preparedStatement.setString(4, student.getEmail());
                            preparedStatement.executeUpdate();
                        }

                        preparedStatement = connection.prepareStatement("INSERT INTO students (id) VALUES (?)");
                        for (Student student : studentList) {
                            preparedStatement.setObject(1, student.getId());
                            preparedStatement.executeUpdate();
                        }

                        preparedStatement = connection.prepareStatement("INSERT INTO student_group (user_id, group_id) VALUES (?, ?)");

                        for (Student student : studentList) {
                            preparedStatement.setObject(1, student.getId());
                            preparedStatement.setObject(2, group.getId());
                            preparedStatement.executeUpdate();
                        }

                        for (Professor professor : professorList) {
                            try {
                                preparedStatement = connection.prepareStatement("INSERT INTO users (id,email,password,username) VALUES (?,?, ?, ?)");
                                preparedStatement.setObject(1, professor.getId());
                                preparedStatement.setString(2, professor.getEmail());
                                preparedStatement.setString(3, professor.getPassword());
                                preparedStatement.setString(4, professor.getEmail());
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Professor already exists:" + professor.getId());
                            }
                        }
                        for (Professor professor : professorList) {
                            try {
                                preparedStatement = connection.prepareStatement("INSERT INTO users_to_roles (user_id,role_id) VALUES (?,?)");
                                preparedStatement.setObject(1, professor.getId());
                                preparedStatement.setLong(2, 2);
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Professor Role Aleeady exists:" + professor.getId());
                            }
                        }
                        for (Student student : studentList) {
                            try {
                                preparedStatement = connection.prepareStatement("INSERT INTO users_to_roles (user_id,role_id) VALUES (?,?)");
                                preparedStatement.setObject(1, student.getId());
                                preparedStatement.setLong(2, 3);
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Student Role Aleeady exists:" + student.getId());
                            }
                        }

                        for (Professor professor : professorList) {
                            try {
                                preparedStatement = connection.prepareStatement("INSERT INTO professors (id) VALUES (?)");
                                preparedStatement.setObject(1, professor.getId());
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("Professor already exists:" + professor.getId());
                            }
                        }


                        preparedStatement = connection.prepareStatement("INSERT INTO projects (project_id, title, project_type_id, youtube_link, report_link, group_id,eproject_status) VALUES (?, ?, ?, ?, ?, ?,?)");
                        preparedStatement.setObject(1, project.getId());
                        preparedStatement.setString(2, project.getTitle());
                        preparedStatement.setObject(3, project.getProjectType().getId());
                        preparedStatement.setString(4, project.getYoutubeLink());
                        preparedStatement.setString(5, project.getReportLink());
                        preparedStatement.setObject(6, group.getId());
                        preparedStatement.setString(7, String.valueOf(project.getEProjectStatus()));
                        preparedStatement.executeUpdate();

                        preparedStatement = connection.prepareStatement("INSERT INTO project_professor (project_id, user_id) VALUES (?, ?)");
                        for (Professor professor : professorList) {
                            preparedStatement.setObject(1, project.getId());
                            preparedStatement.setObject(2, professor.getId());
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

    private static synchronized UUID generateGeneralId() {
        return UUID.randomUUID();
    }

    private static synchronized long generateProjectTypeId() {
        return projectTypeIdCounter++;
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