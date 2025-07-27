package fpt.duantotnghiep.sd28.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import java.util.UUID; // Giữ lại UUID cho idHoaDonDaSuDung nếu bạn muốn ánh xạ trực tiếp
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "ma_giam_gia") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ma_code", nullable = false, length = 100, unique = true) // Thêm name và unique
    private String maCode;

    @Column(name = "da_su_dung", nullable = false) // Thêm name, nullable=false (DEFAULT 0)
    private Boolean daSuDung;

    @Column(name = "id_hoa_don_da_su_dung", columnDefinition = "uniqueidentifier") // Thêm name
    private UUID idHoaDonDaSuDung;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_su_dung") // Thêm name, có thể null trong DB
    private LocalDateTime ngaySuDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phieu_giam_gia", nullable = false) // Sửa tên cột join theo DB là id_phieu_giam_gia
    private PhieuGiamGia phieuGiamGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang_duoc_cap") // Sửa tên cột join theo DB là id_khach_hang_duoc_cap
    private KhachHang khachHangDuocCap;

    @OneToMany(mappedBy = "maGiamGia", cascade = CascadeType.ALL, orphanRemoval = true) // Thêm cascade và orphanRemoval
    private Set<HoaDon> hoaDons; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        if (this.daSuDung == null) {
            this.daSuDung = false; // Giá trị mặc định theo DB (DEFAULT 0)
        }
    }
}