package thien.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @CreatedDate
    @Column(name = "`createdAt`")
    protected Date createdAt;

    @LastModifiedDate
    @Column(name = "`updatedAt`")
    protected Date updatedAt;

    @Column(name = "`deletedAt`")
    protected Date deletedAt;

}
