package project.ec2session.domain.user.dto;

import project.ec2session.domain.user.entity.User;

public record UserRes(
        Long userId,
        String username,
        String nickname
) {
    public static UserRes from(User user) {
        return new UserRes(user.getId(), user.getUsername(), user.getNickname());
    }
}
