package minipj.placepic_core.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Service.AuthenticationService;
import minipj.placepic_core.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "로그인/회원가입 테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final UserService userService;
    //회원가입
    @ApiOperation(value="회원가입",notes = "@RequestBody를 활용한 회원가입 Post Method")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@ApiParam(value = "User object") @RequestBody User user){
            //아이디 중복확인 검사
        if(userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //유저 테이블에 저장
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }
    @ApiOperation(value="로그인요청",notes = "@RequestBody를 활용한 로그인요청 Post Method")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@ApiParam(value = "User object") @RequestBody User user){
        return new ResponseEntity<>(
                authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }//로그인 유저 객체에 토큰과 함께 정보 저장하여 반환
}
