package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="`NHANVIENUSER`")
@Getter
@Setter
public class NhanVienUser extends BaseEntity {
    @Id
    @Column(name = "`ID`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`USERNAME`")
    private String username;

    @Column(name = "`PASSWORD`")
    private String password;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MANV`")
    private NhanVien nhanVien;
}
