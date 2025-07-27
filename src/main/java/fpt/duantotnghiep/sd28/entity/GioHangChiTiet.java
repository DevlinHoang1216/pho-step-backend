package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.math.BigDecimal;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "gio_hang_chi_tiet") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GioHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "so_luong", nullable = false) // Thêm name
    private Integer soLuong; // CHECK (so_luong > 0) - kiểm tra này thường xử lý ở tầng Service/Validation

    @Column(name = "gia_ban_hien_tai", nullable = false, precision = 20, scale = 2) // Thêm name
    private BigDecimal giaBanHienTai;

    @Column(name = "ngay_them_vao", nullable = false, updatable = false) // Thêm name, bỏ columnDefinition "datetime2"
    private LocalDateTime ngayThemVao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gio_hang", nullable = false) // Sửa tên cột join theo DB là id_gio_hang
    private GioHang gioHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_sp", nullable = false) // Sửa tên cột join theo DB là id_chi_tiet_sp
    private ChiTietSanPham chiTietSp;

    // CONSTRAINT UQ_GioHangChiTiet UNIQUE (id_gio_hang, id_chi_tiet_sp) - Unique constraint cho cặp khóa ngoại
    // có thể thêm @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_gio_hang", "id_chi_tiet_sp"})) vào Entity

    @PrePersist
    protected void onCreate() {
        this.ngayThemVao = LocalDateTime.now();
    }
}