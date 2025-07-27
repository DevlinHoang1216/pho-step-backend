package fpt.duantotnghiep.sd28.entity;

import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.util.Set;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "quan_huyen") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuanHuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_quan_huyen", nullable = false, length = 100) // Thêm name
    private String tenQuanHuyen;

    @Column(name = "ma_huyen", length = 10) // Thêm name, có thể null
    private String maHuyen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tinh_thanh", nullable = false) // Sửa tên cột join theo DB là id_tinh_thanh
    private TinhThanh tinhThanh;

    @OneToMany(mappedBy = "quanHuyen", cascade = CascadeType.ALL, orphanRemoval = true) // Thêm cascade và orphanRemoval
    private Set<PhuongXa> phuongXas; // Đổi tên để rõ ràng hơn
}