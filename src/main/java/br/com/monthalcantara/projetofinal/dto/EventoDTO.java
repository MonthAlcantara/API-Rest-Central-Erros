package br.com.monthalcantara.projetofinal.dto;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private Level level;
    private String descricao;
    private String origem;
    private LocalDateTime data;
    private Integer quantidade;

    public Evento build() {
        return Evento.builder()
                .level(this.level)
                .descricao(this.descricao)
                .data(this.data)
                .origem(this.origem)
                .quantidade(this.quantidade)
                .build();
    }

    public EventoDTO(Evento evento) {
        this.level = evento.getLevel();
        this.descricao = evento.getDescricao();
        this.data = evento.getData();
        this.origem = evento.getOrigem();
        this.quantidade = evento.getQuantidade();
    }

}
