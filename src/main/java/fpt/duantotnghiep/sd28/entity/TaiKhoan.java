package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "tai_khoan") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_dang_nhap", nullable = false, length = 100, unique = true) // Thêm name, thêm unique
    private String tenDangNhap;

    @Column(name = "mat_khau_hash", nullable = false, length = 255) // Thêm name, tăng độ dài
    private String matKhauHash;

    @Column(name = "email", length = 100, unique = true) // Thêm name, thêm unique
    private String email;

    @Column(name = "vai_tro", nullable = false, length = 50) // Thêm name
    private String vaiTro; // CHECK (vai_tro IN ('admin', 'nhan_vien', 'khach_hang'))

    @Column(name = "trang_thai", nullable = false) // Thêm name, nullable=false (DEFAULT 1)
    private Boolean trangThai;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NhanVien> nhanViens; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KhachHang> khachHangs; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.trangThai == null) {
            this.trangThai = true; // Giá trị mặc định theo DB (DEFAULT 1)
        }
        if (this.vaiTro == null) {
            this.vaiTro = "khach_hang"; // Giá trị mặc định (ví dụ)
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}