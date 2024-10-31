

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Préparation des données
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant(1L, "Nom1", "Prenom1", 123456L, null, null));
        etudiants.add(new Etudiant(2L, "Nom2", "Prenom2", 789012L, null, null));

        // Configuration du mock
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // Exécution du test
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Vérification des résultats
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant() {
        // Préparation des données
        Etudiant etudiant = new Etudiant(1L, "Nom1", "Prenom1", 123456L, null, null);

        // Configuration du mock
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Exécution du test
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals("Nom1", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testAddEtudiant() {
        // Préparation des données
        Etudiant etudiant = new Etudiant(1L, "Nom1", "Prenom1", 123456L, null, null);

        // Configuration du mock
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Exécution du test
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals("Nom1", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testModifyEtudiant() {
        // Préparation des données
        Etudiant etudiant = new Etudiant(1L, "Nom1", "Prenom1", 123456L, null, null);

        // Configuration du mock
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Exécution du test
        Etudiant result = etudiantService.modifyEtudiant(etudiant);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals("Nom1", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRemoveEtudiant() {
        // Exécution du test
        etudiantService.removeEtudiant(1L);

        // Vérification que la méthode deleteById a été appelée
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRecupererEtudiantParCin() {
        // Préparation des données
        Etudiant etudiant = new Etudiant(1L, "Nom1", "Prenom1", 123456L, null, null);

        // Configuration du mock
        when(etudiantRepository.findEtudiantByCinEtudiant(123456L)).thenReturn(etudiant);

        // Exécution du test
        Etudiant result = etudiantService.recupererEtudiantParCin(123456L);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals(123456L, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(123456L);
    }
}
