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
@Table(name = "phieu_giam_gia") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuGiamGia {

    @Id
    @GeneratedValue // Khi sử dụng UuidGenerator, @GeneratedValue không cần strategy
    @UuidGenerator // Tự động sinh UUID
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uniqueidentifier") // Thêm name
    private UUID id;

    @Column(name = "ma_phieu_giam_gia", nullable = false, length = 100, unique = true) // Thêm name và unique
    private String maPhieuGiamGia;

    @Column(name = "ten_phieu_giam_gia", nullable = false, length = 100) // Thêm name
    private String tenPhieuGiamGia;

    @Column(name = "loai_giam_gia", nullable = false, length = 50) // Thêm name
    private String loaiGiamGia; // CHECK (loai_giam_gia IN ('phan_tram', 'tien_mat'))

    @Column(name = "gia_tri_giam", nullable = false, precision = 10, scale = 2) // Thêm name
    private BigDecimal giaTriGiam; // CHECK (gia_tri_giam > 0)

    @Column(name = "hoa_don_toi_thieu", precision = 10, scale = 2) // Thêm name, DEFAULT 0 trong DB
    private BigDecimal hoaDonToiThieu;

    @Column(name = "so_tien_giam_toi_da", precision = 10, scale = 2) // Thêm name
    private BigDecimal soTienGiamToiDa;

    @Column(name = "ngay_bat_dau", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayKetThuc;

    @Column(name = "trang_thai_phieu", length = 50, nullable = false) // Thêm name, nullable=false (DEFAULT 'hoat_dong')
    private String trangThaiPhieu; // CHECK (trang_thai_phieu IN ('hoat_dong', 'het_han', 'da_huy', 'sap_dien_ra'))

    @Column(name = "loai_ap_dung", length = 50, nullable = false) // Thêm name, nullable=false (DEFAULT 'tat_ca')
    private String loaiApDung; // CHECK (loai_ap_dung IN ('tat_ca', 'danh_muc', 'san_pham'))

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien_tao") // Sửa tên cột join theo DB là id_nhan_vien_tao
    private NhanVien nhanVienTao;

    @OneToMany(mappedBy = "phieuGiamGia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MaGiamGia> maGiamGias; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.hoaDonToiThieu == null) {
            this.hoaDonToiThieu = BigDecimal.ZERO; // Giá trị mặc định theo DB
        }
        if (this.trangThaiPhieu == null) {
            this.trangThaiPhieu = "sap_dien_ra"; // Giá trị mặc định ban đầu có thể là 'sap_dien_ra' hoặc 'hoat_dong' tùy logic
        }
        if (this.loaiApDung == null) {
            this.loaiApDung = "tat_ca"; // Giá trị mặc định theo DB
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}