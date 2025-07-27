package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.math.BigDecimal;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "hoa_don") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {

    @Id
    @GeneratedValue // Khi sử dụng UuidGenerator, @GeneratedValue không cần strategy
    @UuidGenerator // Tự động sinh UUID
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uniqueidentifier") // Thêm name
    private UUID id;

    @Column(name = "ma_hoa_don", nullable = false, length = 50, unique = true) // Thêm name và unique
    private String maHoaDon;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_thanh_toan") // Thêm name, có thể null trong DB
    private LocalDateTime ngayThanhToan;

    @Column(name = "tong_tien_san_pham", nullable = false, precision = 20, scale = 2) // Thêm name
    private BigDecimal tongTienSanPham;

    @Column(name = "phi_van_chuyen", precision = 10, scale = 2) // Thêm name, DEFAULT 0 trong DB
    private BigDecimal phiVanChuyen;

    @Column(name = "tong_tien_thanh_toan", nullable = false, precision = 20, scale = 2) // Thêm name
    private BigDecimal tongTienThanhToan;

    @Column(name = "trang_thai_don_hang", length = 50, nullable = false) // Thêm name, nullable=false (DEFAULT 'cho_xac_nhan')
    private String trangThaiDonHang;

    @Column(name = "ma_van_don", length = 100, unique = true) // Thêm name, unique
    private String maVanDon;

    @Column(name = "ten_don_vi_van_chuyen", length = 100) // Thêm name
    private String tenDonViVanChuyen;

    @Column(name = "ngay_giao_hang_du_kien") // Thêm name, có thể null trong DB
    private LocalDateTime ngayGiaoHangDuKien;

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)") // Thêm name và NVARCHAR(MAX)
    private String ghiChu;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang") // Sửa tên cột join theo DB là id_khach_hang
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien_tao") // Sửa tên cột join theo DB là id_nhan_vien_tao
    private NhanVien nhanVienTao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ma_giam_gia") // Sửa tên cột join theo DB là id_ma_giam_gia
    private MaGiamGia maGiamGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dia_chi_giao_hang", nullable = false) // Sửa tên cột join theo DB là id_dia_chi_giao_hang
    private DiaChiKhachHang diaChiGiaoHang;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HoaDonChiTiet> hoaDonChiTiets; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChiTietThanhToan> chiTietThanhToans; // Đổi tên để rõ ràng hơn

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LichSuHoaDon> lichSuHoaDons; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.trangThaiDonHang == null) {
            this.trangThaiDonHang = "cho_xac_nhan"; // Giá trị mặc định theo DB
        }
        if (this.phiVanChuyen == null) {
            this.phiVanChuyen = BigDecimal.ZERO; // Giá trị mặc định theo DB
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}