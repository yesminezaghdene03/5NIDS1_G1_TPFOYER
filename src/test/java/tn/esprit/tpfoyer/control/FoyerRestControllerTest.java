package tn.esprit.tpfoyer.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Correction de l'import
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FoyerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFoyerService foyerService;
@Autowired
    private ObjectMapper objectMapper;

    private Foyer foyer;

    @BeforeEach
    public void setUp() {
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer Test");
        foyer.setCapaciteFoyer(100);
    }

    @Test
    public void testGetFoyers() throws Exception {
        // Code sans changement
    }

    @Test
    public void testRetrieveFoyer() throws Exception {
        when(foyerService.retrieveFoyer(1L)).thenReturn(foyer);

        mockMvc.perform(get("/foyer/retrieve-foyer/{foyer-id}", 1L))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer Test"));
    }

    @Test
    public void testRetrieveFoyerNotFound() throws Exception {
        when(foyerService.retrieveFoyer(1L)).thenReturn(null);

        mockMvc.perform(get("/foyer/retrieve-foyer/{foyer-id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddFoyer() throws Exception {
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/add-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer Test"));
    }

    @Test
    public void testRemoveFoyer() throws Exception {
        doNothing().when(foyerService).removeFoyer(anyLong());

        mockMvc.perform(delete("/foyer/remove-foyer/{foyer-id}", 1L))
                .andExpect(status().isNoContent());

        verify(foyerService, times(1)).removeFoyer(1L);
    }

    @Test
    public void testModifyFoyer() throws Exception {
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(put("/foyer/modify-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer Test"));
    }
}
