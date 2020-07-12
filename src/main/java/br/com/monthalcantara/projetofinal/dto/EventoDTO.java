package br.com.monthalcantara.projetofinal.dto;

import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
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
        return new Evento()
                .setLevel(this.level)
                .setDescricao(this.descricao)
                .setData(this.data)
                .setOrigem(this.origem)
                .setQuantidade(this.quantidade);
    }

    public EventoDTO(Evento evento){
        this.level = evento.getLevel();
        this.descricao = evento.getDescricao();
        this.data = evento.getData();
        this.origem = evento.getOrigem();
        this.quantidade = evento.getQuantidade();
    }

}
