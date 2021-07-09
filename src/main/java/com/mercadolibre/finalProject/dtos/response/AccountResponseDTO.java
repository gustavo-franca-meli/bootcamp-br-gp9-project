package com.mercadolibre.finalProject.dtos.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private String username;
    private String password;
    private String token;
    private CountryResponseDTO country;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountResponseDTO(String username, String password, CountryResponseDTO country) {
        this.username = username;
        this.password = password;
        this.country = country;
    }
}