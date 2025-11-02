package fr.matawan.football.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.matawan.football.management.payload.EquipePayload;
import fr.matawan.football.management.request.EquipeRequest;
import fr.matawan.football.management.service.EquipeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipeController.class)
class EquipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipeService equipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetEquipes() throws Exception {
        EquipePayload equipe = new EquipePayload();
        equipe.setNom("PSG");
        Page<EquipePayload> page = new PageImpl<>(Collections.singletonList(equipe), PageRequest.of(0, 10), 1);

        Mockito.when(equipeService.getAllEquipes(Mockito.any())).thenReturn(page);

        mockMvc.perform(get("/equipe")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "nom")
                        .param("sens", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nom").value("PSG"));
    }

    @Test
    void testCreateEquipe_success() throws Exception {
        EquipePayload payload = new EquipePayload();
        payload.setNom("TEST NOUVELLE EQUIPE");
        payload.setAcronym("TNE");
        payload.setBudget(10000L);

        EquipeRequest request = new EquipeRequest();
        request.setEquipePayload(payload);

        // Simuler le succès du service
        Mockito.doNothing().when(equipeService).createEquipe(Mockito.any(EquipePayload.class));

        mockMvc.perform(post("/equipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "L'équipe \"TEST NOUVELLE EQUIPE\" a été correctement créée.")));
    }

    @Test
    void testCreateEquipe_error() throws Exception {
        EquipePayload payload = new EquipePayload();
        payload.setNom("TEST FAIL EQUIPE");
        payload.setAcronym("FAIL");
        payload.setBudget(0L);

        EquipeRequest request = new EquipeRequest();
        request.setEquipePayload(payload);

        // Lever une exception lors de l'appel du service
       Mockito.doThrow(new RuntimeException("Erreur simulée"))
                .when(equipeService).createEquipe(Mockito.any(EquipePayload.class));

        mockMvc.perform(post("/equipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Erreur lors de la création de l'équipe: Erreur simulée")));
    }

    @Test
    void testUpdateEquipe_success() throws Exception {
        EquipePayload payload = new EquipePayload();
        payload.setNom("OGC Nice");
        payload.setAcronym("OGCN");
        payload.setBudget(30000L);

        EquipeRequest request = new EquipeRequest();
        request.setEquipePayload(payload);

        // Simuler le succès du service
        Mockito.doNothing().when(equipeService).updateEquipe(Mockito.any(EquipePayload.class));

        mockMvc.perform(patch("/equipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "L'équipe \"OGC Nice\" a été correctement mise à jour.")));
    }

    @Test
    void testUpdateEquipe_error() throws Exception {
        EquipePayload payload = new EquipePayload();
        payload.setNom("OGC Nice");
        payload.setAcronym("OGCN");
        payload.setBudget(30000L);

        EquipeRequest request = new EquipeRequest();
        request.setEquipePayload(payload);

        // Lever une exception lors de l'appel du service
        Mockito.doThrow(new RuntimeException("Erreur simulée"))
                .when(equipeService).updateEquipe(Mockito.any(EquipePayload.class));

        mockMvc.perform(patch("/equipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Erreur lors de la mise à jour de l'équipe: Erreur simulée")));
    }
}