package maurezg7.backend.services;

import maurezg7.backend.models.Enum.StatesUser;
import maurezg7.backend.models.entity.StateUser;
import maurezg7.backend.repository.StateUserRepository;
import org.springframework.stereotype.Service;

@Service
public class StatesUserService {
    private final StateUserRepository stateRepository;
    private final UserService userService;

    public StatesUserService(StateUserRepository stateRepository, UserService userService) {
        this.stateRepository = stateRepository;
        this.userService = userService;
    }
    
    public StatesUser getStateUser(Long idUser) {
        return this.stateRepository.getStateUser(idUser).getState();
    }
    
    public void createState(String username) {
        if (username == null || username.isBlank()) return;
        Long idUser = this.userService.getUserId(username);
        StateUser stateUser = new StateUser(idUser, StatesUser.LINE);
        this.stateRepository.save(stateUser);
    }
    
    public void changeUpdate(Long idUser, StatesUser newState) {
        StateUser stateUser = this.stateRepository.getStateUser(idUser);
        if (stateUser != null) {
            stateUser.setState(newState);
            this.stateRepository.save(stateUser);
        }
    }
}
