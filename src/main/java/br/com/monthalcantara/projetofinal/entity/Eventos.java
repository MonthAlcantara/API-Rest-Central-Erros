package br.com.monthalcantara.projetofinal.entity;

import br.com.monthalcantara.projetofinal.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Eventos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Level Level;
    private String descricaoDoEvento;
    private String logDoEvento;

    private String origem; //(Sistema ou Serviço que originou o evento) Criar enums

    @CreatedDate
    private LocalDateTime data;//(Data do evento),
    private Integer quantidade; //(Quantidade de Eventos de mesmo tipo)

}
