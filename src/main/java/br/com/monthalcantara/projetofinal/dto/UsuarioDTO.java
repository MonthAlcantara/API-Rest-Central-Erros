package br.com.monthalcantara.projetofinal.dto;

import br.com.monthalcantara.projetofinal.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO implements Serializable {

    @NotBlank(message = "Obrigatório informar o login")
    private String login;
    @NotBlank(message = "Obrigatório informar a senha")
    private String password;

    private boolean admin;

    public Usuario toModel() {
        return new Usuario().builder()
                .login(this.login)
                .password(this.password)
                .admin(this.admin)
                .build();
    }

    public UsuarioDTO(Usuario usuario) {
        this.login = usuario.getLogin();
        this.password = usuario.getPassword();
        this.admin = usuario.isAdmin();
    }
}
