package fr.matwan.football.management.payload;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipePayload {

    @Id
    private Long id;

    @NotBlank(message = "Le nom de l'équipe est obligatoire")
    private String nom;

    @NotBlank(message = "L'acronym de l'équipe est obligatoire")
    private String acronym;

    @NotNull(message = "Le budget de l'équipe est obligatoire")
    private Long budget;

    @Valid
    private List<JoueurPayload> joueurs;
}
