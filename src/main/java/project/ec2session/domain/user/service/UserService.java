package project.ec2session.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ec2session.common.exception.CustomException;
import project.ec2session.common.exception.ErrorCode;
import project.ec2session.domain.user.dto.UserReq;
import project.ec2session.domain.user.dto.UserRes;
import project.ec2session.domain.user.entity.User;
import project.ec2session.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserRes readById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserRes.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserRes> readAll() {
        return userRepository.findAll().stream()
                .map(UserRes::from)
                .toList();
    }

    @Transactional
    public void update(Long id, UserReq.UpdateInfo updateInfo) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateInfo(updateInfo.nickname());
    }
}
