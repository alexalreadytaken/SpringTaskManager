package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.model.user.UserModel;
import com.bestSpringApplication.taskManager.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepo userRepo;

    @Autowired
    public void userService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        final Optional<UserModel> user = userRepo.findByMail(str);
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
    public void saveUser(UserModel userModel){
        userRepo.save(userModel);
    }
}
