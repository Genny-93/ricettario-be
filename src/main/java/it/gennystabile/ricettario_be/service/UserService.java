package it.gennystabile.ricettario_be.service;

import it.gennystabile.ricettario_be.dto.user.UserInputDto;
import it.gennystabile.ricettario_be.dto.user.UserOutputDto;
import it.gennystabile.ricettario_be.mapper.UserMapper;
import it.gennystabile.ricettario_be.model.User;
import it.gennystabile.ricettario_be.repository.UserRepository;
import it.gennystabile.ricettario_be.utils.constant.controller.UsersCostanti;
import it.gennystabile.ricettario_be.utils.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
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
        return userMapper.toOutputDto(userRepository.findById(id).orElse(null));
    }

    public UserOutputDto register(UserInputDto userInputDto) {
        User user = userMapper.toUtente(userInputDto);
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(UsersCostanti.USER);
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        UserOutputDto userOutputDto = userMapper.toOutputDto(userRepository.save(user));
        return userOutputDto;
    }

    public UserOutputDto deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return userMapper.toOutputDto(user.get());
        }
        return null;
    }

    public String modifyRole(Long id, Role role){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setRole(role.toString());
            userRepository.save(user.get());
            return "Utente Modificato";
        }
        return "Utente non Trovato";
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }
}
