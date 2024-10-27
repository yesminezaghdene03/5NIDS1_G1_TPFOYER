package tn.esprit.tpfoyer.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Chambre {
    private static final Logger logger = LoggerFactory.getLogger(Chambre.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idChambre;
    private int numeroChambre;
    private TypeChambre typeC;
    private Bloc bloc;

    public Chambre(int numeroChambre, TypeChambre typeC, Bloc bloc) {
        this.numeroChambre = numeroChambre;
        this.typeC = typeC;
        this.bloc = bloc;
        logger.debug("Chambre créée : {}", this); // Message de journalisation pour la création de l'entité
    }

    public void setNumeroChambre(int numeroChambre) {
        this.numeroChambre = numeroChambre;
        logger.debug("Numéro de chambre mis à jour : {}", numeroChambre); // Message de journalisation pour la mise à jour
    }

    public void setTypeC(TypeChambre typeC) {
        this.typeC = typeC;
        logger.debug("Type de chambre mis à jour : {}", typeC); // Message de journalisation pour la mise à jour
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
        logger.debug("Bloc mis à jour : {}", bloc); // Message de journalisation pour la mise à jour
    }

    public void updateChambre(int idChambre, int numeroChambre, TypeChambre typeC, Bloc bloc) {
        this.idChambre = idChambre;
        this.numeroChambre = numeroChambre;
        this.typeC = typeC;
        this.bloc = bloc;
        logger.debug("Chambre mise à jour : {}", this); // Message de journalisation pour la mise à jour de tous les attributs
    }
}
