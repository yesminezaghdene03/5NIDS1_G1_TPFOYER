package tn.esprit.tpfoyer.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.List;

@RestController
@RequestMapping("/chambre")
public class ChambreRestController {

    private static final Logger logger = LoggerFactory.getLogger(ChambreRestController.class);

    @Autowired
    private IChambreService chambreService;

    @GetMapping("/retrieve-chambre/{chambre-id}")
    public ResponseEntity<Chambre> retrieveChambre(@PathVariable("chambre-id") Long chambreId) {
        logger.info("Request to retrieve chambre with ID: {}", chambreId);
        try {
            Chambre chambre = chambreService.retrieveChambre(chambreId);
            logger.info("Chambre retrieved successfully: {}", chambre);
            return ResponseEntity.ok(chambre);
        } catch (RuntimeException e) {
            logger.error("Chambre with ID: {} not found. Error: {}", chambreId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/retrieve-all-chambres")
    public ResponseEntity<List<Chambre>> retrieveAllChambres() {
        logger.info("Request to retrieve all chambres");
        List<Chambre> chambres = chambreService.retrieveAllChambres();
        logger.info("Retrieved {} chambres", chambres.size());
        return ResponseEntity.ok(chambres);
    }

    @PostMapping("/add-chambre")
    public ResponseEntity<Chambre> addChambre(@RequestBody Chambre chambre) {
        logger.info("Request to add chambre: {}", chambre);
        Chambre newChambre = chambreService.addChambre(chambre);
        logger.info("Chambre added successfully: {}", newChambre);
        return ResponseEntity.ok(newChambre);
    }

    @DeleteMapping("/remove-chambre/{chambre-id}")
    public ResponseEntity<Void> removeChambre(@PathVariable("chambre-id") Long chambreId) {
        logger.warn("Request to remove chambre with ID: {}", chambreId);
        chambreService.removeChambre(chambreId);
        logger.info("Chambre with ID: {} removed successfully", chambreId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/modify-chambre")
    public ResponseEntity<Chambre> modifyChambre(@RequestBody Chambre chambre) {
        logger.info("Request to modify chambre: {}", chambre);
        Chambre updatedChambre = chambreService.modifyChambre(chambre);
        logger.info("Chambre modified successfully: {}", updatedChambre);
        return ResponseEntity.ok(updatedChambre);
    }

    @GetMapping("/trouver-chambres-selon-typ/{tc}")
    public ResponseEntity<List<Chambre>> trouverChambresSelonTyp(@PathVariable("tc") Chambre.TypeChambre typeChambre) {
        logger.info("Request to find chambres by type: {}", typeChambre);
        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(typeChambre);
        logger.info("Found {} chambres of type {}", chambres.size(), typeChambre);
        return ResponseEntity.ok(chambres);
    }

    @GetMapping("/trouver-chambre-selon-etudiant/{cin}")
    public ResponseEntity<Chambre> trouverChambreSelonEtudiant(@PathVariable("cin") int cin) {
        logger.info("Request to find chambre for student with CIN: {}", cin);
        Chambre chambre = chambreService.trouverchambreSelonEtudiant(cin);
        if (chambre != null) {
            logger.info("Chambre found for student with CIN: {}", cin);
            return ResponseEntity.ok(chambre);
        } else {
            logger.error("No chambre found for student with CIN: {}", cin);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}