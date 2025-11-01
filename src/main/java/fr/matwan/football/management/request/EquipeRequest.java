package fr.matwan.football.management.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.matwan.football.management.payload.EquipePayload;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipeRequest {
    @Valid
    @JsonProperty("equipePayload")
    private EquipePayload equipePayload;
}
