package minipj.placepic_core.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import minipj.placepic_core.Entity.UserDto;
import minipj.placepic_core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    @ApiOperation(value="유저조회",notes = "유저조회 PostMethod")
    @GetMapping("/findUser/{userId}")
    public ApiResponse findUser(@PathVariable Long userId){
         return ApiResponse.success(UserDto.UserInfo.toDto(userService.findUser(userId).get()));
    }

    @ApiOperation(value="유저정보수정",notes = "유저정보수정 PostMethod")
    @PutMapping("/editUser/{userId}")
    public ApiResponse editUser(@PathVariable Long userId,
                                @RequestBody UserDto.UserInfo userInfo){
        userService.editUser(userInfo,userId);
        return ApiResponse.success();
    }

}
