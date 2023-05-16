package minipj.placepic_core.Controller;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Service.AuthenticationService;
import minipj.placepic_core.Service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

}
