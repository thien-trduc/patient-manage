package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "`CHITIETKHAM`",
        uniqueConstraints = {
            @UniqueConstraint(
                    columnNames = {"`MABA`", "`MABS`", "`MAYTA`", "`NGAYKHAM`" }
            )
        }
)
@Getter
@Setter
public class BenhAnKham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`CTKHAM_ID`")
    private Integer id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MABA`", nullable = false)
    private BenhAn benhAn;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MABS`", nullable = false)
    private NhanVien bacSi;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MAYTA`")
    private NhanVien yta;

    @Column(name = "`NGAYKHAM`", nullable = false)
    private Date ngayKham;

    @Column(name = "`CHANDOAN`", columnDefinition = "VARCHAR(1000)", nullable = false)
    private String chanDoan;

    @Column(name = "`TINHTRANG`", columnDefinition = "VARCHAR(50)", nullable = false)
    private String tinhTrang;

}
