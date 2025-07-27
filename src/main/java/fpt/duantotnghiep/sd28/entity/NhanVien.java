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
@Table(name = "nhan_vien") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_nhan_vien", nullable = false, length = 100) // Thêm name
    private String tenNhanVien;

    @Column(name = "ngay_sinh") // Thêm name
    private LocalDate ngaySinh;

    @Column(name = "gioi_tinh", nullable = false) // Thêm name, nullable=false (DEFAULT 0)
    private Boolean gioiTinh;

    @Column(name = "so_dien_thoai", length = 20, unique = true) // Thêm name, thêm unique
    private String soDienThoai;

    @Column(name = "ma_nhan_vien", nullable = false, length = 10, unique = true) // Thêm name và unique
    private String maNhanVien;

    @Column(name = "cccd", length = 20, unique = true) // Thêm name, thêm unique
    private String cccd;

    @Column(name = "dia_chi_so_nha_ten_duong", columnDefinition = "NVARCHAR(255)") // Thêm name, đổi sang NVARCHAR
    private String diaChiSoNhaTenDuong;

    @Column(name = "dia_chi_phuong_xa", length = 100) // Thêm name
    private String diaChiPhuongXa;

    @Column(name = "dia_chi_quan_huyen", length = 100) // Thêm name
    private String diaChiQuanHuyen;

    @Column(name = "dia_chi_tinh_thanh", length = 100) // Thêm name
    private String diaChiTinhThanh;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @Column(name = "trang_thai", nullable = false) // Thêm name, nullable=false (DEFAULT 1)
    private Boolean trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tai_khoan", unique = true) // Sửa tên cột join theo DB là id_tai_khoan, thêm unique
    private TaiKhoan taiKhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chuc_vu", nullable = false) // Sửa tên cột join theo DB là id_chuc_vu
    private ChucVu chucVu;

    @OneToMany(mappedBy = "nhanVienTao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhieuGiamGia> phieuGiamGiasTao; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "nhanVienTao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HoaDon> hoaDonsTao; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "nhanVienThucHien", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LichSuHoaDon> lichSuHoaDonsThucHien; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.gioiTinh == null) {
            this.gioiTinh = false; // Giá trị mặc định theo DB (DEFAULT 0)
        }
        if (this.trangThai == null) {
            this.trangThai = true; // Giá trị mặc định theo DB (DEFAULT 1)
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}