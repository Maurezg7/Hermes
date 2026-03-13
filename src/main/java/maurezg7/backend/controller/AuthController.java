package maurezg7.backend.controller;

import jakarta.mail.MessagingException;
import java.util.Collections;
import maurezg7.backend.models.DTO.AuthServiceDTO;
import maurezg7.backend.models.DTO.ChangePassword;
import maurezg7.backend.models.entity.AuthUser;
import maurezg7.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(value = "http://localhost:5173")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    public String info(Authentication auth) {
    // auth.getAuthorities() -> te da los roles (ADMIN, USER)
    // auth.getPrincipal() -> te da el objeto de usuario completo
    return "Usuario conectado: " + auth.getName();
    }

    @PostMapping("/register-request")
    public ResponseEntity<?> requestRegister(@RequestBody AuthServiceDTO registerRequestDTO) throws MessagingException {
        this.authService.register(registerRequestDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario registrado y código enviado"));
    }

    @PostMapping("/login-request")
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
        boolean isValid = this.authService.verifyCode(authUser.getDataUser(), authUser.getDataPassword(), code);
        if (isValid) {
            return ResponseEntity.ok(Collections.singletonMap("status", "success"));
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("status", "invalid_code_or_credentials"));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword){
        this.authService.changePassword(changePassword);
        return  ResponseEntity.ok(Collections.singletonMap("message", "Password changed"));
    }
}