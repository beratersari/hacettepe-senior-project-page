package seniorproject.api.controllers;
import org.springframework.security.core.userdetails.User;
public class RoleChecker {

    public static boolean isStudent(User user){
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
    }
    public static boolean isProfessor(User user){
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFESSOR"));
    }
    public static boolean isAdmin(User user){
        return user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
