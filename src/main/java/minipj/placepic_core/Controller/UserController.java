package minipj.placepic_core.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Service.AuthenticationService;
import minipj.placepic_core.Service.UserService;
import org.springframework.web.bind.annotation.*;

@Api("유저관련 테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    @ApiOperation(value="유저삭제",notes = "유저삭제 PostMethod")
    @PostMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

}
