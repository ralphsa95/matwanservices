package fr.matawan.football.management.mapper;

import fr.matawan.football.management.model.Equipe;
import fr.matawan.football.management.model.Joueur;
import fr.matawan.football.management.payload.EquipePayload;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper pour Equipe and EquipePayload
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {JoueurMapper.class})
public interface EquipeMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "nom", source = "equipePayload.nom"),
            @Mapping(target = "acronym", source = "equipePayload.acronym"),
            @Mapping(target = "budget", source = "equipePayload.budget"),
            @Mapping(target = "joueurs", source = "equipePayload.joueurs"),
    })
    Equipe fromPayload(EquipePayload equipePayload);

    @Mappings({
            @Mapping(target = "id", source = "equipe.id"),
            @Mapping(target = "nom", source = "equipe.nom"),
            @Mapping(target = "acronym", source = "equipe.acronym"),
            @Mapping(target = "budget", source = "equipe.budget"),
            @Mapping(target = "joueurs", source = "equipe.joueurs"),
    })
    EquipePayload toPayload(Equipe equipe);

    /**
     * Après le mapping, lier chaque Joueur à son Equipe parent
     */
    @AfterMapping
    default void linkJoueursToEquipe(@MappingTarget Equipe equipe) {
        if (equipe.getJoueurs() != null) {
            for (Joueur joueur : equipe.getJoueurs()) {
                joueur.setEquipe(equipe);
            }
        }
    }

    /**
     * Met à jour l'entité Equipe existante à partir du payload. Les propriétés "null" dans le payload ne seront pas copiées.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "nom",  ignore = true),
            @Mapping(target = "acronym", ignore = true),
            @Mapping(target = "budget", source = "equipePayload.budget"),
            @Mapping(target = "joueurs", source = "equipePayload.joueurs"),
    })
    void updateEquipeFromPayload(EquipePayload equipePayload, @MappingTarget Equipe equipe);


    // Après le mapping, lier chaque Joueur à son Equipe parent
    @AfterMapping
    default void linkJoueursToEquipeUpdate(EquipePayload equipePayload, @MappingTarget Equipe equipe) {
        if (equipe.getJoueurs() != null) {
            for (Joueur joueur : equipe.getJoueurs()) {
                joueur.setEquipe(equipe);
            }
        }
    }
}
