package com.solutis.acabouomony.account.service;

import com.solutis.acabouomony.account.dto.request.UserReqDTO;
import com.solutis.acabouomony.account.dto.request.UserUpdDTO;
import com.solutis.acabouomony.account.dto.response.UserRespDTO;
import com.solutis.acabouomony.account.model.UserAuth;
import com.solutis.acabouomony.account.model.User;
import com.solutis.acabouomony.account.repository.UserAuthRepository;
import com.solutis.acabouomony.account.repository.UserRepository;
import com.solutis.acabouomony.util.SecurityUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void createUser(UserReqDTO userReqDTO) {
        validateEmail(userReqDTO.getEmail());
        validateCpf(userReqDTO.getCpf());
        userReqDTO.setPassword(passwordEncoder.encode(userReqDTO.getPassword()));
        User user = new User(null, userReqDTO.getName(), userReqDTO.getPassword(),
                userReqDTO.getCpf(), userReqDTO.getContact(), userReqDTO.getEmail());
        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotAuthorizedException("Usuário não encontrado"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return generateOTP(user.getUserId());
        } else {
            throw new NotAuthorizedException("Usuário ou senha inválidos");
        }
    }

    public List<UserRespDTO> listUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserRespDTO(user.getName(), user.getCpf(), user.getContact(), user.getEmail()))
                .toList();
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(UserUpdDTO userUpdDTO) {
        User user = userRepository.findById(userUpdDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        if (userUpdDTO.getName() != null) user.setName(userUpdDTO.getName());
        if (userUpdDTO.getContact() != null) user.setContact(userUpdDTO.getContact());
        if (userUpdDTO.getEmail() != null) user.setEmail(userUpdDTO.getEmail());
    }

    public UserRespDTO detailUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        return new UserRespDTO(user.getName(), user.getCpf(), user.getContact(), user.getEmail());
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EntityExistsException("Email já está em uso");
        }
    }

    private void validateCpf(String cpf) {
        if (userRepository.existsByCpf(cpf)) {
            throw new EntityExistsException("CPF já está em uso");
        }
    }

    private String generateOTP(UUID userId) {
        UserAuth userAuth = userAuthRepository.findUserAuthByUserIdAndIsUsedIsFalse(userId);
        String otpCode;
        if (userAuth != null) {
            otpCode = userAuth.getOtpCode();
        } else {
            otpCode = SecurityUtil.generateOtpCode();
            userAuthRepository.save(new UserAuth(userId, otpCode, false));
        }
        return otpCode;
    }

    public boolean validateOTP(String otpCode) {
        return userAuthRepository.findUserAuthByOtpCodeAndIsUsedIsFalse(otpCode) != null;
    }

    public UserAuth getOtpEntity(String otpCode) {
        return userAuthRepository.findUserAuthByOtpCodeAndIsUsedIsFalse(otpCode);
    }
}
