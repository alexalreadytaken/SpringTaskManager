package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.entities.User;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.repos.UserRepo;
import com.bestSpringApplication.taskManager.servises.interfaces.UserService;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO: 4/13/21 ldap
// FIXME: 4/25/21 all
@Deprecated
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

    @Deprecated
    public void validateUserExistsAndContainsRoleOrThrow(String userId, Role role){
        getUserByIdOrThrow(userId)
                .filter(el->el.getRole()==role)
                .orElseThrow(()-> {
                    log.warn("user by id '{}' not contains role '{}'",userId,role);
                    return new ContentNotFoundException("Роль пользователя не соответствует требованиям");
                });
    }

    @Deprecated
    public void validateUserExistsOrThrow(String userId){
        getUserByIdOrThrow(userId);
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

    private Optional<User> getUserByIdOrThrow(String userId) {
        return Optional.ofNullable(getUserById(userId)
                .orElseThrow(() -> {
                    log.warn("user by id '{}' not found", userId);
                    return new ContentNotFoundException("Пользователь не найден");
                }));
    }
}
















