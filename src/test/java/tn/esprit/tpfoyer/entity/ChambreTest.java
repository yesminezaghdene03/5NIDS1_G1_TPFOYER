package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.Set;

class ChambreTest {
    private Chambre chambre;
    private Bloc bloc;

    @BeforeEach
    void setUp() {
        bloc = new Bloc(); // Assurez-vous que Bloc a un constructeur par défaut ou personnalisez-le
        chambre = new Chambre(101, Chambre.TypeChambre.DOUBLE, bloc);  // Utilisation correcte de TypeChambre
        System.out.println("Initialisation de la chambre : " + chambre);
    }

    @Test
    void testChambreConstructor() {
        Chambre newChambre = new Chambre(103, Chambre.TypeChambre.SIMPLE, bloc);
        System.out.println("Test du constructeur : " + newChambre);
        assertEquals(103, newChambre.getNumeroChambre());
        assertEquals(Chambre.TypeChambre.SIMPLE, newChambre.getTypeC());
        assertEquals(bloc, newChambre.getBloc());
    }

    @Test
    void testChambreFullConstructor() {
        Set<Reservation> reservations = new HashSet<>();
        Chambre fullChambre = new Chambre(1, 104, Chambre.TypeChambre.DOUBLE, bloc, reservations);
        System.out.println("Test du constructeur complet : " + fullChambre);
        assertEquals(1, fullChambre.getIdChambre());
        assertEquals(104, fullChambre.getNumeroChambre());
        assertEquals(Chambre.TypeChambre.DOUBLE, fullChambre.getTypeC());
        assertEquals(bloc, fullChambre.getBloc());
        assertEquals(reservations, fullChambre.getReservations());
    }

    @Test
    void testSetNumeroChambre() {
        chambre.setNumeroChambre(202);
        System.out.println("Test de setNumeroChambre : " + chambre);
        assertEquals(202, chambre.getNumeroChambre());
    }

    @Test
    void testSetTypeC() {
        chambre.setTypeC(Chambre.TypeChambre.SIMPLE);
        System.out.println("Test de setTypeC : " + chambre);
        assertEquals(Chambre.TypeChambre.SIMPLE, chambre.getTypeC());
    }

    @Test
    void testSetBloc() {
        Bloc nouveauBloc = new Bloc();
        chambre.setBloc(nouveauBloc);
        System.out.println("Test de setBloc : " + chambre);
        assertEquals(nouveauBloc, chambre.getBloc());
    }

    @Test
    void testUpdateChambre() {
        Bloc nouveauBloc = new Bloc();
        chambre.updateChambre(1, 102, Chambre.TypeChambre.SIMPLE, nouveauBloc);
        System.out.println("Test de updateChambre : " + chambre);
        assertEquals(1, chambre.getIdChambre());
        assertEquals(102, chambre.getNumeroChambre());
        assertEquals(Chambre.TypeChambre.SIMPLE, chambre.getTypeC());
        assertEquals(nouveauBloc, chambre.getBloc());
    }
}
