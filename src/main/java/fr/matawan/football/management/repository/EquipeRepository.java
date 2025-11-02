package fr.matawan.football.management.repository;

import fr.matawan.football.management.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository pour gérer les opérations CRUD sur les entités Equipe.
 */
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    Optional<Equipe> findByNom(String nom);

    Optional<Equipe> findByNomOrAcronym(String nom, String acronym);

    Optional<Equipe> findByAcronym(String acronym);
}