package seniorproject.api.controllers;
import org.springframework.security.core.userdetails.User;
public class RoleChecker {

    public static boolean isStudent(User user){
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("STUDENT"));
    }
    public static boolean isProfessor(User user){
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("PROFESSOR"));
    }
    public static boolean isAdmin(User user){
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }
}
