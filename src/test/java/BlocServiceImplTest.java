import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.Arrays;
import java.util.List;

public class BlocServiceImplTest {

    @InjectMocks
    private BlocServiceImpl blocService;

    @Mock
    private BlocRepository blocRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllBlocs() {
        // Arrange
        Bloc bloc1 = new Bloc(1L, "Bloc 1", 10L); // Correction ici
        Bloc bloc2 = new Bloc(2L, "Bloc 2", 20L); // Correction ici
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Assert
        assertEquals(2, result.size());
        verify(blocRepository).findAll();
    }

    @Test
    public void testRetrieveBlocsSelonCapacite() {
        // Arrange
        Bloc bloc1 = new Bloc(1L, "Bloc 1", 10L); // Correction ici
        Bloc bloc2 = new Bloc(2L, "Bloc 2", 30L); // Correction ici
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(20L); // Correction ici

        // Assert
        assertEquals(1, result.size());
        assertEquals(bloc2, result.get(0));
    }

    @Test
    public void testRetrieveBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, "Bloc 1", 10L); // Correction ici
        when(blocRepository.findById(1L)).thenReturn(java.util.Optional.of(bloc));

        // Act
        Bloc result = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
    }

    // Ajoute d'autres tests pour les méthodes restantes
}

