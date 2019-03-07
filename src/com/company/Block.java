package com.company;

import java.util.Date;

public class Block {
    public static int difficulty = 1;
    private int nonce;
    private long timestamp;
    private Transaction transaction;
    private String hash;
    private String prev_hash;

    public Block(Transaction transaction, String prev_hash){
        timestamp = new Date().getTime();
        this.transaction = transaction;
        this.prev_hash = prev_hash;
        this.hash = getValidHash();
        nonce = 0;
    }

    public String getHash() { return hash; }

    public String getPrev_hash() { return prev_hash; }

    public String calculateHash(){
        return Hash.getSHA256(prev_hash + Long.toString(timestamp) + transaction.getTransaction() + Integer.toString(nonce));
    }

    private boolean isHashValid(String tempHash) { // hash starts with a number of 0's that equal difficulty
        for (int i = 0; i < difficulty; ++i) {
            if (tempHash.charAt(i) != '0')
                return false;
        }
        return true;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public String getValidHash() { // proof of work
        String tempHash = calculateHash();
        while (!isHashValid(tempHash)) {
            nonce++;
            tempHash = calculateHash();
        }
        return tempHash;
    }

}
