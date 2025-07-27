package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.util.Set;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "tinh_thanh") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TinhThanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_tinh_thanh", nullable = false, length = 100) // Thêm name
    private String tenTinhThanh;

    @Column(name = "ma_vung", length = 10) // Thêm name, có thể null
    private String maVung;

    @OneToMany(mappedBy = "tinhThanh", cascade = CascadeType.ALL, orphanRemoval = true) // Thêm cascade và orphanRemoval
    private Set<QuanHuyen> quanHuyens; // Đổi tên để rõ ràng hơn
}