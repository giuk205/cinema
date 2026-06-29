package it.tcweb.cinema.exceptions;

public class ErrorResponse {
    private int status;
    private String messaggio;

    public ErrorResponse(int status, String messaggio){
        this.status = status;
        this.messaggio = messaggio;
    }

    public int getStatus() { return status; }
    public String getMessaggio() { return messaggio; }
    }