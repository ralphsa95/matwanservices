package fr.matwan.football.management.response;

import fr.matwan.football.management.payload.EquipePayload;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Classe pour personnaliser la réponse paginée de l'équipe .
 */
@Getter
@Setter
public class EquipeResponse {
    private List<EquipePayload> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
