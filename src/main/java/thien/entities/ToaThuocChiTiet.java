package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "`CHITIETTOATHUOC`",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"`MATOA`", "`MATHUOC`"}
                )
        }
)
@IdClass(ToaThuocChiTietID.class)
@Getter
@Setter
public class ToaThuocChiTiet implements Serializable {

    @Id
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MATOA`")
    private ToaThuoc toaThuoc;

    @Id
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MATHUOC`")
    private Thuoc thuoc;

    @Column(name = "`SOLUONG`", columnDefinition = "INT CHECK (`SOLUONG` > 0)")
    private int soLuong;

    @Column(name = "`DONGIA`", columnDefinition = "DOUBLE PRECISION CHECK (`DONGIA` > 0)")
    private Float donGia;

    @Column(name = "`CACHDUNG`", columnDefinition = "VARCHAR(255)")
    private String cachDung;
}
