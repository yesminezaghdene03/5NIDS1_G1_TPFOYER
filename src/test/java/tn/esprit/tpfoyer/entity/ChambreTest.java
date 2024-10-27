package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChambreTest {
    private Chambre chambre;
    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        bloc = new Bloc(); // Assurez-vous que Bloc a un constructeur par défaut ou personnalisez-le
        chambre = new Chambre(101, TypeChambre.DOUBLE, bloc);  // Utilisation correcte de TypeChambre
    }

    @Test
    public void testOnPrePersist() {
        // Affichez les informations de la chambre avant la persistance
        System.out.println("Avant la persistance : " + chambre);
        // Vérifiez les attributs avant la persistance
        assertEquals(101, chambre.getNumeroChambre());
        assertEquals(TypeChambre.DOUBLE, chambre.getTypeC());
        // Message de réussite
        System.out.println("Test de onPrePersist effectué avec succès avec les informations : " + chambre);
    }

    @Test
    public void testOnPreUpdate() {
        Bloc nouveauBloc = new Bloc(); // Personnalisez ce bloc si nécessaire
        chambre.updateChambre(1, 102, TypeChambre.SIMPLE, nouveauBloc);  // Utilisation correcte de TypeChambre
        System.out.println("Après la mise à jour : " + chambre);
        assertEquals(1, chambre.getIdChambre()); // Vérifie que l'ID a changé
        assertEquals(102, chambre.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, chambre.getTypeC());
        assertEquals(nouveauBloc, chambre.getBloc()); // Vérifie que le bloc a changé
        System.out.println("Test de onPreUpdate effectué avec succès avec les informations : " + chambre);
    }
}
