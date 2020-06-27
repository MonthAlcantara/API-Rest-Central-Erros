package br.com.monthalcantara.projetofinal.dto;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO{

    @NotBlank(message = "Obrigatorio informar o login")
    private String login;
    @NotBlank(message = "Obrigatorio informar a senha")
    private String password;

    private boolean admin;

    public Usuario build(){
        return new Usuario()
                .setLogin(this.login)
                .setPassword(this.password)
                .setAdmin(this.admin);
    }

    public UsuarioDTO(Usuario usuario){
        this.login = usuario.getLogin();
        this.password = usuario.getPassword();
        this.admin = usuario.isAdmin();
    }
}
