package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Chambre {
    private static final Logger logger = LoggerFactory.getLogger(Chambre.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idChambre;
    private int numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre typeC;

    @ManyToOne
    @JoinColumn(name = "idBloc")  // Updated to match the primary key field in Bloc
    private Bloc bloc;

    @OneToMany(mappedBy = "chambre")
    private Set<Reservation> reservations;

    public Chambre(int numeroChambre, TypeChambre typeC, Bloc bloc) {
        this.numeroChambre = numeroChambre;
        this.typeC = typeC;
        this.bloc = bloc;
        logger.debug("Chambre créée : {}", this);
    }

    public void setNumeroChambre(int numeroChambre) {
        this.numeroChambre = numeroChambre;
        logger.debug("Numéro de chambre mis à jour : {}", numeroChambre);
    }

    public void setTypeC(TypeChambre typeC) {
        this.typeC = typeC;
        logger.debug("Type de chambre mis à jour : {}", typeC);
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
        logger.debug("Bloc mis à jour : {}", bloc);
    }

    public void updateChambre(int idChambre, int numeroChambre, TypeChambre typeC, Bloc bloc) {
        this.idChambre = idChambre;
        this.numeroChambre = numeroChambre;
        this.typeC = typeC;
        this.bloc = bloc;
        logger.debug("Chambre mise à jour : {}", this);
    }

    public enum TypeChambre {
        SIMPLE, DOUBLE
    }
}
