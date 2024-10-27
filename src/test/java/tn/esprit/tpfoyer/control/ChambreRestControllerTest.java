package tn.esprit.tpfoyer.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(ChambreRestController.class)
public class ChambreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IChambreService chambreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetChambres() throws Exception {
        Chambre chambre1 = new Chambre(101, TypeChambre.SIMPLE, new Bloc());
        Chambre chambre2 = new Chambre(102, TypeChambre.DOUBLE, new Bloc());
        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);

        when(chambreService.retrieveAllChambres()).thenReturn(chambres);

        mockMvc.perform(get("/chambre/retrieve-all-chambres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numeroChambre").value(101))
                .andExpect(jsonPath("$[1].numeroChambre").value(102));
    }

    @Test
    public void testRetrieveChambre() throws Exception {
        Chambre chambre = new Chambre(103, TypeChambre.SIMPLE, new Bloc());

        when(chambreService.retrieveChambre(1L)).thenReturn(chambre);

        mockMvc.perform(get("/chambre/retrieve-chambre/{chambre-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroChambre").value(103));
    }

    @Test
    public void testRetrieveChambreNotFound() throws Exception {
        when(chambreService.retrieveChambre(1L)).thenThrow(new RuntimeException("Chambre non trouvée"));

        mockMvc.perform(get("/chambre/retrieve-chambre/{chambre-id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddChambre() throws Exception {
        Chambre chambre = new Chambre(104, TypeChambre.DOUBLE, new Bloc());

        when(chambreService.addChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(post("/chambre/add-chambre")
                        .contentType("application/json")
                        .content("{\"numeroChambre\":104,\"typeC\":\"DOUBLE\",\"bloc\":{}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroChambre").value(104));
    }

    @Test
    public void testRemoveChambre() throws Exception {
        doNothing().when(chambreService).removeChambre(1L);

        mockMvc.perform(delete("/chambre/remove-chambre/{chambre-id}", 1L))
                .andExpect(status().isOk());

        verify(chambreService, times(1)).removeChambre(1L);
    }

    @Test
    public void testModifyChambre() throws Exception {
        Chambre chambre = new Chambre(105, TypeChambre.SIMPLE, new Bloc());

        when(chambreService.modifyChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(put("/chambre/modify-chambre")
                        .contentType("application/json")
                        .content("{\"numeroChambre\":105,\"typeC\":\"SIMPLE\",\"bloc\":{}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroChambre").value(105));
    }

    @Test
    public void testTrouverChSelonTC() throws Exception {
        Chambre chambre = new Chambre(106, TypeChambre.SIMPLE, new Bloc());
        List<Chambre> chambres = Arrays.asList(chambre);

        when(chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE)).thenReturn(chambres);

        mockMvc.perform(get("/chambre/trouver-chambres-selon-typ/{tc}", TypeChambre.SIMPLE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].numeroChambre").value(106));
    }

    @Test
    public void testTrouverChSelonEt() throws Exception {
        Chambre chambre = new Chambre(107, TypeChambre.DOUBLE, new Bloc());

        when(chambreService.trouverchambreSelonEtudiant(123456)).thenReturn(chambre);

        mockMvc.perform(get("/chambre/trouver-chambre-selon-etudiant/{cin}", 123456))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroChambre").value(107));
    }

    @Test
    public void testTrouverchambreSelonEtudiantNotFound() throws Exception {
        when(chambreService.trouverchambreSelonEtudiant(123456)).thenReturn(null);

        mockMvc.perform(get("/chambre/trouver-chambre-selon-etudiant/{cin}", 123456))
                .andExpect(status().isNotFound());
    }
}
