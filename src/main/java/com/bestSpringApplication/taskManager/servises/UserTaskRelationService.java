package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.study.implementations.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.repos.UserTaskRelationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTaskRelationService {

    private final UserTaskRelationRepo utrRepo;

    @Autowired
    public UserTaskRelationService(UserTaskRelationRepo utrRepo) {
        this.utrRepo = utrRepo;
    }

    public boolean saveRelation(UserTaskRelationImpl relation){
        utrRepo.save(relation);
        return true;
    }

    public boolean removeRelation(UserTaskRelationImpl relation){
        utrRepo.delete(relation);
        return true;
    }

    public List<UserTaskRelationImpl> getRelationList(){
        return utrRepo.findAll();
    }

    public Optional<UserTaskRelationImpl> getRelationById(int id){
        return utrRepo.findById(id);
    }


    public Optional<UserTaskRelationImpl> getRelationById(String id){
        try {
            int id0 = Integer.parseInt(id);
            return getRelationById(id0);
        }catch (NumberFormatException ex){
            return Optional.empty();
        }
    }

}





