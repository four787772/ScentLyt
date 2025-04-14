package com.haui.ScentLyt.response.user;

import com.haui.ScentLyt.entity.User;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Integer id;

    private String fullname;

    private String phoneNumber;

    private String email;

    private String address;

    private LocalDate dateOfBirth;

    private Integer roleId;

    private String roleName;

    public static UserResponse fromUser(User user) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        if (user.getRole() != null) {
            response.setRoleId(user.getRole().getId());
            response.setRoleName(user.getRole().getRoleName());
        }

        return response;
    }
}
