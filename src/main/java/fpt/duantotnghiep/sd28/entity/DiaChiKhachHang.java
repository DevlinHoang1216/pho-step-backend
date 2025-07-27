package fpt.duantotnghiep.sd28.entity;
import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "dia_chi_khach_hang") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaChiKhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_nguoi_nhan", nullable = false, length = 100) // Thêm name
    private String tenNguoiNhan;

    @Column(name = "so_dien_thoai_nguoi_nhan", nullable = false, length = 20) // Thêm name
    private String soDienThoaiNguoiNhan;

    @Column(name = "so_nha_ten_duong", nullable = false, columnDefinition = "NVARCHAR(255)")
    // Thêm name, đổi từ varchar(max) sang NVARCHAR(255)
    private String soNhaTenDuong;

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)") // Thêm name và NVARCHAR(MAX)
    private String ghiChu;

    @Column(name = "la_dia_chi_mac_dinh", nullable = false) // Thêm name, nullable=false (DEFAULT 0)
    private Boolean laDiaChiMacDinh;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang", nullable = false) // Sửa tên cột join theo DB là id_khach_hang
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phuong_xa", nullable = false) // Sửa tên cột join theo DB là id_phuong_xa
    private PhuongXa phuongXa;

    @OneToMany(mappedBy = "diaChiGiaoHang", cascade = CascadeType.ALL, orphanRemoval = true)
    // Thêm cascade và orphanRemoval
    private Set<HoaDon> hoaDons; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.laDiaChiMacDinh == null) {
            this.laDiaChiMacDinh = false; // Giá trị mặc định theo DB (DEFAULT 0)
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}