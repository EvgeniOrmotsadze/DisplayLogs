package com.service.display.model;

import java.time.LocalDateTime;


public class Result {

    private LocalDateTime localDateTime;
    private Severity severity;
    private String message;

    public Result(){}


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /*
     *  this override because when read log file if already has read will not repeat in list
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Result) {
            Result res = (Result) obj;
            if (this.getLocalDateTime().equals(res.getLocalDateTime()) && this.getSeverity().equals(res.getSeverity()) &&
                    this.getMessage().equals(res.getMessage()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Result{" +
                ", localDateTime=" + localDateTime +
                ", severity='" + severity + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
