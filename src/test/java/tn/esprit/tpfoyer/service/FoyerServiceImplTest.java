package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer Test");
    }

    @Test
    void testRetrieveAllFoyers() {
        List<Foyer> foyers = new ArrayList<>();
        foyers.add(foyer);

        when(foyerRepository.findAll()).thenReturn(foyers);

        List<Foyer> result = foyerService.retrieveAllFoyers();
        assertEquals(1, result.size());
        assertEquals(foyer, result.get(0));

        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer_Found() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.retrieveFoyer(1L);
        assertNotNull(result);
        assertEquals(foyer, result);

        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveFoyer_NotFound() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        Foyer result = foyerService.retrieveFoyer(1L);
        assertNull(result);

        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddFoyer() {
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerService.addFoyer(foyer);
        assertNotNull(result);
        assertEquals(foyer, result);

        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testModifyFoyer() {
        when(foyerRepository.existsById(foyer.getIdFoyer())).thenReturn(true);
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerService.modifyFoyer(foyer);
        assertNotNull(result);
        assertEquals(foyer, result);

        verify(foyerRepository, times(1)).existsById(foyer.getIdFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testModifyFoyer_NotFound() {
        when(foyerRepository.existsById(foyer.getIdFoyer())).thenReturn(false);

        Foyer result = foyerService.modifyFoyer(foyer);
        assertNull(result);

        verify(foyerRepository, times(1)).existsById(foyer.getIdFoyer());
        verify(foyerRepository, times(0)).save(foyer);
    }

    @Test
    void testRemoveFoyer_Found() {
        when(foyerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(foyerRepository).deleteById(1L);

        foyerService.removeFoyer(1L);

        verify(foyerRepository, times(1)).existsById(1L);
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRemoveFoyer_NotFound() {
        when(foyerRepository.existsById(1L)).thenReturn(false);

        foyerService.removeFoyer(1L);

        verify(foyerRepository, times(1)).existsById(1L);
        verify(foyerRepository, times(0)).deleteById(1L);
    }
}
