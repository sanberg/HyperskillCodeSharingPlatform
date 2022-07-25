package io.sanberg.hyperskillcodesharingplatform.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import io.sanberg.hyperskillcodesharingplatform.entities.CodeSnippet;
import io.sanberg.hyperskillcodesharingplatform.repository.CodeSnippetsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class CodeSnippetsService {
    private final CodeSnippetsRepository codeSnippetsRepository;
    Logger logger = LoggerFactory.getLogger(CodeSnippetsService.class);


    @Autowired
    public CodeSnippetsService(CodeSnippetsRepository codeSnippetsRepository) {
        this.codeSnippetsRepository = codeSnippetsRepository;
    }

    public boolean checkLimitationsAndUpdate(CodeSnippet codeSnippet) {
        if (codeSnippet.isTimeLimited() && codeSnippet.getTime() > 0) {
            this.updateTime(codeSnippet);
        } else if (codeSnippet.isTimeLimited()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity not found"
            );
        }
        if (codeSnippet.isViewsLimited() && codeSnippet.getViews() > 0) {
            this.updateViews(codeSnippet);
        } else if (codeSnippet.isViewsLimited()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity not found"
            );
        }
        return true;
    }

    public CodeSnippet updateViews(CodeSnippet codeSnippet) {
        if (codeSnippet.getViews() > 0) {
            codeSnippet.setViews(codeSnippet.getViews() - 1);
        }
        logger.info("views updated: " + codeSnippet);
        return codeSnippetsRepository.save(codeSnippet);
    }

    public CodeSnippet updateTime(CodeSnippet codeSnippet) {
        if (codeSnippet.getTime() > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");//2022/07/24 20:23:00
            LocalDateTime snippetDate = LocalDateTime.parse(codeSnippet.getDate(), formatter);
            codeSnippet.setTime(
                    codeSnippet.getTime() - ChronoUnit.SECONDS.between(snippetDate, LocalDateTime.now()));
            logger.info("time updated: " + codeSnippet);
            if (codeSnippet.getTime() < 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "blocked");
            }
        }
        return codeSnippetsRepository.save(codeSnippet);
    }
}
