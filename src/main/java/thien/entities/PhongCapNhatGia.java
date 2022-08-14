package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(
        name = "`CAPNHATGIAGIUONG`",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"`MAPHONG`", "`NGAYCAPNHAT`"}
                )
        }
)
@IdClass(PhongCapNhatGiaID.class)
@Getter
@Setter
public class PhongCapNhatGia extends BaseCapNhat implements Serializable {
    @Id
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MAPHONG`")
    private Phong phong;

    @Id
    @Column(name = "`NGAYCAPNHAT`")
    private Date ngayCapNhat;

}
