package it.gennystabile.ricettariobe.service;

import it.gennystabile.ricettariobe.dto.user.UserInputDto;
import it.gennystabile.ricettariobe.dto.user.UserOutputDto;
import it.gennystabile.ricettariobe.exception.ResourceNotFoundException;
import it.gennystabile.ricettariobe.mapper.UserMapper;
import it.gennystabile.ricettariobe.model.User;
import it.gennystabile.ricettariobe.repository.UserRepository;
import it.gennystabile.ricettariobe.utils.constant.ControllersConstants;
import it.gennystabile.ricettariobe.utils.enumeration.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
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

    public UserOutputDto getById(Long id) {
        return userMapper.toOutputDto(getUser(id));
    }

    public UserOutputDto register(UserInputDto userInputDto) {
        User user = userMapper.toUtente(userInputDto);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }
}
