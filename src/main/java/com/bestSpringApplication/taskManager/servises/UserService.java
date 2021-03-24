package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.utils.exceptions.forClient.UserNotFoundException;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.repos.UserRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @NonNull private final UserRepo userRepo;
    @NonNull private final PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        final Optional<User> user = userRepo.findByMail(str);// it should/так надо
        return user.orElseThrow(()-> new UsernameNotFoundException("USER NOT FOUND"));
    }

    public boolean containsMail(String mail){
        try {
            loadUserByUsername(mail);
            return true;
        }catch (UsernameNotFoundException ex){
            return false;
        }
    }

    public void validateExistsAndContainsRole(String studentId,Role role){
        Optional.ofNullable(getUserById(studentId)
                .orElseThrow(()->new UserNotFoundException("Пользователь не найден")))
                .filter(el->el.getRole()==role)
                .orElseThrow(()-> new UserNotFoundException("Роль пользователя не соответствует требованиям"));
    }

    public void saveUser(User user){
        String password = user.getPassword();
        String encodedPass = encoder.encode(password);
        user.setPassword(encodedPass);
        userRepo.save(user);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public boolean existsUserById(String id){
        try {
            long id0 = Long.parseLong(id);
            return existsUserById(id0);
        }catch (NumberFormatException ex){
            return false;
        }
    }
    public boolean existsUserById(Long id){
        return userRepo.existsById(id);
    }

    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }
    public Optional<User> getUserById(String id){
        try {
            long id0 = Integer.parseInt(id);
            return userRepo.findById(id0);
        }catch (NumberFormatException ex){
            return Optional.empty();
        }
    }
}
















