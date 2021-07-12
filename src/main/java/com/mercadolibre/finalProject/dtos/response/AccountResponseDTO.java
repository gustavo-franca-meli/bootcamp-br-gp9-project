package com.mercadolibre.finalProject.dtos.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResponseDTO {
    private Long id;
    private String username;
    private String password;
    private String token;
    private Integer rol;
    private CountryResponseDTO country;

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

    public AccountResponseDTO(Long id, String username, String password, Integer rol, CountryResponseDTO country) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.country = country;
    }
}