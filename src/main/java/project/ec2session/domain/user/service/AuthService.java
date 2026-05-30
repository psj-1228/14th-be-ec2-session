package project.ec2session.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ec2session.common.exception.CustomException;
import project.ec2session.common.exception.ErrorCode;
import project.ec2session.common.jwt.JwtUtil;
import project.ec2session.domain.user.dto.TokenDto;
import project.ec2session.domain.user.dto.UserReq;
import project.ec2session.domain.user.entity.User;
import project.ec2session.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public Long signUp(UserReq.SignUpDto signUpDto) {
        User user =
                signUpDto.toEntity(passwordEncoder.encode(signUpDto.password()));

        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public TokenDto signIn(UserReq.SignInDto signInDto) {
        User user = userRepository.findByUsername(signInDto.username())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_INFO));

        if (!passwordEncoder.matches(signInDto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.USER_INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        return TokenDto.of(accessToken);
    }
}
