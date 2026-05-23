package maurezg7.backend.controller;

import maurezg7.backend.models.Enum.StatesUser;
import maurezg7.backend.services.StatesUserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statesuser")
@CrossOrigin(origins = "https://vercel.app")
public class StateUserController {
    private final StatesUserService stateService;

    public StateUserController(StatesUserService stateService) {
        this.stateService = stateService;
    }
    
     @GetMapping("/userstate/{iduser}")
    public String getUserStateByIDUser(@PathVariable Long iduser) {
        return this.stateService.getStateUser(iduser).name();
    }

    @PutMapping("/userstate/{iduser}/{stateuser}")
    public void updateStateUser(
        @PathVariable("iduser") Long iduser, 
        @PathVariable("stateuser") StatesUser stateuser
    ) {
        this.stateService.changeUpdate(iduser, stateuser);
    }
}
