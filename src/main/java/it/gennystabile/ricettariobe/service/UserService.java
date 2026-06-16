package it.gennystabile.ricettariobe.service;

import it.gennystabile.ricettariobe.dto.auth.ResetPasswordRequest;
import it.gennystabile.ricettariobe.dto.user.UserInputDto;
import it.gennystabile.ricettariobe.dto.user.UserOutputDto;
import it.gennystabile.ricettariobe.exception.BadRequestException;
import it.gennystabile.ricettariobe.exception.DuplicateException;
import it.gennystabile.ricettariobe.exception.ResourceNotFoundException;
import it.gennystabile.ricettariobe.mapper.UserMapper;
import it.gennystabile.ricettariobe.model.PasswordResetToken;
import it.gennystabile.ricettariobe.model.User;
import it.gennystabile.ricettariobe.repository.PasswordResetTokenRepository;
import it.gennystabile.ricettariobe.repository.UserRepository;
import it.gennystabile.ricettariobe.utils.constant.ControllersConstants;
import it.gennystabile.ricettariobe.utils.enumeration.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }


    public List<UserOutputDto> getAll() {
        List<User> utenti;
        List<UserOutputDto> listaUtenti = new ArrayList<>();
        utenti = userRepository.findAll();
        utenti.forEach(utente -> {
            listaUtenti.add(userMapper.toOutputDto(utente));
        });
        return listaUtenti;
    }

    public String generateTokenToResetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("L'email inserita non è stata trovata"));
        String tokenTemporaneo = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(tokenTemporaneo);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        passwordResetTokenRepository.save(resetToken);
        return ("http://localhost:4200/reset-password?token=" + tokenTemporaneo);
    }

    @Transactional(noRollbackFor = BadRequestException.class)
    public Boolean resetPassword(ResetPasswordRequest request) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(request.getToken()).orElseThrow(() -> new ResourceNotFoundException("Token non valido"));

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            passwordResetTokenRepository.delete(passwordResetToken);
            throw new BadRequestException("Token scaduto per il reset della password!");
        }
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
        return true;
    }

    public UserOutputDto getById(Long id) {
        return userMapper.toOutputDto(getUser(id));
    }

    public UserOutputDto register(UserInputDto userInputDto) {
        User user = userMapper.toUtente(userInputDto);
        if(userRepository.findByEmailOrUsername(userInputDto.getEmail(),userInputDto.getEmail()).isPresent()){
            throw new DuplicateException("Email o Username inserite già presenti");
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(ControllersConstants.USER);
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        return userMapper.toOutputDto(userRepository.save(user));
    }

    public UserOutputDto deleteById(Long id) {
        User user = getUser(id);

        userRepository.deleteById(id);
        return userMapper.toOutputDto(user);
    }

    public String modifyRole(Long id, Role role) {
        User user = getUser(id);
        user.setRole(role.toString());
        userRepository.save(user);
        return "Utente Modificato";
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utente con id " + id + " non trovato"));
    }

   /* public String modifyPassword(UserInputDto inputDto) {
        User user = userRepository.findByUsernameAndEmail(inputDto.getUsername(), inputDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Username o email non valide"));
        user.setPassword(passwordEncoder.encode(inputDto.getPassword()));
        userRepository.save(user);
        return "Password cambiata correttamente";
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }
}
