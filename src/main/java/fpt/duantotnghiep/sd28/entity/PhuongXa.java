package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.util.Set;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "phuong_xa") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhuongXa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_phuong_xa", nullable = false, length = 100) // Thêm name
    private String tenPhuongXa;

    @Column(name = "ma_xa", length = 10) // Thêm name, có thể null
    private String maXa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_quan_huyen", nullable = false) // Sửa tên cột join theo DB là id_quan_huyen
    private QuanHuyen quanHuyen;

    @OneToMany(mappedBy = "phuongXa", cascade = CascadeType.ALL, orphanRemoval = true) // Thêm cascade và orphanRemoval
    private Set<DiaChiKhachHang> diaChiKhachHangs; // Đổi tên để rõ ràng hơn
}