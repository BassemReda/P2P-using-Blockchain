package com.company;

import java.util.Vector;

public class Blockchain {
    private Vector<Block> blockchain;
    public Blockchain(){
        blockchain = new Vector<>();

    }
    public void addBlock(String data, Transaction transaction){
        String prev_hash = "0";
        if(!blockchain.isEmpty())
            prev_hash = blockchain.get(blockchain.size() - 1).getHash();
        blockchain.add(new Block(transaction, prev_hash));
    }
    public Transaction getBlockData(int index){
        Block required = blockchain.get(index);
        String hash = required.calculateHash();
        if ( index < blockchain.size()-1 && hash.equals(blockchain.get(index+1).getPrev_hash()) )
            return blockchain.get(index).getTransaction();
        else
            return new Transaction("","","");
    }
}
