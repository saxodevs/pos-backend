package com.saxodevs.pos.service.implementation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.request.ChangePasswordRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saxodevs.pos.config.JwtProvider;
import com.saxodevs.pos.domain.UserRole;
import com.saxodevs.pos.dto.UserDTO;
import com.saxodevs.pos.exceptions.UserException;
import com.saxodevs.pos.mapper.UserMapper;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.UserRepository;
import com.saxodevs.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserImplementation implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

//    UserDetails userDetails;


    @Override
    public User getUserFromJwtToken(String token)  {

        String username = jwtProvider.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AppException("Invalid token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new AppException("Invalid token", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return user;

    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws UserException {

        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new AppException("Username already exist", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByPhone(userDTO.getPhone()) != null) {
            throw new AppException("Phone number already exist", HttpStatus.BAD_REQUEST);
        }

        if (userDTO.getRole() == UserRole.MANAGER || userDTO.getRole() == UserRole.CASHIER) {

            User newUser = UserMapper.toEntity(userDTO);

            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            return UserMapper.toDTO(userRepository.save(newUser));

        }
        throw new AppException("Role as admin not supported", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDTO getUserById(UUID id) throws UserException {

        User existingUser = userRepository.findById(id).orElseThrow(()
                -> new AppException("User not found by " + id, HttpStatus.NOT_FOUND));

        return UserMapper.toDTO(existingUser);

    }

    @Override
    public List<UserDTO> getAllUsers() throws UserException {

        List<User> users = userRepository.findByDeletedFalse();
        return UserMapper.userDtoList(users);
    }

    @Override
    public void deleteUserById(UUID id) throws UserException {
        User user = getCurrentUser();
        User exixtingUser = userRepository.findById(id).orElseThrow(
                () -> new AppException("User not found by " + id, HttpStatus.NOT_FOUND));

        exixtingUser.setDeleted(true);
        exixtingUser.setDeletedBy(user);

        userRepository.save(exixtingUser);

    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDto) throws UserException {

        User user = userRepository.findById(id).orElseThrow(() -> new AppException("User not found by " + id, HttpStatus.NOT_FOUND));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());

        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);

    }

    @Override
    public UserDTO changePassword(ChangePasswordRequest request) {

        User user = getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AppException("Wrong password", HttpStatus.NOT_FOUND);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return  UserMapper.toDTO(user);
    }
}
