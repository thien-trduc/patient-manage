package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "`CHITIETXEPGIUONG`", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"`CTPHONGGIUONG_ID`", "`MABA`"}
        )
})
@IdClass(BenhAnXepGiuongID.class)
@Getter
@Setter
public class BenhAnXepGiuong implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "`CTPHONGGIUONG_ID`", columnDefinition = "INTEGER")
    @JsonManagedReference
    private PhongGiuong ctPhongGiuong;

    @Id
    @ManyToOne
    @JoinColumn(name = "`MABA`")
    @JsonManagedReference
    private BenhAn benhAn;

    @Column(name = "`NGAYTHUE`")
    private Date ngayThue;

    @Column(name = "`NGAYTRA`")
    private Date ngayTra;

    @Column(name = "`DONGIA`", columnDefinition = "DOUBLE PRECISION CHECK (`DONGIA` > 0)")
    private Float donGia;

}
