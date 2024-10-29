package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Foyer;

import java.util.List;

public interface IFoyerService {

    List<Foyer> retrieveAllFoyers();
    Foyer retrieveFoyer(Long foyerId);
    Foyer addFoyer(Foyer f);
    void removeFoyer(Long foyerId);
    Foyer modifyFoyer(Foyer foyer);

    // Here we will add later methods calling keywords and methods calling JPQL
}
