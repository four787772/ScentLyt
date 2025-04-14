package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @JsonProperty("role_name")
    @NotBlank(message = "{role.name.not_blank}")
    private String roleName;

    @JsonProperty("active")
    private Boolean active = true;
}
