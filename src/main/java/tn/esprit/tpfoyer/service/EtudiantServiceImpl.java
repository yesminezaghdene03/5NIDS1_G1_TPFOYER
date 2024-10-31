package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {

    private static final Logger logger = LogManager.getLogger(EtudiantServiceImpl.class);

    EtudiantRepository etudiantRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        logger.debug("Appel de retrieveAllEtudiants");
        List<Etudiant> etudiants = etudiantRepository.findAll();
        logger.info("Récupération de tous les étudiants : {}", etudiants);
        return etudiants;
    }

    @Override
    public Etudiant retrieveEtudiant(Long etudiantId) {
        logger.debug("Appel de retrieveEtudiant avec ID : {}", etudiantId);
        Optional<Etudiant> etudiant = etudiantRepository.findById(etudiantId);
        if (etudiant.isPresent()) {
            logger.info("Étudiant trouvé : {}", etudiant.get());
            return etudiant.get();
        } else {
            logger.warn("Aucun étudiant trouvé avec ID : {}", etudiantId);
            return null;
        }
    }

    @Override
    public Etudiant addEtudiant(Etudiant c) {
        logger.info("Ajout de l'étudiant : {}", c);
        Etudiant etudiant = etudiantRepository.save(c);
        logger.debug("Étudiant ajouté avec succès : {}", etudiant);
        return etudiant;
    }

    @Override
    public Etudiant modifyEtudiant(Etudiant c) {
        logger.info("Modification de l'étudiant : {}", c);
        Etudiant etudiant = etudiantRepository.save(c);
        logger.debug("Étudiant modifié avec succès : {}", etudiant);
        return etudiant;
    }

    @Override
    public void removeEtudiant(Long etudiantId) {
        logger.info("Suppression de l'étudiant avec ID : {}", etudiantId);
        try {
            etudiantRepository.deleteById(etudiantId);
            logger.debug("Étudiant avec ID {} supprimé avec succès", etudiantId);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'étudiant avec ID : {}", etudiantId, e);
        }
    }

    @Override
    public Etudiant recupererEtudiantParCin(long cin) {
        logger.debug("Appel de recupererEtudiantParCin avec CIN : {}", cin);
        Etudiant etudiant = etudiantRepository.findEtudiantByCinEtudiant(cin);
        if (etudiant != null) {
            logger.info("Étudiant trouvé avec CIN {} : {}", cin, etudiant);
        } else {
            logger.warn("Aucun étudiant trouvé avec CIN : {}", cin);
        }
        return etudiant;
    }
}
