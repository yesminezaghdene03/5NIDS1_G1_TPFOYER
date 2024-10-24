package tn.esprit.tpfoyer.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j  // Simple Logging Facade for Java
public class BlocServiceImpl implements IBlocService {

    BlocRepository blocRepository;

    @Scheduled(fixedRate = 30000) // millisecondes // cron fixedRate
    //@Scheduled(cron="0/15 * * * * *")
    public List<Bloc> retrieveAllBlocs() {
        log.info("Retrieving all blocs from the database...");

        List<Bloc> listB = blocRepository.findAll();
        log.info("Total size of blocs: " + listB.size());

        for (Bloc b : listB) {
            log.debug("Bloc retrieved: " + b);
        }

        return listB;
    }

    @Transactional
    public List<Bloc> retrieveBlocsSelonCapacite(long c) {
        log.info("Retrieving blocs with capacity >= {}", c);

        List<Bloc> listB = blocRepository.findAll();
        List<Bloc> listBselonC = new ArrayList<>();

        for (Bloc b : listB) {
            if (b.getCapaciteBloc() >= c) {
                listBselonC.add(b);
                log.debug("Bloc added based on capacity: " + b);
            }
        }

        log.info("Number of blocs found with capacity >= {}: {}", c, listBselonC.size());
        return listBselonC;
    }

    @Transactional
    public Bloc retrieveBloc(Long blocId) {
        log.info("Retrieving bloc with ID: {}", blocId);
        Bloc bloc = blocRepository.findById(blocId).orElse(null);

        if (bloc == null) {
            log.error("Error occurred while retrieving bloc with ID: {}", blocId);
        } else {
            log.info("Bloc retrieved: {}", bloc);
        }

        return bloc;
    }

    public Bloc addBloc(Bloc c) {
        log.info("Adding new bloc: {}", c);
        return blocRepository.save(c);
    }

    public Bloc modifyBloc(Bloc bloc) {
        log.info("Modifying bloc: {}", bloc);
        return blocRepository.save(bloc);
    }

    public void removeBloc(Long blocId) {
        log.info("Removing bloc with ID: {}", blocId);
        blocRepository.deleteById(blocId);
        log.info("Bloc with ID: {} removed successfully.", blocId);
    }

    public List<Bloc> trouverBlocsSansFoyer() {
        log.info("Finding blocs without foyer...");
        List<Bloc> blocsSansFoyer = blocRepository.findAllByFoyerIsNull();
        log.info("Number of blocs without foyer: {}", blocsSansFoyer.size());
        return blocsSansFoyer;
    }

    public List<Bloc> trouverBlocsParNomEtCap(String nb, long c) {
        log.info("Finding blocs by name: {} and capacity: {}", nb, c);
        List<Bloc> result = blocRepository.findAllByNomBlocAndCapaciteBloc(nb, c);
        log.info("Number of blocs found with name '{}' and capacity '{}': {}", nb, c, result.size());
        return result;
    }
}
