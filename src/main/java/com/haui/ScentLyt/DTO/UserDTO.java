package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @JsonProperty("fullname")
    @NotNull(message = "{user.fullname.not_null}")
    @NotBlank(message = "{user.fullname.not_blank}")
    private String fullName;

    @JsonProperty("phone_number")
    @NotNull(message = "{user.phone_number.not_null}")
    @NotBlank(message = "{user.phone_number.not_blank}")
    @Pattern(regexp = "^\\d{10}$", message = "{user.phone_number.pattern}")
    private String phoneNumber;

    @JsonProperty("email")
    @NotNull(message = "{user.email.not_null}")
    @NotBlank(message = "{user.email.not_blank}")
    @Pattern(regexp = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "{user.email.pattern}")
    private String email;

    private String address;

    @NotNull(message = "{user.password.not_null}")
    @NotBlank(message = "{user.password.not_blank}")
    private String password;

    @JsonProperty("retype_password")
    @NotNull(message = "{user.retype_password.not_null}")
    @NotBlank(message = "{user.retype_password.not_blank}")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "{user.role_id.not_null}")
    @JsonProperty("role_id")
    private Integer roleId;
}
