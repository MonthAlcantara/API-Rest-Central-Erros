package br.com.monthalcantara.projetofinal;

import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService usuarioService;

    @Test
    @DisplayName("Deve criar um usuario com sucesso")
    public void criaUsuario(){

        BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(null);
    }
}
