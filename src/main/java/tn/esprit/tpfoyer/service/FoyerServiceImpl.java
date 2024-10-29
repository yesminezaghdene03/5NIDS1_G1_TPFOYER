package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements IFoyerService {
    private static final Logger logger = LogManager.getLogger(FoyerServiceImpl.class);

    FoyerRepository foyerRepository;

    public List<Foyer> retrieveAllFoyers() {
        logger.info("Attempting to retrieve all foyers");

        try {
            List<Foyer> foyers = foyerRepository.findAll();
            logger.info("Successfully retrieved {} foyers", foyers.size());
            return foyers;
        } catch (Exception e) {
            logger.error("Failed to retrieve foyers due to an error: ", e);
            return null;
        }
    }

    public Foyer retrieveFoyer(Long foyerId) {
        logger.info("Attempting to retrieve foyer with ID: {}", foyerId);

        Optional<Foyer> foyer = foyerRepository.findById(foyerId);
        if (foyer.isPresent()) {
            logger.info("Foyer found: {}", foyer.get());
            return foyer.get();
        } else {
            logger.warn("Foyer with ID {} not found", foyerId);
            return null;
        }
    }

    public Foyer addFoyer(Foyer f) {
        logger.info("Attempting to add new foyer: {}", f);

        try {
            Foyer savedFoyer = foyerRepository.save(f);
            logger.info("Successfully added foyer with ID: {}", savedFoyer.getIdFoyer());
            return savedFoyer;
        } catch (Exception e) {
            logger.error("Failed to add new foyer due to an error: ", e);
            return null;
        }
    }

    public Foyer modifyFoyer(Foyer foyer) {
        logger.info("Attempting to modify foyer with ID: {}", foyer.getIdFoyer());

        if (!foyerRepository.existsById(foyer.getIdFoyer())) {
            logger.warn("Foyer with ID {} does not exist, cannot modify", foyer.getIdFoyer());
            return null;
        }

        try {
            Foyer updatedFoyer = foyerRepository.save(foyer);
            logger.info("Successfully updated foyer: {}", updatedFoyer);
            return updatedFoyer;
        } catch (Exception e) {
            logger.error("Failed to update foyer with ID {} due to an error: ", foyer.getIdFoyer(), e);
            return null;
        }
    }

    public void removeFoyer(Long foyerId) {
        logger.info("Attempting to remove foyer with ID: {}", foyerId);

        if (!foyerRepository.existsById(foyerId)) {
            logger.warn("Foyer with ID {} does not exist, cannot remove", foyerId);
            return;
        }

        try {
            foyerRepository.deleteById(foyerId);
            logger.info("Successfully removed foyer with ID: {}", foyerId);
        } catch (Exception e) {
            logger.error("Failed to remove foyer with ID {} due to an error: ", foyerId, e);
        }
    }
}
