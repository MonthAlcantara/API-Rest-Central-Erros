package br.com.monthalcantara.projetofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredenciaisDTO implements Serializable {

    private String login;

    private String password;

}
