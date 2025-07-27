package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.math.BigDecimal;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "hoa_don_chi_tiet") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "so_luong", nullable = false) // Thêm name
    private Integer soLuong; // CHECK (so_luong > 0) - kiểm tra này thường xử lý ở tầng Service/Validation

    @Column(name = "don_gia", nullable = false, precision = 20, scale = 2) // Thêm name
    private BigDecimal donGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don", nullable = false) // Sửa tên cột join theo DB là id_hoa_don
    private HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_sp", nullable = false) // Sửa tên cột join theo DB là id_chi_tiet_sp
    private ChiTietSanPham chiTietSp;

    // CONSTRAINT UQ_HoaDonChiTiet UNIQUE (id_hoa_don, id_chi_tiet_sp)
    // Có thể thêm @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_hoa_don", "id_chi_tiet_sp"})) vào Entity nếu cần
}