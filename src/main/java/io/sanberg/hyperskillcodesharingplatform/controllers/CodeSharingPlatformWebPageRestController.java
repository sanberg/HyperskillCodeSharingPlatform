package io.sanberg.hyperskillcodesharingplatform.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import io.sanberg.hyperskillcodesharingplatform.entities.CodeSnippet;
import io.sanberg.hyperskillcodesharingplatform.repository.CodeSnippetsRepository;
import io.sanberg.hyperskillcodesharingplatform.services.CodeSnippetsService;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class CodeSharingPlatformWebPageRestController {
    private final CodeSnippetsRepository snippetsRepository;
    private final CodeSnippetsService snippetsService;
    Logger logger = LoggerFactory.getLogger(CodeSnippetsService.class);


    @Autowired
    public CodeSharingPlatformWebPageRestController(CodeSnippetsRepository codeSnippetsRepository, CodeSnippetsService snippetsService) {
        this.snippetsRepository = codeSnippetsRepository;
        this.snippetsService = snippetsService;
    }

    @GetMapping("/code/{uiid}")
    public ModelAndView getCodeHtmlPage(@PathVariable String uiid) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.set(HttpHeaders.CONTENT_TYPE, "text/html");
        CodeSnippet snippet = snippetsRepository.findByUiid(uiid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found id: " + uiid));
        var params = new HashMap<String, CodeSnippet>();
        logger.info("requested: " + uiid);
        snippetsService.checkLimitationsAndUpdate(snippet);
        params.put("snippet", snippet);
        return new ModelAndView("code", params);
    }

    @GetMapping("/code/new")
    public ModelAndView getNewCodePage() {
        return new ModelAndView("newCode");
    }

    @GetMapping("/code/latest")
    public ModelAndView getLatestCodePage() {
        ArrayList<CodeSnippet> arrayList = snippetsRepository.findTop10ByIsTimeLimitedFalseAndIsViewsLimitedFalseOrderByDateDesc();
        var params = new HashMap<String, ArrayList<CodeSnippet>>();
        params.put("snippets", arrayList);
        return new ModelAndView("latestSnippets", params);
    }
}