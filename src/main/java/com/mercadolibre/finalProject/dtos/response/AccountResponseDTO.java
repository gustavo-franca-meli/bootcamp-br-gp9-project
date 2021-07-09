package com.mercadolibre.finalProject.dtos.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private Long id;
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

    public AccountResponseDTO(Long id, String username, String password, CountryResponseDTO country) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.country = country;
    }

    public AccountResponseDTO(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }
}