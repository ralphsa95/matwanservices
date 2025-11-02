package fr.matawan.football.management.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Joueur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOM", length = 50, nullable = false)
    private String nom;

    @Column(name = "PRENOM", length = 50, nullable = false)
    private String prenom;

    @Column(name = "DATE_DEBUT",nullable = false)
    private Date dateDebut;

    @Column(name = "DATE_FIN",nullable = false)
    private Date dateFinContrat;

    @Column(name = "POSITION", length = 50, nullable = false)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EQUIPE_ID", referencedColumnName = "ID", nullable = false)
    private Equipe equipe;
}
