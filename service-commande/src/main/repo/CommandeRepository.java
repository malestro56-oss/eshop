package fr.formation.servicecommande.repo;

import fr.formation.servicecommande.model.Commande;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommandeRepository implements PanacheRepositoryBase<Commande, Integer> {
        
    public List<Integer> findCommandeByClientId(String clientId) {
        return this.find("select id from commande c where c.clientId = ?1", clientId)
        .project(Integer.class).list();
    }
    
}
