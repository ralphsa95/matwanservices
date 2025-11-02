package fr.matawan.football.management.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoueurPayload {

    @Id
    private Long id;

    @NotBlank(message = "Le nom du joueur est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom du joueur est obligatoire")
    private String prenom;

    @NotNull(message = "La date début du joueur est obligatoire")
    @Schema(type = "string", format = "date", example = "2025-01-01", defaultValue = "2025-01-01")
    private Date dateDebut;

    @NotNull(message = "La date fin contrat du joueur est obligatoire")
    @Schema(type = "string", format = "date", example = "2029-01-01", defaultValue = "2029-01-01")
    private Date dateFinContrat;

    @NotBlank(message = "La position du joueur est obligatoire")
    private String position;
}
