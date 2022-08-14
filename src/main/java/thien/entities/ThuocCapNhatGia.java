package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(
        name = "`CAPNHATGIATHUOC`",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"`MATHUOC`", "`NGAYCAPNHAT`"}
                )
        }
)
@IdClass(ThuocCapNhatGiaID.class)
@Getter
@Setter
public class ThuocCapNhatGia extends BaseCapNhat implements Serializable {
    @Id
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "`MATHUOC`")
    private Thuoc thuoc;

    @Id
    @Column(name = "`NGAYCAPNHAT`")
    private Date ngayCapNhat;
}
