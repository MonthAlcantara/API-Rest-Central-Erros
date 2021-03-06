package br.com.monthalcantara.projetofinal.model;

import br.com.monthalcantara.projetofinal.enums.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@Table(name = "event_logs")
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "description")
    @Size(max = 100)
    private String descricao;

    @NotNull
    private String log;

    @Column(name = "origin")
    @Size(max = 100)
    @NotNull
    private String origem;

    @Column(name = "event_date", nullable = false)
    @JsonFormat
    @CreatedDate
    private LocalDateTime data;

    @Column(name = "quantity")
    private Integer quantidade;


}
