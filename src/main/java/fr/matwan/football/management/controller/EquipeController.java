package fr.matwan.football.management.controller;

import fr.matwan.football.management.payload.EquipePayload;
import fr.matwan.football.management.request.EquipeRequest;
import fr.matwan.football.management.response.EquipeResponse;
import fr.matwan.football.management.service.EquipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

/**
 * Controller pour gérer les équipes de football.
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/equipe")
public class EquipeController {

    @Autowired
    EquipeService equipeService;

    /**
     * Récupère une liste paginée et triée des équipes.
     *
     * @param page Numéro de la page à récupérer (par défaut 0).
     * @param size Taille de la page (par défaut 10).
     * @param sort Elément de tri (par défaut "nom").
     * @param sens Sens de triage (par défaut "asc").
     * @return Une réponse contenant la liste paginée et triée des équipes.
     * @throws Exception En cas d'erreur lors de la récupération des équipes.
     */
    @GetMapping
    @Operation(summary = "Lister les équipes", description = "Retourne la liste paginée et trié des équipes.")
    public ResponseEntity<?> getEquipes(
            @Parameter(description = "Numéro de page", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de page", example = "5")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Elément de tri entre:nom, acronym ou budget", example = "acronym")
            @RequestParam(defaultValue = "nom") String sort, // "nom", "acronym", "budget"
            @Parameter(description = "Sens de triage", example = "asc ou desc")
            @RequestParam(defaultValue = "asc") String sens
    ) throws Exception {
        Sort sortOrder = sens.equalsIgnoreCase("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<EquipePayload> pageResult = equipeService.getAllEquipes(pageable);


        EquipeResponse response = new EquipeResponse();
        response.setContent(pageResult.getContent());
        response.setPage(pageResult.getNumber());
        response.setSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setFirst(pageResult.isFirst());
        response.setLast(pageResult.isLast());

        return ResponseEntity.ok(response);
    }

    /**
     * Crée une nouvelle équipe.
     *
     * @param equipeRequest La requête contenant les données de l'équipe à créer.
     * @return Une réponse indiquant le succès ou l'échec de l'opération.
     */
    @PostMapping
    public ResponseEntity<?> createEquipe(@Valid @RequestBody EquipeRequest equipeRequest)  {
       try {
           equipeService.createEquipe(equipeRequest.getEquipePayload());
           return ResponseEntity.ok("L'équipe \"" + equipeRequest.getEquipePayload().getNom() + "\" a été " +
                   "correctement créée.");
       } catch (Exception e) {
           return   ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'équipe: " + e.getMessage());
       }
    }

    /**
     * Met à jour une équipe existante.
     *
     * @param equipeRequest La requête contenant les données de l'équipe à mettre à jour.
     * @return Une réponse indiquant le succès ou l'échec de l'opération.
     */
    @PatchMapping
    public ResponseEntity<?> updateEquipe(@Valid @RequestBody EquipeRequest equipeRequest) {
        try {
            equipeService.updateEquipe(equipeRequest.getEquipePayload());
            return ResponseEntity.ok("L'équipe \"" + equipeRequest.getEquipePayload().getNom() + "\" a été " +
                    "correctement mise à jour.");
        } catch (Exception e) {
            return   ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de l'équipe: " + e.getMessage());
        }
    }


}

