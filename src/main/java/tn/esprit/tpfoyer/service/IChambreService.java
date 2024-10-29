package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Chambre.TypeChambre;

import java.util.List;

public interface IChambreService {
    List<Chambre> retrieveAllChambres();
    Chambre retrieveChambre(Long id);
    Chambre addChambre(Chambre chambre);
    void removeChambre(Long id);
    Chambre modifyChambre(Chambre chambre);
    List<Chambre> recupererChambresSelonTyp(TypeChambre typeChambre);
    Chambre trouverchambreSelonEtudiant(long cin);
}
