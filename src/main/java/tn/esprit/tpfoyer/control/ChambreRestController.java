package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations liées aux chambres.
 * <p>
 * Ce contrôleur fournit des endpoints pour récupérer, ajouter, modifier et supprimer des chambres.
 * Il utilise des services pour accéder aux données.
 * </p>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/chambre")
public class ChambreRestController {
    private static final Logger logger = LogManager.getLogger(ChambreRestController.class);
    private final IChambreService chambreService;

    /**
     * Récupère toutes les chambres.
     *
     * @return liste des chambres disponibles.
     * <p>
     * Cette méthode enregistre une demande de récupération de toutes les chambres
     * et renvoie la liste des chambres. Si aucune chambre n'est trouvée,
     * une liste vide est retournée.
     * </p>
     */
    @GetMapping("/retrieve-all-chambres")
    public List<Chambre> getChambres() {
        logger.info("Demande de récupération de toutes les chambres.");
        List<Chambre> listChambres = chambreService.retrieveAllChambres();
        logger.info("Réponse : {} chambres récupérées.", listChambres.size());
        return listChambres;
    }

    /**
     * Récupère une chambre par son identifiant.
     *
     * @param chId identifiant de la chambre à récupérer.
     * @return chambre correspondante à l'identifiant spécifié.
     * <p>
     * En cas d'échec, par exemple si la chambre n'existe pas, une exception
     * peut être lancée pour indiquer que la chambre n'a pas été trouvée.
     * </p>
     */
    @GetMapping("/retrieve-chambre/{chambre-id}")
    public ResponseEntity<Chambre> retrieveChambre(@PathVariable("chambre-id") Long chId) {
        logger.info("Demande de récupération de la chambre avec ID : {}", chId);
        try {
            Chambre chambre = chambreService.retrieveChambre(chId);
            logger.info("Réponse : Chambre récupérée : {}", chambre);
            return ResponseEntity.ok(chambre);
        } catch (RuntimeException e) {
            logger.error("Chambre avec ID : {} non trouvée.", chId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Ajoute une nouvelle chambre.
     *
     * @param c chambre à ajouter.
     * @return chambre ajoutée, y compris son identifiant généré.
     * <p>
     * La méthode enregistre une demande d'ajout d'une chambre et retourne
     * l'objet chambre créé. En cas d'erreur lors de l'ajout, une exception
     * peut être levée.
     * </p>
     */
    @PostMapping("/add-chambre")
    public Chambre addChambre(@RequestBody Chambre c) {
        logger.info("Demande d'ajout d'une chambre : {}", c);
        Chambre chambre = chambreService.addChambre(c);
        logger.info("Réponse : Chambre ajoutée : {}", chambre);
        return chambre;
    }

    /**
     * Supprime une chambre par son identifiant.
     *
     * @param chId identifiant de la chambre à supprimer.
     * <p>
     * Cette méthode enregistre une demande de suppression d'une chambre.
     * Si la chambre n'est pas trouvée, une exception sera lancée.
     * La méthode ne retourne rien.
     * </p>
     */
    @DeleteMapping("/remove-chambre/{chambre-id}")
    public void removeChambre(@PathVariable("chambre-id") Long chId) {
        logger.warn("Demande de suppression de la chambre avec ID : {}", chId);
        chambreService.removeChambre(chId);
        logger.info("Chambre avec ID : {} supprimée.", chId);
    }

    /**
     * Modifie une chambre existante.
     *
     * @param c chambre à modifier, y compris son identifiant.
     * @return chambre modifiée.
     * <p>
     * La méthode enregistre une demande de modification d'une chambre
     * et retourne l'objet chambre mis à jour. Si l'identifiant de la
     * chambre n'est pas trouvé, une exception peut être levée.
     * </p>
     */
    @PutMapping("/modify-chambre")
    public Chambre modifyChambre(@RequestBody Chambre c) {
        logger.info("Demande de modification de la chambre : {}", c);
        Chambre chambre = chambreService.modifyChambre(c);
        logger.info("Réponse : Chambre modifiée : {}", chambre);
        return chambre;
    }

    /**
     * Récupère les chambres selon leur type.
     *
     * @param tc type de chambre à rechercher.
     * @return liste des chambres correspondant au type spécifié.
     * <p>
     * En cas de succès, la méthode renvoie toutes les chambres qui correspondent
     * au type fourni. Si aucune chambre n'est trouvée, une liste vide est retournée.
     * </p>
     */
    @GetMapping("/trouver-chambres-selon-typ/{tc}")
    public List<Chambre> trouverChSelonTC(@PathVariable("tc") TypeChambre tc) {
        logger.info("Demande de recherche de chambres selon le type : {}", tc);
        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(tc);
        logger.info("Réponse : {} chambres trouvées pour le type : {}", chambres.size(), tc);
        return chambres;
    }

    /**
     * Récupère une chambre selon le numéro d'identification de l'étudiant.
     *
     * @param cin numéro d'identification de l'étudiant.
     * @return chambre correspondante à l'étudiant.
     * <p>
     * Cette méthode permet de rechercher une chambre en fonction du
     * numéro d'identification de l'étudiant. Si aucune chambre n'est trouvée,
     * une exception peut être lancée.
     * </p>
     */
    @GetMapping("/trouver-chambre-selon-etudiant/{cin}")
    public ResponseEntity<Chambre> trouverChSelonEt(@PathVariable("cin") long cin) {
        logger.info("Demande de recherche de chambre selon l'étudiant avec CIN : {}", cin);
        try {
            Chambre chambre = chambreService.trouverchambreSelonEtudiant(cin);
            if (chambre == null) {
                throw new RuntimeException("Aucune chambre trouvée pour l'étudiant");
            }
            logger.info("Réponse : Chambre trouvée pour l'étudiant avec CIN : {}", cin);
            return ResponseEntity.ok(chambre);
        } catch (RuntimeException e) {
            logger.error("Aucune chambre trouvée pour l'étudiant avec CIN : {}", cin);
            return ResponseEntity.notFound().build();
        }
    }
}
