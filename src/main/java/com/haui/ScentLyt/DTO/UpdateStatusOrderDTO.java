package com.haui.ScentLyt.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusOrderDTO {

    @NotBlank(message = "Status must not be blank")
    private String status;
}
