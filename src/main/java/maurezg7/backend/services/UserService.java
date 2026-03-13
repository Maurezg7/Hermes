package maurezg7.backend.services;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import maurezg7.backend.models.DTO.UserDTO;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserDTO getUser(String userdata) {
        User user = userRepository.findByUsernameOrEmail(userdata)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + userdata));
        
        return new UserDTO(user.getUsername(), user.getEmail());
    }

    @Transactional
    public UserDTO registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getUsername(), savedUser.getEmail());
    }

    @Transactional
    public UserDTO updateUser(String userdata, UserDTO userDTO) {
        User user = userRepository.findByUsernameOrEmail(userdata)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        
        userRepository.save(user);

        return new UserDTO(user.getUsername(), user.getEmail());
    }

    @Transactional
    public Map<String, String> deleteUser(String userdata) {
        int deletedCount = userRepository.removeByUserData(userdata);
        
        if (deletedCount == 0) {
            throw new RuntimeException("No se pudo eliminar: Usuario no encontrado");
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return response;
    }
}