package fr.matawan.football.management.service;

import fr.matawan.football.management.mapper.EquipeMapper;
import fr.matawan.football.management.model.Equipe;
import fr.matawan.football.management.payload.EquipePayload;
import fr.matawan.football.management.repository.EquipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeServiceTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private EquipeMapper equipeMapper;

    @InjectMocks
    private EquipeService equipeService;

    @Test
    void testGetAllEquipes() {
        // Crée des équipes (entité)
        Equipe equipe1 = new Equipe();
        equipe1.setAcronym("PSG");
        Equipe equipe2 = new Equipe();
        equipe2.setAcronym("OM");
        Equipe equipe3 = new Equipe();
        equipe3.setAcronym("OL");
        List<Equipe> allEquipes = Arrays.asList(equipe1, equipe2, equipe3);

        // Mock le repository pour retourner une page d'entités
        Page<Equipe> page = new PageImpl<>(allEquipes, PageRequest.of(0, 10), allEquipes.size());
        when(equipeRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Mock le mapper pour convertir chaque équipe en payload
        when(equipeMapper.toPayload(any(Equipe.class))).thenAnswer(invocation -> {
            Equipe e = invocation.getArgument(0);
            EquipePayload payload = new EquipePayload();
            payload.setNom(e.getNom());
            payload.setAcronym(e.getAcronym());
            return payload;
        });

        // Appelle le service
        Page<EquipePayload> result = equipeService.getAllEquipes(PageRequest.of(0, 10));
        assertEquals(3, result.getTotalElements());

        // Vérifie que "PSG" est présent parmi les résultats
        boolean foundPSG = result.getContent().stream().anyMatch(eq -> "PSG".equals(eq.getAcronym()));
        assertTrue(foundPSG, "PSG should be present in the result");
    }

    @Test
    void testCreateEquipe() {
        // Prépare le payload
        EquipePayload equipePayload = new EquipePayload();
        equipePayload.setNom("TestEquipe");
        equipePayload.setAcronym("TE");
        equipePayload.setBudget(50000L);

        // Prépare l'entité qui sera retournée par le repository
        Equipe equipeEntity = new Equipe();
        equipeEntity.setNom("TestEquipe");
        equipeEntity.setAcronym("TE");
        equipeEntity.setBudget(50000L);

        // Mock le repository pour retourner l'entité correcte
        when(equipeRepository.save(Mockito.any())).thenReturn(equipeEntity);
        when(equipeRepository.findByNom("TestEquipe")).thenReturn(Optional.of(equipeEntity));

        // Appelle le service
        equipeService.createEquipe(equipePayload);

        // Vérifie que le repository contient bien l'équipe
        Optional<Equipe> saved = equipeRepository.findByNom("TestEquipe");
        assertEquals("TE", saved.get().getAcronym());
    }

    @Test
    void testUpdateEquipe() {
        // 1. payload (ce qu' on passe au service);
        EquipePayload equipePayload = new EquipePayload();
        equipePayload.setAcronym("OGCN");
        equipePayload.setNom("OGC Nice");
        equipePayload.setBudget(30000L);

        // 2. l'entité persistée (ce que le repo retourne) q
        Equipe equipeEntity = new Equipe();
        equipeEntity.setAcronym("OGCN");
        equipeEntity.setNom("OGC Nice");
        equipeEntity.setBudget(30000L);

        // 3. Mock la méthode save pour retourner l'entité
        when(equipeRepository.save(Mockito.any())).thenReturn(equipeEntity);

        // 4. Mock la méthode findByAcronym pour retourner l'entité
        when(equipeRepository.findByNomAndAcronym("OGC Nice","OGCN")).thenReturn(Optional.of(equipeEntity));

        // 5. Appelle la méthode du service
        equipeService.updateEquipe(equipePayload);

        // 6. Vérification du résultat
        Optional<Equipe> result = equipeRepository.findByNomAndAcronym("OGC Nice","OGCN");
        assertEquals(30000L, result.get().getBudget());
    }
}