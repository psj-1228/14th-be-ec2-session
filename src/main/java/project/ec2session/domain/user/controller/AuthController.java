package project.ec2session.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ec2session.domain.user.dto.TokenDto;
import project.ec2session.domain.user.dto.UserReq;
import project.ec2session.domain.user.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Auth API", description = "회원가입, 로그인 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController{
    private final AuthService authService;

    @Operation(summary = "로그인", description = "name, password, nickname을 입력 후 Token 리턴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "404", description = "로그인 실패")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(
            @RequestBody @Valid UserReq.SignInDto request
    ) {
        TokenDto response = authService.signIn(request);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "회원가입", description = "name, password, nickname을 입력 후 userId를 리턴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "404", description =  "회원가입 실패")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserReq.SignUpDto request) {
        Long userId = authService.signUp(request);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        return ResponseEntity.ok(response);
    }
}
