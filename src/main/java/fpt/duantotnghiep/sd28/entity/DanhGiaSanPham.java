package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "danh_gia_san_pham") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data // Bao gồm @Getter, @Setter, @EqualsAndHashCode, @ToString
@NoArgsConstructor // Constructor không đối số
@AllArgsConstructor // Constructor với tất cả đối số
@Builder // Builder pattern
public class DanhGiaSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "so_sao", nullable = false) // Thêm name
    private Integer soSao; // CHECK (so_sao BETWEEN 1 AND 5) - kiểm tra này thường xử lý ở tầng Service/Validation

    @Column(name = "noi_dung", columnDefinition = "NVARCHAR(MAX)") // Thêm name, đổi từ varchar(max) sang NVARCHAR(MAX)
    private String noiDung;

    @Column(name = "ngay_danh_gia", nullable = false, updatable = false) // Thêm name, bỏ columnDefinition "datetime2"
    private LocalDateTime ngayDanhGia;

    @Column(name = "trang_thai", length = 50, nullable = false) // Thêm name, nullable=false (DEFAULT 'dang_xu_ly')
    private String trangThai;

    @Column(name = "ngay_cap_nhat", nullable = false) // Thêm name, bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang", nullable = false) // Sửa tên cột join theo DB là id_khach_hang
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", nullable = false) // Sửa tên cột join theo DB là id_san_pham
    private SanPham sanPham;

    @PrePersist
    protected void onCreate() {
        this.ngayDanhGia = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.trangThai == null) {
            this.trangThai = "dang_xu_ly"; // Giá trị mặc định theo DB
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}