package fr.matwan.football.management.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Equipe implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOM", length = 50, nullable = false)
    private String nom;

    @Column(name = "ACRONYM", length = 50, nullable = false)
    private String acronym;

    @Column(name = "BUDGET",  nullable = false)
    private Long budget;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "equipe", fetch = FetchType.LAZY)
    @OrderBy("nom ASC")
    @ToString.Exclude
    private List<Joueur> joueurs;

}
