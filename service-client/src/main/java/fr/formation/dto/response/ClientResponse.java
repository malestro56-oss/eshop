package fr.formation.dto.response;

import fr.formation.model.Client;

public class ClientResponse {
    private String id;
    private String nom;
    private String prenom;

    
    
    public ClientResponse() {
    }
    public ClientResponse(String id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public static ClientResponse convert(Client client) {
        return new ClientResponse(client.getId(), client.getNom(), client.getPrenom());
    }

}
