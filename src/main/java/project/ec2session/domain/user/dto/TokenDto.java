package project.ec2session.domain.user.dto;

public record TokenDto(
        String accessToken
) {
    public static TokenDto of(String accessToken) {
        return new TokenDto(accessToken);
    }
}
