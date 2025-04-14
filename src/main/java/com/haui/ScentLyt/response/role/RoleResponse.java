package com.haui.ScentLyt.response.role;

import lombok.*;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleResponse {
    private Integer id;
    private String roleName;
    private Boolean active;

    public static RoleResponse fromRole(Role role) {
        RoleResponse response = new RoleResponse();
        BeanUtils.copyProperties(role, response);
        response.setId(role.getRoleId());
        return response;
    }
}
