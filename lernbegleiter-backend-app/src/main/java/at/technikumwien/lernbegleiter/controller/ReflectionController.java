package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.services.ReflectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class ReflectionController {
    @Autowired
    private ReflectionService reflectionService;
}
