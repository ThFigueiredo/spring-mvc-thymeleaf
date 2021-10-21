package curso.springboot.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {


    @CreatedDate
    @Column(name = "DT_CRIACAO", updatable = false)
    protected LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "DT_MODIFICACAO")
    protected LocalDateTime dataModificacao;

    @Column(name = "CPF_CRIADO_POR", updatable = false)
    @CreatedBy
    protected String criadoPor;

    @Column(name = "CPF_MODIFICADO_POR")
    @LastModifiedBy
    protected String modificadoPor;
}