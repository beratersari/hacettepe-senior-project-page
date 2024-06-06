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
import java.util.Date;

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
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HacettepeSeniorProjectPage_Dummy", "postgres", "123");

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

            preparedStatement = connection.prepareStatement("INSERT INTO roles (id, name)  VALUES (?, ?)");
            preparedStatement.setObject(1, generateGeneralId());
            preparedStatement.setString(2, "ROLE_USER");
            preparedStatement.executeUpdate();
            if (jsonNode.isArray()) {
                Iterator<JsonNode> elements = jsonNode.elements();
                while (elements.hasNext()) {
                    JsonNode projectNode = elements.next();
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HacettepeSeniorProjectPage_Dummy", "postgres", "123");


                    String projectName = projectNode.get("project_name").asText();
                    String projectTerm = projectNode.get("project_term").asText();
                    String youtubeLink = projectNode.get("youtube_link").asText();
                    String reportLink = projectNode.get("report_link").asText();
                    String abstractText = projectNode.get("abstract").asText();
                    String[] students = objectMapper.convertValue(projectNode.get("students"), String[].class);
                    String[] supervisors = objectMapper.convertValue(projectNode.get("supervisor"), String[].class);
                    String[] keywords = objectMapper.convertValue(projectNode.get("keywords"), String[].class);

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
                            List<Timeline> timelines = new ArrayList<>();
                            preparedStatement = connection.prepareStatement("SELECT * FROM timelines WHERE project_type_id = ?");
                            preparedStatement.setObject(1, existingProjectTypeId);
                            ResultSet timelineResultSet = preparedStatement.executeQuery();
                            while (timelineResultSet.next()) {
                                Timeline timeline = new Timeline();
                                timeline.setId(timelineResultSet.getObject("id", UUID.class));
                                timeline.setDeliveryDate(timelineResultSet.getDate("delivery_date"));
                                timeline.setDeliveryName(timelineResultSet.getString("delivery_name"));
                                timeline.setProjectType(seniorProject);
                                timelines.add(timeline);
                            }
                            if (existingProjectTypeId != null) {
                                seniorProject.setId(existingProjectTypeId);
                                seniorProject.setTimelines(timelines);

                            } else {
                                seniorProject.setId(generateGeneralId());
                                seniorProject.setTimelines(timelines);

                            }
                        }
                        else {
                            seniorProject.setId(generateGeneralId());
                            List<Timeline> timelines = new ArrayList<>();
                            Timeline timeline = new Timeline();
                            timeline.setId(generateGeneralId());
                            String year_one = projectTerm.split("-")[0];
                            String year_two = projectTerm.split("-")[1];
                            timeline.setDeliveryDate(java.sql.Date.valueOf(year_one +"-10-30"));
                            timeline.setDeliveryName("Project Proposal");
                            timeline.setProjectType(seniorProject);
                            timelines.add(timeline);

                            timeline = new Timeline();
                            timeline.setId(generateGeneralId());
                            timeline.setDeliveryDate(java.sql.Date.valueOf(year_two+"-01-06"));
                            timeline.setDeliveryName("End Of Term Development Report");
                            timeline.setProjectType(seniorProject);
                            timelines.add(timeline);

                            timeline = new Timeline();
                            timeline.setId(generateGeneralId());
                            timeline.setDeliveryDate(java.sql.Date.valueOf(year_two+"-04-15"));
                            timeline.setDeliveryName("Term Study Plan");
                            timeline.setProjectType(seniorProject);
                            timelines.add(timeline);

                            timeline = new Timeline();
                            timeline.setId(generateGeneralId());
                            timeline.setDeliveryDate(java.sql.Date.valueOf(year_two+"-06-30"));
                            timeline.setDeliveryName("End Of Project");
                            timeline.setProjectType(seniorProject);
                            timelines.add(timeline);

                            seniorProject.setTimelines(timelines);
                        }
                        project.setProjectType(seniorProject);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }




                    project.setDemoLink(youtubeLink);
                    project.setDescription(abstractText);
                    if(Objects.equals(projectTerm, "2022-2023")){
                        project.setEProjectStatus(EProjectStatus.WORKING);
                    }
                    else{
                        project.setEProjectStatus(EProjectStatus.ARCHIVED);
                    }




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

                   try {
                       preparedStatement = connection.prepareStatement("INSERT INTO  timelines (id,delivery_date,delivery_name,project_type_id) VALUES (?,?,?,?)");
                          for (Timeline timeline : ((SeniorProject) project.getProjectType()).getTimelines()) {
                            preparedStatement.setObject(1, timeline.getId());
                            preparedStatement.setDate(2, new java.sql.Date(timeline.getDeliveryDate().getTime()));
                            preparedStatement.setString(3, timeline.getDeliveryName());
                            preparedStatement.setObject(4, project.getProjectType().getId());
                            preparedStatement.executeUpdate();
                          }
                    } catch (SQLException e) {
                        System.out.println("Timeline already exists:" + project.getProjectType().getId());
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
                                preparedStatement = connection.prepareStatement("SELECT id FROM roles WHERE name = ?");
                                preparedStatement.setString(1, "ROLE_PROFESSOR");
                                ResultSet resultSet = preparedStatement.executeQuery();
                                if (resultSet.next()) {
                                    UUID roleId = resultSet.getObject("id", UUID.class);
                                    preparedStatement = connection.prepareStatement("INSERT INTO users_to_roles (user_id,role_id) VALUES (?,?)");
                                    preparedStatement.setObject(1, professor.getId());
                                    preparedStatement.setObject(2, roleId);
                                    preparedStatement.executeUpdate();
                                }
                            } catch (SQLException e) {
                                System.out.println("Professor Role Aleeady exists:" + professor.getId());
                            }
                        }
                        for (Student student : studentList) {
                            try {
                                preparedStatement = connection.prepareStatement("SELECT id FROM roles WHERE name = ?");
                                preparedStatement.setString(1, "ROLE_STUDENT");
                                ResultSet resultSet = preparedStatement.executeQuery();
                                if (resultSet.next()) {
                                    UUID roleId = resultSet.getObject("id", UUID.class);
                                    preparedStatement = connection.prepareStatement("INSERT INTO users_to_roles (user_id,role_id) VALUES (?,?)");
                                    preparedStatement.setObject(1, student.getId());
                                    preparedStatement.setObject(2, roleId);
                                    preparedStatement.executeUpdate();
                                }
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

                        List<Keyword> keywordList = new ArrayList<>();
                        for( String keyword : keywords){
                            preparedStatement = connection.prepareStatement("SELECT keyword_id FROM keywords WHERE name = ?");
                            preparedStatement.setString(1, keyword);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            UUID keywordId;
                            if (resultSet.next()) {
                                keywordId = resultSet.getObject("keyword_id", UUID.class);
                                Keyword keywordObject = new Keyword();
                                keywordObject.setId(keywordId);
                                keywordObject.setName(keyword);
                                keywordList.add(keywordObject);

                            } else {
                                keywordId = generateGeneralId();
                                preparedStatement = connection.prepareStatement("INSERT INTO keywords (keyword_id, name) VALUES (?, ?)");
                                preparedStatement.setObject(1, keywordId);
                                preparedStatement.setString(2, keyword);
                                preparedStatement.executeUpdate();
                                Keyword keywordObject = new Keyword();
                                keywordObject.setId(keywordId);
                                keywordObject.setName(keyword);
                                keywordList.add(keywordObject);

                            }

                        }

                        project.setKeywords(keywordList);

                        preparedStatement = connection.prepareStatement("INSERT INTO projects (project_id, title, project_type_id, demo_link,poster_path, group_id,eproject_status,description) VALUES (?, ?, ?, ?, ?, ?,?,?)");
                        preparedStatement.setObject(1, project.getId());
                        preparedStatement.setString(2, project.getTitle());
                        preparedStatement.setObject(3, project.getProjectType().getId());
                        preparedStatement.setString(4, project.getDemoLink());
                        preparedStatement.setString(5, project.getPosterPath());
                        preparedStatement.setObject(6, group.getId());
                        preparedStatement.setString(7, String.valueOf(project.getEProjectStatus()));
                        preparedStatement.setString(8, project.getDescription());
                        preparedStatement.executeUpdate();

                        preparedStatement = connection.prepareStatement("INSERT INTO project_professor (project_id, user_id) VALUES (?, ?)");
                        for (Professor professor : professorList) {
                            preparedStatement.setObject(1, project.getId());
                            preparedStatement.setObject(2, professor.getId());
                            preparedStatement.executeUpdate();
                        }

                        for (Keyword keyword : project.getKeywords()) {
                            preparedStatement = connection.prepareStatement("INSERT INTO project_keywords (project_id, keyword_id) VALUES (?, ?)");
                            preparedStatement.setObject(1, project.getId());
                            preparedStatement.setObject(2, keyword.getId());
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