package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.models.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class AuxiliaryController {

    private final String STATUS_VALUES_RU = "/statuses/ru";

    @GetMapping(STATUS_VALUES_RU)
    public List<String> getRuStatusValues(){
        return Status.ruValues();
    }

}
