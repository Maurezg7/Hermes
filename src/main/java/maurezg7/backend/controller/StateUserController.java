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
@CrossOrigin(origins = "https://hermes-c6gswsisi-maurezg7.vercel.app")
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
    public ResponseEntity<Void> updateStateUser(
            @PathVariable("iduser") Long iduser,
            @PathVariable("stateuser") String stateuser) {
        try {
            StatesUser enumState = StatesUser.valueOf(stateuser.toUpperCase().trim());

            this.stateService.changeUpdate(iduser, enumState);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
