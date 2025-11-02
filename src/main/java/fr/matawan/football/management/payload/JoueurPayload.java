package fr.matawan.football.management.payload;

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
    private Date dateDebut;

    @NotNull(message = "La date fin contrat du joueur est obligatoire")
    private Date dateFinContrat;

    @NotBlank(message = "La position du joueur est obligatoire")
    private String position;
}
