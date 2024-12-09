//package com.example.demo.controller;
//
//import com.example.demo.service.JDoodleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/compile")
//@CrossOrigin(origins = "http://localhost:4200")  // Allowing Angular frontend access
//public class JDoodleController {
//
//    @Autowired
//    private JDoodleService jdoodleService;
//
//    @PostMapping("/execute")
//    public ResponseEntity<String> executeCode(
//            @RequestBody CodeRequest codeRequest
//    ) {
//        // Call JDoodle service
//        String response = jdoodleService.executeCode(
//                codeRequest.getScript(),
//                codeRequest.getLanguage(),
//                codeRequest.getVersionIndex(),
//                codeRequest.getInput()
//
//        );
//        return ResponseEntity.ok(response);
//    }
//}
//
//class CodeRequest {
//    private String script;
//    private String language;
//    private String versionIndex;
//    private String input;
//
//    // Getters and Setters
//    public String getScript() {
//        return script;
//    }
//
//    public void setScript(String script) {
//        this.script = script;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public String getVersionIndex() {
//        return versionIndex;
//    }
//
//    public void setVersionIndex(String versionIndex) {
//        this.versionIndex = versionIndex;
//    }
//
//    public String getInput() {
//        return input;
//    }
//
//    public void setInput(String input) {
//        this.input = input;
//    }
//}



package com.example.demo.controller;

import com.example.demo.serviceImpl.JDoodleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compile")
@CrossOrigin(origins = "http://localhost:4200")  // Allowing Angular frontend access
public class JDoodleController {

    @Autowired
    private JDoodleService jdoodleService;

    @PostMapping("/execute")
    public ResponseEntity<String> executeCode(
            @RequestBody CodeRequest codeRequest
    ) {
        // Call JDoodle service
        String response = jdoodleService.executeCode(
                codeRequest.getScript(),
                codeRequest.getLanguage(),
                codeRequest.getVersionIndex(),
                codeRequest.getInput()

        );
        return ResponseEntity.ok(response);
    }
}

class CodeRequest {
    private String script;
    private String language;
    private String versionIndex;
    private String input;

    // Getters and Setters
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVersionIndex() {
        return versionIndex;
    }

    public void setVersionIndex(String versionIndex) {
        this.versionIndex = versionIndex;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}




