package tn.esprit.tpfoyer.control;

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

    @Autowired
    private IChambreService chambreService;

    @GetMapping("/retrieve-chambre/{chambre-id}")
    public ResponseEntity<Chambre> retrieveChambre(@PathVariable("chambre-id") Long chambreId) {
        try {
            Chambre chambre = chambreService.retrieveChambre(chambreId);
            return ResponseEntity.ok(chambre);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/retrieve-all-chambres")
    public ResponseEntity<List<Chambre>> retrieveAllChambres() {
        List<Chambre> chambres = chambreService.retrieveAllChambres();
        return ResponseEntity.ok(chambres);
    }

    @PostMapping("/add-chambre")
    public ResponseEntity<Chambre> addChambre(@RequestBody Chambre chambre) {
        Chambre newChambre = chambreService.addChambre(chambre);
        return ResponseEntity.ok(newChambre);
    }

    @DeleteMapping("/remove-chambre/{chambre-id}")
    public ResponseEntity<Void> removeChambre(@PathVariable("chambre-id") Long chambreId) {
        chambreService.removeChambre(chambreId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/modify-chambre")
    public ResponseEntity<Chambre> modifyChambre(@RequestBody Chambre chambre) {
        Chambre updatedChambre = chambreService.modifyChambre(chambre);
        return ResponseEntity.ok(updatedChambre);
    }

    @GetMapping("/trouver-chambres-selon-typ/{tc}")
    public ResponseEntity<List<Chambre>> trouverChambresSelonTyp(@PathVariable("tc") Chambre.TypeChambre typeChambre) {
        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(typeChambre);
        return ResponseEntity.ok(chambres);
    }

    @GetMapping("/trouver-chambre-selon-etudiant/{cin}")
    public ResponseEntity<Chambre> trouverChambreSelonEtudiant(@PathVariable("cin") int cin) {
        Chambre chambre = chambreService.trouverchambreSelonEtudiant(cin);
        if (chambre != null) {
            return ResponseEntity.ok(chambre);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
