package io.sanberg.hyperskillcodesharingplatform.repository;

import org.springframework.data.repository.CrudRepository;
import io.sanberg.hyperskillcodesharingplatform.entities.CodeSnippet;

import java.util.ArrayList;
import java.util.Optional;

public interface CodeSnippetsRepository extends CrudRepository<CodeSnippet, Long> {
    ArrayList<CodeSnippet> findTop10ByIsTimeLimitedFalseAndIsViewsLimitedFalseOrderByDateDesc();

    Optional<CodeSnippet> findByUiid(String uiid);

    ArrayList<CodeSnippet> findTop10ByOrderByDateDesc();


}
