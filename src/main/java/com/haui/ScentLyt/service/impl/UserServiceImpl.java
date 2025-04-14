package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.ChangePasswordDTO;
import com.haui.ScentLyt.DTO.UpdateUserDTO;
import com.haui.ScentLyt.DTO.UserDTO;
import com.haui.ScentLyt.DTO.UserLoginDTO;
import com.haui.ScentLyt.entity.Role;
import com.haui.ScentLyt.entity.User;
import com.haui.ScentLyt.exception.DataNotFoundException;
import com.haui.ScentLyt.exception.ExpiredTokenException;
import com.haui.ScentLyt.exception.InvalidParamException;
import com.haui.ScentLyt.repository.RoleRepository;
import com.haui.ScentLyt.repository.UserRepository;
import com.haui.ScentLyt.response.user.UserResponse;
import com.haui.ScentLyt.service.UserService;
import com.haui.ScentLyt.utils.DateUtils;
import com.haui.ScentLyt.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.haui.ScentLyt.utils.ValidationUtils.isValidEmail;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtils jwtTokenUtils;

    private final DateUtils dateUtils;
    private final RoleRepository roleRepository;


    @Override
    public UserResponse save(UserDTO userDTO) throws DataNotFoundException {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setActive(true);

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);
        user.setEmail(userDTO.getEmail());
        user.setFullname(userDTO.getFullName());
        user.setDateOfBirth(dateUtils.convertDateToLocalDate(userDTO.getDateOfBirth()));

        Optional<Role> role = Optional.of(roleRepository.findById(userDTO.getRoleId()))
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy quyền"));

        user.setRole(role.orElse(new Role()));

        user = userRepository.save(user);

        return UserResponse.fromUser(user);
    }

    @Override
    public List<String> validUser(BindingResult result, UserDTO userDTO) {
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessages.add(error.getDefaultMessage());
            }
        }

        Optional<User> existingUser = userRepository.findByEmailOrPhoneNumber(userDTO.getEmail(), userDTO.getPhoneNumber());
        if (existingUser.isPresent())
            errorMessages.add("Số điện thoại hoặc email đã tồn tại");

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword()))
            errorMessages.add("Mật khẩu không khớp");

        return errorMessages;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws InvalidParamException {

        Optional<User> user = userRepository.findByPhoneNumberAndActive(userLoginDTO.getPhoneNumber(), true);
        String subject = userLoginDTO.getPhoneNumber();
        // Create authentication token using the found subject and granted authorities
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                subject,
                userLoginDTO.isPasswordBlank() ? "" : userLoginDTO.getPassword(),
                user.get().getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(user.get());
    }

    @Override
    public List<String> validLogin(BindingResult result, UserLoginDTO userLoginDTO) {
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessages.add(error.getDefaultMessage());
            }

            return errorMessages;
        }

        Optional<User> user = userRepository.findByPhoneNumberAndActive(userLoginDTO.getPhoneNumber(), true);
        if (user.isEmpty()) {
            errorMessages.add("Username or Password is incorrect!");
            return errorMessages;
        }

        Role role = user.get().getRole();


        if (role.getActive() && role.getRoleName().equals("ROLE_GUEST"))
            errorMessages.add("Bạn không có quyền login vào đây");

        if (!user.get().getActive()) {
            errorMessages.add("Tài khoản của bạn đã bị khoá");
        }

        return errorMessages;
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String subject = jwtTokenUtils.getSubject(token);
        Optional<User> user = userRepository.findByPhoneNumberAndActive(subject, true);
        if (user.isEmpty() && isValidEmail(subject)) {
            user = userRepository.findByEmailAndActive(subject, true);
        }
        return user.orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public Page<UserResponse> getAllUsers(String name, String phoneNumber, String email, Boolean active, Pageable pageable) {
        Page<User> userPage = userRepository.findAllUsers(name, phoneNumber, email, active, pageable);
        return userPage.map(UserResponse::fromUser);
    }

    @Override
    public User updateUser(Integer userId, UpdateUserDTO updateUserDTO) throws DataNotFoundException {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        existingUser.setFullname(updateUserDTO.getFullName());
        existingUser.setAddress(updateUserDTO.getAddress());
        existingUser.setEmail(updateUserDTO.getEmail());
        if (updateUserDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(dateUtils.convertDateToLocalDate(updateUserDTO.getDateOfBirth()));
        }

        // Save the updated user
        return userRepository.save(existingUser);
    }

    @Override
    public List<String> validUpdateUser(BindingResult result, UpdateUserDTO updateUserDTO, Integer userId) throws DataNotFoundException {
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessages.add(error.getDefaultMessage());
            }
            return errorMessages;
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy người dùng"));

        if(!updateUserDTO.getEmail().equals(existingUser.getEmail())){
            Optional<User> userByEmail = userRepository.findByEmailAndActive(updateUserDTO.getEmail(), true);
            if(userByEmail.isPresent()) errorMessages.add("Email đã được sử dụng");
        }

        return errorMessages;
    }

    @Override
    public void deleteUser(Integer userId) {
        Optional<User> user = userRepository.findByIdAndActive(userId, true);
        if(user.isPresent()) {
            user.get().setActive(false);
            userRepository.save(user.get());
        }
    }

    @Override
    public User updateUserPassword(User user, ChangePasswordDTO changePasswordDTO) {
        String newPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    public List<String> validateChangePassword(User user, BindingResult result, ChangePasswordDTO changePasswordDTO) {
        List<String> errorMessages = new ArrayList<>();

        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessages.add(error.getDefaultMessage());
            }

            return errorMessages;
        }

        boolean matches = passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword());
        if(!matches){
            errorMessages.add("Mật khẩu không chính xác");
        }

        if(!Objects.equals(changePasswordDTO.getConfirmPassword(), changePasswordDTO.getNewPassword())){
            errorMessages.add("Mật khẩu mới không khớp");
        }

        return errorMessages;
    }
}
