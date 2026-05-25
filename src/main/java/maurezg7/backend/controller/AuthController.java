package maurezg7.backend.controller;

import jakarta.mail.MessagingException;
import java.util.Collections;
import java.util.Map;
import maurezg7.backend.models.DTO.AuthServiceDTO;
import maurezg7.backend.models.DTO.ChangePassword;
import maurezg7.backend.models.entity.AuthUser;
import maurezg7.backend.services.AuthService;
import maurezg7.backend.services.StatesUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "https://hermes-c6gswsisi-maurezg7.vercel.app")
public class AuthController {
    private final AuthService authService;
    private final  StatesUserService stateService;
    
    public AuthController(AuthService authService, StatesUserService stateService){
        this.authService = authService;
        this.stateService = stateService;
    }
    
    public String info(Authentication auth) {
        return "Usuario conectado: " + auth.getName();
    }

    @PostMapping("/register")
    public ResponseEntity<?> requestRegister(@RequestBody AuthServiceDTO registerRequestDTO) throws MessagingException {
        this.authService.register(registerRequestDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario registrado y código enviado"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> requestLogin(@RequestBody AuthUser authUser) throws MessagingException {
        try {
            this.authService.requestLogin(authUser.getDataUser(), authUser.getDataPassword());
            return ResponseEntity.ok(Collections.singletonMap("message", "Usuario logueado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody AuthUser authUser, @RequestParam String code) {
        try {
            String token = this.authService.verifyCode(authUser.getDataUser(), authUser.getDataPassword(), code);

            this.stateService.createState(authUser.getDataUser());

            return ResponseEntity.ok(Map.of(
                "status", "success",
                "token", token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword){
        this.authService.changePassword(changePassword);
        return  ResponseEntity.ok(Collections.singletonMap("message", "Password changed"));
    }
}
