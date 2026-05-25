package maurezg7.backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.AuthServiceDTO;
import maurezg7.backend.models.DTO.ChangePassword;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.AuthRepository;
import maurezg7.backend.repository.UserRepository;
import maurezg7.backend.security.JwtUtils;
import maurezg7.backend.models.Enum.StatesUser;

@Service
public class AuthService {
    private final JavaMailSender mailSender;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final StatesUserService statesUserService;

    public AuthService(JavaMailSender mailSender, AuthRepository authRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtUtils jwtUtils, StatesUserService statesUserService) {
        this.mailSender = mailSender;
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.statesUserService = statesUserService;
        this.jwtUtils = jwtUtils;
    }

    public void register(AuthServiceDTO registerdDto) throws MessagingException {
        if (registerdDto == null)
            throw new IllegalArgumentException("User cannot be null");

        boolean existUsername = this.authRepository.existsByUsername(registerdDto.getUsername());
        boolean existEmail = this.authRepository.existsByEmail(registerdDto.getEmail());

        if (existUsername)
            throw new RuntimeException("Username already exists");

        if (existEmail)
            throw new RuntimeException("Email already exists");

        String rawPassword = registerdDto.getPassword();

        User user = new User();
        user.setUsername(registerdDto.getUsername());
        user.setEmail(registerdDto.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));

        this.userRepository.save(user);
        this.statesUserService.createState(user.getUsername(), StatesUser.LINE);
        this.requestLogin(user.getUsername(), rawPassword);
    }

    public void requestLogin(String datauser, String rawPassword) throws MessagingException {
        User user = authRepository.findByUsernameOrEmail(datauser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        if (user.getUser_verify() == false) {
            java.security.SecureRandom random = new java.security.SecureRandom();
            String randomCode = String.format("%06d", random.nextInt(999999));
            user.setVerificationCode(randomCode);
            user.setCodeExpiration(java.time.LocalDateTime.now().plusMinutes(5));
            this.userRepository.save(user);

            this.sendVerificationEmail(user.getEmail(), randomCode);
        }
    }

    public String verifyCode(String datauser, String rawPassword, String code) {
        User user = authRepository.findByUsernameOrEmail(datauser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            if (user.getVerificationCode() != null &&
                    user.getVerificationCode().equals(code) &&
                    user.getCodeExpiration().isAfter(LocalDateTime.now())) {

                if (!user.getUser_verify()) {
                    user.setUser_verify(true);
                    user.setVerificationCode(null);
                    this.authRepository.save(user);
                }

                return jwtUtils.generateToken(user.getUsername());
            }
        }
        throw new RuntimeException("Invalid code or credentials");
    }

    // CORRECCIÓN: Colocamos el @Async AQUÍ. Este es el proceso pesado que tarda segundos e internet puede bloquear
    @Async 
    public void sendVerificationEmail(String to, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("🔒 Tu código de verificación");

        String htmlContent = "<div style='font-family: sans-serif; border: 1px solid #eee; padding: 20px; border-radius: 10px; max-width: 500px; margin: auto;'>"
                +
                "<h2 style='color: #333;'>Verifica tu cuenta</h2>" +
                "<p>Usa el siguiente código para completar tu acceso:</p>" +
                "<div style='background: #f4f4f4; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px; color: #007bff;'>"
                +
                code +
                "</div>" +
                "<p style='font-size: 12px; color: #777; margin-top: 20px;'>" +
                "Este código expira en 5 minutes por razones de seguridad." +
                "</p>" +
                "</div>";

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    public boolean changePassword(ChangePassword changePassword) {
        User user = this.authRepository.findByUsernameOrEmail(changePassword.getDatauser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (passwordEncoder.matches(changePassword.getOldpassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePassword.getNewpassword()));
            this.authRepository.save(user);
            return true;
        }
        return false;
    }
}
