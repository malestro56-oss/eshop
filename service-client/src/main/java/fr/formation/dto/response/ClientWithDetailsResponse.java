package fr.formation.dto.response;

import java.time.LocalDate;

import fr.formation.model.Client;

public class ClientWithDetailsResponse {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;

    
    public ClientWithDetailsResponse() {
    }
    public ClientWithDetailsResponse(String id, String nom, String prenom, String email, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

     public static ClientWithDetailsResponse convert(Client client) {
        return new ClientWithDetailsResponse(client.getId(), client.getNom(), client.getPrenom(), client.getEmail(), client.getDateNaissance());
    }
    

}
