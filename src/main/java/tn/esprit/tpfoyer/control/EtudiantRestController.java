package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
public class EtudiantRestController {

    private static final Logger logger = LogManager.getLogger(EtudiantRestController.class);

    IEtudiantService etudiantService;

    @GetMapping("/retrieve-all-etudiants")
    public List<Etudiant> getEtudiants() {
        logger.info("Récupération de tous les étudiants");
        List<Etudiant> listEtudiants = etudiantService.retrieveAllEtudiants();
        logger.debug("Liste des étudiants récupérée : {}", listEtudiants);
        return listEtudiants;
    }

    @GetMapping("/retrieve-etudiant-cin/{cin}")
    public Etudiant retrieveEtudiantParCin(@PathVariable("cin") Long cin) {
        logger.info("Récupération de l'étudiant avec CIN : {}", cin);
        Etudiant etudiant = etudiantService.recupererEtudiantParCin(cin);
        if (etudiant != null) {
            logger.debug("Étudiant trouvé : {}", etudiant);
        } else {
            logger.warn("Aucun étudiant trouvé avec CIN : {}", cin);
        }
        return etudiant;
    }

    @GetMapping("/retrieve-etudiant/{etudiant-id}")
    public Etudiant retrieveEtudiant(@PathVariable("etudiant-id") Long chId) {
        logger.info("Récupération de l'étudiant avec ID : {}", chId);
        Etudiant etudiant = etudiantService.retrieveEtudiant(chId);
        if (etudiant != null) {
            logger.debug("Étudiant trouvé : {}", etudiant);
        } else {
            logger.warn("Aucun étudiant trouvé avec ID : {}", chId);
        }
        return etudiant;
    }

    // http://localhost:8089/tpfoyer/etudiant/add-etudiant
    @PostMapping("/add-etudiant")
    public Etudiant addEtudiant(@RequestBody Etudiant c) {
        logger.info("Ajout d'un nouvel étudiant : {}", c);
        Etudiant etudiant = etudiantService.addEtudiant(c);
        logger.debug("Étudiant ajouté avec succès : {}", etudiant);
        return etudiant;
    }

    // http://localhost:8089/tpfoyer/etudiant/remove-etudiant/{etudiant-id}
    @DeleteMapping("/remove-etudiant/{etudiant-id}")
    public void removeEtudiant(@PathVariable("etudiant-id") Long chId) {
        logger.info("Suppression de l'étudiant avec ID : {}", chId);
        try {
            etudiantService.removeEtudiant(chId);
            logger.debug("Étudiant avec ID {} supprimé avec succès", chId);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'étudiant avec ID : {}", chId, e);
        }
    }

    // http://localhost:8089/tpfoyer/etudiant/modify-etudiant
    @PutMapping("/modify-etudiant")
    public Etudiant modifyEtudiant(@RequestBody Etudiant c) {
        logger.info("Modification de l'étudiant : {}", c);
        Etudiant etudiant = etudiantService.modifyEtudiant(c);
        logger.debug("Étudiant modifié avec succès : {}", etudiant);
        return etudiant;
    }
}
