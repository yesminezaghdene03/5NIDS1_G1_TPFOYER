package tn.esprit.tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ChambreServiceImplTest {

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @Mock
    private ChambreRepository chambreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllChambres() {
        Chambre chambre1 = new Chambre(101, TypeChambre.SIMPLE, new Bloc());
        Chambre chambre2 = new Chambre(102, TypeChambre.DOUBLE, new Bloc());
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre1, chambre2));

        List<Chambre> chambres = chambreService.retrieveAllChambres();

        assertEquals(2, chambres.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveChambre() {
        Chambre chambre = new Chambre(103, TypeChambre.SIMPLE, new Bloc());
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.retrieveChambre(1L);

        assertNotNull(result);
        assertEquals(103, result.getNumeroChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveChambreNotFound() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            chambreService.retrieveChambre(1L);
        });

        String expectedMessage = "Chambre non trouvée";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testAddChambre() {
        Chambre chambre = new Chambre(104, TypeChambre.DOUBLE, new Bloc());
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.addChambre(chambre);

        assertNotNull(result);
        assertEquals(104, result.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testModifyChambre() {
        Chambre chambre = new Chambre(105, TypeChambre.SIMPLE, new Bloc());
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.modifyChambre(chambre);

        assertNotNull(result);
        assertEquals(105, result.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testRemoveChambre() {
        Long chambreId = 1L;
        doNothing().when(chambreRepository).deleteById(chambreId);

        chambreService.removeChambre(chambreId);

        verify(chambreRepository, times(1)).deleteById(chambreId);
    }

    @Test
    void testRecupererChambresSelonTyp() {
        Chambre chambre1 = new Chambre(106, TypeChambre.SIMPLE, new Bloc());
        Chambre chambre2 = new Chambre(107, TypeChambre.SIMPLE, new Bloc());
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(chambre1, chambre2));

        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        assertEquals(2, chambres.size());
        assertEquals(TypeChambre.SIMPLE, chambres.get(0).getTypeC());
        verify(chambreRepository, times(1)).findAllByTypeC(TypeChambre.SIMPLE);
    }

    @Test
    void testTrouverchambreSelonEtudiant() {
        Chambre chambre = new Chambre(108, TypeChambre.DOUBLE, new Bloc());
        when(chambreRepository.trouverChselonEt(123456L)).thenReturn(chambre);

        Chambre result = chambreService.trouverchambreSelonEtudiant(123456L);

        assertNotNull(result);
        assertEquals(108, result.getNumeroChambre());
        verify(chambreRepository, times(1)).trouverChselonEt(123456L);
    }

    @Test
    void testTrouverchambreSelonEtudiantNotFound() {
        when(chambreRepository.trouverChselonEt(123456L)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            chambreService.trouverchambreSelonEtudiant(123456L);
        });

        String expectedMessage = "Aucune chambre trouvée pour l'étudiant";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(chambreRepository, times(1)).trouverChselonEt(123456L);
    }
}