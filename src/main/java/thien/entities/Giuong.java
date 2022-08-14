package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`GIUONG`")
@Getter
@Setter
public class Giuong extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`MAGIUONG`")
    private Integer maGiuong;
    @Column(name = "`SOGIUONG`", columnDefinition = "VARCHAR(10)", nullable = false, unique = true)
    private String soGiuong;
    @JsonBackReference
    @OneToMany(mappedBy = "giuong", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<PhongGiuong> phongGiuongs = new ArrayList<>();
}
