package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(
        name = "`CAPNHATGIADICHVU`",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"`MADV`", "`NGAYCAPNHAT`"}
                )
        }
)
@IdClass(DichVuCapNhatGiaID.class)
@Getter
@Setter
public class DichVuCapNhatGia extends BaseCapNhat implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "`MADV`")
    @JsonManagedReference
    private DichVu dichVu;
    @Id
    @Column(name = "`NGAYCAPNHAT`")
    private Date ngayCapNhat;
}
