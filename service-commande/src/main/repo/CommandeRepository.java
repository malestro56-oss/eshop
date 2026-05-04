package fr.formation.servicecommande.repo;

import fr.formation.servicecommande.model.Commande;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommandeRepository implements PanacheRepositoryBase<Commande, Integer> {

}
