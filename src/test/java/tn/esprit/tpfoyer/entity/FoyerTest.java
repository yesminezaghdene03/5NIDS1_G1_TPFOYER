package tn.esprit.tpfoyer.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FoyerTest {

    private Foyer foyer;
    private Universite universite;
    private Bloc bloc;
    private Set<Bloc> blocs;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer Test");
        foyer.setCapaciteFoyer(100L);

        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Université Test");

        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");

        blocs = new HashSet<>();
        blocs.add(bloc);
    }

    @Test
    void testGettersAndSetters() {
        // Vérifie que les valeurs définies sont bien récupérées par les getters
        assertEquals(1L, foyer.getIdFoyer());
        assertEquals("Foyer Test", foyer.getNomFoyer());
        assertEquals(100L, foyer.getCapaciteFoyer());
    }

    @Test
    void testRelations() {
        // Vérifie la relation avec Universite
        foyer.setUniversite(universite);
        assertEquals(universite, foyer.getUniversite());

        // Vérifie la relation avec Bloc
        foyer.setBlocs(blocs);
        assertEquals(1, foyer.getBlocs().size());
        assertTrue(foyer.getBlocs().contains(bloc));
    }

    @Test
    void testToStringExcludesSensitiveFields() {
        // Vérifie que la méthode toString() n'inclut pas les champs sensibles ou en relation
        String foyerString = foyer.toString();
        assertFalse(foyerString.contains("universite"));
        assertFalse(foyerString.contains("blocs"));
    }

    @Test
    void testJsonSerialization() throws JsonProcessingException {
        // Test de la sérialisation JSON avec Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        foyer.setUniversite(universite);
        foyer.setBlocs(blocs);

        String jsonString = objectMapper.writeValueAsString(foyer);
        assertTrue(jsonString.contains("nomFoyer"));
        assertTrue(jsonString.contains("capaciteFoyer"));

        // Vérifie que les champs ignorés ne sont pas inclus dans le JSON
        assertFalse(jsonString.contains("universite"));
        assertFalse(jsonString.contains("blocs"));
    }

    @Test
    void testJsonDeserialization() throws JsonProcessingException {
        // Test de la désérialisation JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"nomFoyer\":\"Foyer Test\", \"capaciteFoyer\":100}";

        Foyer deserializedFoyer = objectMapper.readValue(json, Foyer.class);
        assertEquals("Foyer Test", deserializedFoyer.getNomFoyer());
        assertEquals(100L, deserializedFoyer.getCapaciteFoyer());
    }
}

