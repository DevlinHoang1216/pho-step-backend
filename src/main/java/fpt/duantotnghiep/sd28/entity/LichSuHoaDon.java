package fpt.duantotnghiep.sd28.entity;

import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Entity
@Table(name = "lich_su_hoa_don") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "hanh_dong", nullable = false, length = 100) // Thêm name
    private String hanhDong;

    @Column(name = "mo_ta_hanh_dong", columnDefinition = "NVARCHAR(MAX)") // Thêm name, đổi từ varchar(max) sang NVARCHAR(MAX)
    private String moTaHanhDong;

    @Column(name = "thoi_gian", nullable = false, updatable = false) // Thêm name, bỏ columnDefinition "datetime2"
    private LocalDateTime thoiGian;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don", nullable = false) // Sửa tên cột join theo DB là id_hoa_don
    private HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien_thuc_hien") // Sửa tên cột join theo DB là id_nhan_vien_thuc_hien
    private NhanVien nhanVienThucHien;

    @PrePersist
    protected void onCreate() {
        this.thoiGian = LocalDateTime.now();
    }
}