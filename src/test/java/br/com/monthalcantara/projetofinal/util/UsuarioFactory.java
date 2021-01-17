package br.com.monthalcantara.projetofinal.util;

import br.com.monthalcantara.projetofinal.model.Usuario;

public class UsuarioFactory {

    public static Usuario geraAdminNaoSalvo(){
        return Usuario
                .builder()
                .login("Teste")
                .password("123")
                .admin(true)
                .build();
    }

    public static Usuario geraUserNaoSalvo(){
        return Usuario
                .builder()
                .login("Teste")
                .password("123")
                .admin(false)
                .build();
    }

    public static Usuario geraUserSalvo(){
        return Usuario
                .builder()
                .id(1L)
                .login("Teste")
                .password("123")
                .admin(false)
                .build();
    }

    public static Usuario geraAdminSalvo(){
        return Usuario
                .builder()
                .id(1L)
                .login("Teste")
                .password("123")
                .admin(true)
                .build();
    }
}
