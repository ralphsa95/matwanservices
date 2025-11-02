package fr.matawan.football.management.mapper;

import fr.matawan.football.management.model.Joueur;
import fr.matawan.football.management.payload.JoueurPayload;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EquipeMapper.class})
public interface JoueurMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "nom", source = "joueurPayload.nom"),
            @Mapping(target = "prenom", source ="joueurPayload.prenom"),
            @Mapping(target = "dateDebut", source ="joueurPayload.dateDebut"),
            @Mapping(target = "dateFinContrat", source ="joueurPayload.dateFinContrat"),
            @Mapping(target = "position", source ="joueurPayload.position"),
    })
    Joueur fromPayload(JoueurPayload joueurPayload);

    @Mappings({
            @Mapping(target = "id", source = "joueur.id"),
            @Mapping(target = "nom", source = "joueur.nom"),
            @Mapping(target = "prenom", source ="joueur.prenom"),
            @Mapping(target = "dateDebut", source ="joueur.dateDebut"),
            @Mapping(target = "dateFinContrat", source ="joueur.dateFinContrat"),
            @Mapping(target = "position", source ="joueur.position"),
    })
    JoueurPayload toPayload(Joueur joueur);

}
