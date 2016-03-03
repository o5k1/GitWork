package com.leaf.swe;

/**
 * Rappresenta i task foglia, senza figli.
 */
public class Subtask {
    String parentID; //Id del task padre
    String id;
    String nome;
   String descrizione;

    public Subtask(String parentID, String id, String nome, String descrizione) {
        this.parentID = parentID;
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getParentID() { return parentID;}

    public String getId() { return id;}

    public String getNome() { return nome;}

    public String getDescrizione() { return descrizione;}

    public void setParentID(String parentID) { this.parentID = parentID;}

    public void setId(String id) { this.id = id;}

    public void setNome(String nome) { this.nome = nome;}

    public void setDescrizione(String descrizione) { this.descrizione = descrizione;}
}
