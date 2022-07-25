package io.sanberg.hyperskillcodesharingplatform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
public class CodeSnippet {
    @Lob
    @Column
    private String code;
    private LocalDateTime date;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uiid", columnDefinition = "VARCHAR(255)")
    @JsonIgnore
    private String uiid;
    private int views;
    private long time;

    @JsonIgnore
    private boolean isViewsLimited;

    @JsonIgnore
    private boolean isTimeLimited;

    public CodeSnippet() {
        this.date = LocalDateTime.now();
    }

    public CodeSnippet(String code, LocalDateTime date) {
        this.code = code;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUiid() {
        return uiid;
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    @JsonIgnore
    public boolean isViewsLimited() {
        return isViewsLimited;
    }

    public void setIsViewsLimited(boolean viewsLimited) {
        isViewsLimited = viewsLimited;
    }

    @JsonIgnore
    public boolean isTimeLimited() {
        return isTimeLimited;
    }

    @JsonIgnore
    public void setIsTimeLimited(boolean timeLimited) {
        isTimeLimited = timeLimited;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "code='" + code + '\'' +
                ", date=" + date +
                ", uiid='" + uiid + '\'' +
                ", views=" + views +
                ", time=" + time +
                ", isViewsLimited=" + isViewsLimited +
                ", isTimeLimited=" + isTimeLimited +
                '}';
    }
}
