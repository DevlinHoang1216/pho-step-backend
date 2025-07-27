package fpt.duantotnghiep.sd28.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "chi_tiet_thanh_toan") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietThanhToan {

    @Id
    @GeneratedValue // Khi sử dụng UuidGenerator, @GeneratedValue không cần strategy
    @UuidGenerator // Tự động sinh UUID
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uniqueidentifier") // Thêm name
    private UUID id;

    @Column(name = "so_tien_thanh_toan", nullable = false, precision = 20, scale = 2) // Thêm name
    private BigDecimal soTienThanhToan;

    @Column(name = "thoi_gian_thanh_toan", nullable = false, updatable = false) // Thêm name, nullable=false (DEFAULT GETDATE())
    private LocalDateTime thoiGianThanhToan;

    @Column(name = "trang_thai_thanh_toan", length = 50, nullable = false) // Thêm name, nullable=false (DEFAULT 'PENDING')
    private String trangThaiThanhToan;

    @Column(name = "ma_giao_dich_ngan_hang", length = 255) // Thêm name, kiểm tra độ dài trong DB
    private String maGiaoDichNganHang;

    @Column(name = "ngan_hang_thanh_toan", length = 100) // Thêm name
    private String nganHangThanhToan;

    @Column(name = "ghi_chu_thanh_toan", columnDefinition = "NVARCHAR(MAX)") // Thêm name và NVARCHAR(MAX)
    private String ghiChuThanhToan;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don", nullable = false) // Sửa tên cột join theo DB là id_hoa_don
    private HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phuong_thuc_thanh_toan", nullable = false) // Sửa tên cột join theo DB là id_phuong_thuc_thanh_toan
    private PhuongThucThanhToan phuongThucThanhToan;

    @PrePersist
    protected void onCreate() {
        this.thoiGianThanhToan = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.trangThaiThanhToan == null) {
            this.trangThaiThanhToan = "PENDING"; // Giá trị mặc định theo DB (DEFAULT 'PENDING')
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}