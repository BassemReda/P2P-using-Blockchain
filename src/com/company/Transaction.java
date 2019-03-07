package com.company;

public class Transaction {
    private String source;
    private String destination;
    private String data;

    public Transaction(String source, String destination, String data) {
        this.source = source;
        this.destination = destination;
        this.data = data;
    }

    public String getSource() { return source; }

    public String getDestination() { return destination; }

    public String getData() { return data; }

    public String getTransaction(){
        return source + destination + data;
    }
}
