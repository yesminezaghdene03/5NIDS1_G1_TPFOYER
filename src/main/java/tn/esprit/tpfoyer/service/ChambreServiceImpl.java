package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Chambre.TypeChambre;
import tn.esprit.tpfoyer.exception.ChambreNotFoundException;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ChambreServiceImpl implements IChambreService {
    private final ChambreRepository chambreRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        log.info("In Method retrieveAllChambres");
        List<Chambre> listC = chambreRepository.findAll();
        log.info("Out of retrieveAllChambres: {} chambres récupérées.", listC.size());
        return listC;
    }

    @Override
    public Chambre retrieveChambre(Long chambreId) {
        log.info("In Method retrieveChambre with ID: {}", chambreId);
        Optional<Chambre> optionalChambre = chambreRepository.findById(chambreId);
        if (optionalChambre.isPresent()) {
            Chambre chambre = optionalChambre.get();
            log.info("Chambre trouvée: {}", chambre);
            return chambre;
        } else {
            log.error("Chambre avec ID: {} non trouvée.", chambreId);
            throw new ChambreNotFoundException("Chambre non trouvée avec ID: " + chambreId);
        }
    }

    @Override
    public Chambre addChambre(Chambre c) {
        log.debug("In Method addChambre: {}", c);
        Chambre chambre = chambreRepository.save(c);
        log.debug("Chambre ajoutée: {}", chambre);
        return chambre;
    }

    @Override
    public Chambre modifyChambre(Chambre c) {
        log.debug("In Method modifyChambre: {}", c);
        Chambre chambre = chambreRepository.save(c);
        log.debug("Chambre modifiée: {}", chambre);
        return chambre;
    }

    @Override
    public void removeChambre(Long chambreId) {
        log.warn("In Method removeChambre with ID: {}", chambreId);
        try {
            chambreRepository.deleteById(chambreId);
            log.info("Chambre avec ID: {} supprimée.", chambreId);
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la chambre avec ID: {}. Message: {}", chambreId, e.getMessage());
        }
    }

    @Override
    public List<Chambre> recupererChambresSelonTyp(TypeChambre tc) {
        log.info("In Method recupererChambresSelonTyp with Type: {}", tc);
        List<Chambre> chambres = chambreRepository.findAllByTypeC(tc);
        log.info("Nombre de chambres trouvées selon le type {}: {}", tc, chambres.size());
        return chambres;
    }

    @Override
    public Chambre trouverchambreSelonEtudiant(long cin) {
        log.info("In Method trouverchambreSelonEtudiant with CIN: {}", cin);
        Chambre chambre = chambreRepository.trouverChselonEt(cin);
        if (chambre != null) {
            log.info("Chambre trouvée pour l'étudiant avec CIN: {}", cin);
            return chambre;
        } else {
            log.error("Aucune chambre trouvée pour l'étudiant avec CIN: {}", cin);
            throw new ChambreNotFoundException("Aucune chambre trouvée pour l'étudiant avec CIN: " + cin);
        }
    }
}