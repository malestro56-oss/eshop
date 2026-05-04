package fr.formation.repo;

import fr.formation.model.Commande;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommandeRepository implements PanacheRepositoryBase<Commande, Integer> {

}
