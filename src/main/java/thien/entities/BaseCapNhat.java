package thien.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseCapNhat extends BaseEntity {
    @Column(name = "`GIA`", columnDefinition = "DOUBLE PRECISION CHECK (`GIA` > 0)")
    protected Float gia;

    @Column(name = "`GHICHU`", columnDefinition = "VARCHAR(255)")
    protected String ghiChu;
}
