package maurezg7.backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.AuthServiceDTO;
import maurezg7.backend.models.DTO.ChangePassword;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.AuthRepository;
import maurezg7.backend.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void register(AuthServiceDTO registerdDto) throws MessagingException {
        if(registerdDto == null) throw new IllegalArgumentException("User cannot be null");
        
        boolean existUsername = this.authRepository.existsByUsername(registerdDto.getUsername());
        boolean existEmail = this.authRepository.existsByEmail(registerdDto.getEmail());
        
        if(existUsername)
            throw new RuntimeException("Username already exists");

        if(existEmail)
            throw new RuntimeException("Email already exists");

        String rawPassword = registerdDto.getPassword();
        
        User user = new User();
        user.setUsername(registerdDto.getUsername());
        user.setEmail(registerdDto.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));

        this.userRepository.save(user);
        this.requestLogin(user.getUsername(), rawPassword);
    }

    public void requestLogin(String datauser, String rawPassword) throws MessagingException {
        User user = authRepository.findByUsernameOrEmail(datauser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Incorrent password");
        }
        if(user.getUser_verify() == false){
            String randomCode = String.valueOf((int)(Math.random() * 900000) + 100000);
            user.setVerificationCode(randomCode);
            user.setCodeExpiration(LocalDateTime.now().plusMinutes(5));
            this.userRepository.save(user);
            this.sendVerificationEmail(user.getEmail(), randomCode);
        }

    }

    public boolean verifyCode(String datauser, String rawPassword, String code) {
        User user = authRepository.findByUsernameOrEmail(datauser).orElse(null);

        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            if (user.getVerificationCode() != null &&
                    user.getVerificationCode().equals(code) &&
                    user.getCodeExpiration().isAfter(LocalDateTime.now())) {
                    if(!user.getUser_verify())
                        user.setUser_verify(true);
                    this.authRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public void sendVerificationEmail(String to, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("🔒 Tu código de verificación");

        String htmlContent =
                "<div style='font-family: sans-serif; border: 1px solid #eee; padding: 20px; border-radius: 10px; max-width: 500px; margin: auto;'>" +
                        "<h2 style='color: #333;'>Verifica tu cuenta</h2>" +
                        "<p>Usa el siguiente código para completar tu acceso:</p>" +
                        "<div style='background: #f4f4f4; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px; color: #007bff;'>" +
                        code +
                        "</div>" +
                        "<p style='font-size: 12px; color: #777; margin-top: 20px;'>" +
                        "Este código expira en 5 minutos por razones de seguridad." +
                        "</p>" +
                        "</div>";

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    public boolean changePassword(ChangePassword changePassword){
        User user = this.authRepository.findByUsernameOrEmail(changePassword.getDatauser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(user.getPassword().equals(changePassword.getOldpassword())){
            user.setPassword(passwordEncoder.encode(changePassword.getNewpassword()));
            this.authRepository.save(user);
            return true;
        }
        return false;
    }
}