package seniorproject.models.dto;

public class AuthResponseDto {

    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public String getAccessToken(){
        return accessToken;
    }
}