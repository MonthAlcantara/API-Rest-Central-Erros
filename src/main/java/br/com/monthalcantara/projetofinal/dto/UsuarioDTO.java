package br.com.monthalcantara.projetofinal.dto;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
