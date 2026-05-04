package eshop.quarkus.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import eshop.quarkus.client.ProduitClient;
import eshop.quarkus.dto.ProduitDTO;
import eshop.quarkus.exception.ProduitNotFoundException;
import eshop.quarkus.exception.StockInsuffisantException;
import eshop.quarkus.model.Stock;
import eshop.quarkus.repository.StockRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StockService {

    @Inject
    @RestClient
    ProduitClient produitClient;

    @Inject 
    StockRepository stockRepository;

    public ProduitDTO checkProduit(Long id) {
        try {
            return produitClient.getById(id);
        }
        catch (Exception e) {
            throw new RuntimeException("Le produit n'existe pas !");
        }
    }

    //ajout qtt produit au stock (impossible si produit existe pas)
    @Transactional
    public void ajouterStock (Long produitId, int quantite) {
        checkProduit(produitId);

        Stock stock = stockRepository.find("produitId", produitId).firstResult();

        if ( stock == null ) {

            stockRepository.persist(new Stock(produitId,quantite));
            return;

        }

        stock.quantite += quantite;
        
    }


    // dispo d'un produit (impossible si porduit existe pas )
    public boolean isDisponible( Long produitId, int quantite) {
        checkProduit(produitId);

        Stock stock = stockRepository.find("produitId", produitId).firstResult();

        if (stock == null ) {
            return false;
        }

        return stock.quantite >= quantite;
        

    }


    // retrait qtt produit du stock (impossible si produiit existe pas)

    @Transactional
    public void retraitProduit(Long produitId, int quantite) {
         checkProduit(produitId);

        Stock stock = stockRepository.find("produitId", produitId).firstResult();

        if ( stock == null ) {
              throw new ProduitNotFoundException("Stock inexistant pour ce produit");
        }

        if (stock.quantite < quantite) {
        throw new StockInsuffisantException("Stock insuffisant");
    }

        stock.quantite -= quantite;
        

    }

}
