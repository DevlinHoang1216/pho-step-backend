package fpt.duantotnghiep.sd28.entity;

import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.time.LocalDate;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "khach_hang") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_khach_hang", nullable = false, length = 100) // Thêm name
    private String tenKhachHang;

    @Column(name = "so_dien_thoai", length = 20, unique = true) // Thêm name, thêm unique
    private String soDienThoai;

    @Column(name = "gioi_tinh", nullable = false) // Thêm name, nullable=false (DEFAULT 0)
    private Boolean gioiTinh;

    @Column(name = "ngay_sinh") // Thêm name
    private LocalDate ngaySinh;

    @Column(name = "ma_khach_hang", nullable = false, length = 20, unique = true) // Thêm name và unique
    private String maKhachHang;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tai_khoan", unique = true) // Sửa tên cột join theo DB là id_tai_khoan, thêm unique
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiaChiKhachHang> diaChiKhachHangs; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "khachHangDuocCap", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MaGiamGia> maGiamGiasDuocCap; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HoaDon> hoaDons; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GioHang> gioHangs; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DanhGiaSanPham> danhGiaSanPhams; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.gioiTinh == null) {
            this.gioiTinh = false; // Giá trị mặc định theo DB (DEFAULT 0)
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}