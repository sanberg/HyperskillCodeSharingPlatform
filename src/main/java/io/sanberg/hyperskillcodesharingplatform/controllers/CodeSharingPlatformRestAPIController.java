package io.sanberg.hyperskillcodesharingplatform.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.sanberg.hyperskillcodesharingplatform.entities.CodeSnippet;
import io.sanberg.hyperskillcodesharingplatform.repository.CodeSnippetsRepository;
import io.sanberg.hyperskillcodesharingplatform.services.CodeSnippetsService;

import java.util.ArrayList;

@RestController
public class CodeSharingPlatformRestAPIController {
    private final CodeSnippetsRepository snippetsRepository;
    private final CodeSnippetsService snippetsService;
    Logger logger = LoggerFactory.getLogger(CodeSnippetsService.class);


    @Autowired
    public CodeSharingPlatformRestAPIController(CodeSnippetsRepository codeSnippetsRepository, CodeSnippetsService snippetsService) {
        this.snippetsRepository = codeSnippetsRepository;
        this.snippetsService = snippetsService;
    }



    @GetMapping("/api/code/{uiid}")
    public ResponseEntity<CodeSnippet> getCodeJson(@PathVariable String uiid) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        CodeSnippet codeSnippet =  snippetsRepository.findByUiid(uiid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found id: " + uiid)); //numbers start from 1
        logger.info("requested: " + uiid);
        snippetsService.checkLimitationsAndUpdate(codeSnippet);
        return new ResponseEntity<>(codeSnippet, respHeaders, HttpStatus.OK);
    }

    @PostMapping("/api/code/new")
    public ResponseEntity<String> writeCodeSnippet(@RequestBody CodeSnippet codeSnippet) {
        if (codeSnippet.getTime() > 0) {
            codeSnippet.setIsTimeLimited(true);
        }
        if (codeSnippet.getViews() > 0) {
            codeSnippet.setIsViewsLimited(true);
        }
        logger.info("created: " + codeSnippet);
        return new ResponseEntity<>("{ \"id\" : \"" +
                snippetsRepository.save(codeSnippet).getUiid() + "\" }", HttpStatus.OK);
    }

    @GetMapping("/api/code/latest")
    public ResponseEntity<ArrayList<CodeSnippet>> getLatestCodeSnippetsJson() {
        HttpHeaders respHeaders = new HttpHeaders();
        ArrayList<CodeSnippet> arrayList = snippetsRepository.findTop10ByIsTimeLimitedFalseAndIsViewsLimitedFalseOrderByDateDesc();
        respHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(arrayList, respHeaders, HttpStatus.OK);
    }

    @GetMapping("/api/deleteAll")
    public ResponseEntity deleteAll() {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        snippetsRepository.deleteAll();
        System.out.println("Deleted");
        return new ResponseEntity<>(respHeaders, HttpStatus.OK);
    }
}
