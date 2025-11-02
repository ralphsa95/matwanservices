package fr.matwan.football.management.service;

import fr.matwan.football.management.mapper.EquipeMapper;
import fr.matwan.football.management.model.Equipe;
import fr.matwan.football.management.payload.EquipePayload;
import fr.matwan.football.management.repository.EquipeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EquipeService {
    @Autowired
    EquipeMapper equipeMapper;

    @Autowired
    EquipeRepository equipeRepository;

    /**
     * Récupère une page d'équipes.
     *
     * @param pageable les informations de pagination
     * @return une page d'équipes sous forme de payloads
     */
    @Transactional
    public Page<EquipePayload> getAllEquipes(Pageable pageable)  {
        Page<Equipe> equipes = equipeRepository.findAll(pageable);
        return equipes.map(equipeMapper::toPayload);
    }

    /**
     * Crée une nouvelle équipe.
     *
     * @param equipePayload les données de l'équipe à créer
     */
    @Transactional
    public void createEquipe(EquipePayload equipePayload)  {
        Optional existingEquipe = equipeRepository.findByNomOrAcronym(equipePayload.getNom(), equipePayload.getAcronym());
        if(existingEquipe.isPresent()) {
            throw new RuntimeException("Équipe déjà existante");
        }
            Equipe equipe = equipeMapper.fromPayload(equipePayload);
            equipeRepository.save(equipe);
    }

    /**
     * Met à jour une équipe existante.
     *
     * @param equipePayload les nouvelles données de l'équipe
     */
    @Transactional
    public void updateEquipe(@Valid EquipePayload equipePayload) {
        Equipe existingEquipe = equipeRepository.findByNomOrAcronym(equipePayload.getNom(), equipePayload.getAcronym())
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée pour la mise à jour"));

        equipeMapper.updateEquipeFromPayload(equipePayload, existingEquipe);
        equipeRepository.save(existingEquipe);
    }
}
