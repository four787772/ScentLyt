package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.UserLoginDTO;
import com.haui.ScentLyt.entity.User;
import com.haui.ScentLyt.exception.DataNotFoundException;
import com.haui.ScentLyt.exception.InvalidParamException;
import com.haui.ScentLyt.repository.UserRepository;
import com.haui.ScentLyt.service.UserService;
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


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtils jwtTokenUtils;



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

//        List<UserRole> userRoles = user.get().getUserRoles();
//
//        for (UserRole userRole : userRoles) {
//            if (userRole.getRole().getActive() && userRole.getRole().getRoleName().equals("ROLE_GUEST"))
//                errorMessages.add(localizationUtils.getLocalizedMessage(MessageKeys.USER_IS_NOT_ALLOWED_LOGIN));
//            return errorMessages;
//        }

        if (!user.get().getActive()) {
            errorMessages.add("User is not active!");
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

        existingUser.setFullName(updateUserDTO.getFullName());
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
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if(!updateUserDTO.getEmail().equals(existingUser.getEmail())){
            Optional<User> userByEmail = userRepository.findByEmailAndActive(updateUserDTO.getEmail(), true);
            if(userByEmail.isPresent()) errorMessages.add(localizationUtils.getLocalizedMessage(MessageKeys.EMAIL_ALREADY_IS_USED));
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
            errorMessages.add(localizationUtils.getLocalizedMessage(MessageKeys.CURRENT_PASSWORD_WRONG));
        }

        if(!Objects.equals(changePasswordDTO.getConfirmPassword(), changePasswordDTO.getNewPassword())){
            errorMessages.add(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
        }

        return errorMessages;
    }
}
