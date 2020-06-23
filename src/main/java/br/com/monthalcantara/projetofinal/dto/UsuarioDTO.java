package br.com.monthalcantara.projetofinal.dto;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO{

    private String login;
    private String password;

    public Usuario build(){
        return new Usuario()
                .setLogin(this.login)
                .setPassword(this.password);
    }

    public UsuarioDTO(Usuario usuario){
        this.login = usuario.getLogin();
        this.password = usuario.getPassword();
    }
}
