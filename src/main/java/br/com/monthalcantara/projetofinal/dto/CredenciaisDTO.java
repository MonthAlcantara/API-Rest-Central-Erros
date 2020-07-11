package br.com.monthalcantara.projetofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredenciaisDTO {

    private String login;

    private String password;

}
