package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`BENHNHAN`")
@Getter
@Setter
public class BenhNhan extends BasePerson{
    @Id
    @Column(name = "`CMND`", columnDefinition = "VARCHAR(15)")
    private String cmnd;
    @Column(name ="`SODIENTHOAI`", columnDefinition = "VARCHAR(20)", unique = true, nullable = false)
    private String soDienThoai;
    @Column(name ="`EMAIL`", columnDefinition = "VARCHAR(100)", unique = true)
    private String email;
    @Column(name ="`DOITUONG`", columnDefinition = "VARCHAR(50)", nullable = false)
    private String doiTuong;
    @Column(name ="`BHYT`", columnDefinition = "VARCHAR(20)", unique = true, nullable = false)
    private String bhyt;
    @JsonBackReference
    @OneToMany(mappedBy = "benhNhan", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<BenhAn> benhAns = new ArrayList<>();
}
