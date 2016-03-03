package com.leaf.swe;

/**
 * Rappresenta una singola tasklist.
 */
public class Tasklist {
    String id;
    String nome;
    String description;
    boolean isComplete;

    public Tasklist(String id, String nome, String description, boolean isComplete) {
        this.id = id;
        this.nome = nome;
        this.description = description;
        this.isComplete = isComplete;
    }

    public String getId() { return id;}

    public String getNome() {
        return nome;
    }

    public String getDescription() {
        return description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setId(String id) { this.id = id;}
}
