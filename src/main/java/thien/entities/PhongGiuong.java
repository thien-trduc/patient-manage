package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "`CHITIETPHONGGIUONG`",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"`MAPHONG`", "`MAGIUONG`"}
                )
        }
)
@Getter
@Setter
public class PhongGiuong implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`CTPHONGGIUONG_ID`")
    private Integer id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MAPHONG`")
    private Phong phong;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MAGIUONG`")
    private Giuong giuong;

    @Column(name = "`TRANGTHAI`")
    private boolean trangThai;

    @JsonBackReference
    @OneToMany(mappedBy = "ctPhongGiuong", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<BenhAnXepGiuong> benhAnXepGiuongs = new ArrayList<>();
}
