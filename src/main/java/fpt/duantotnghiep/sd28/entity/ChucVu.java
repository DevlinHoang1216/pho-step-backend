package fpt.duantotnghiep.sd28.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chuc_vu") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChucVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa strategy từ SEQUENCE sang IDENTITY
    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @Column(name = "ten_chuc_vu", nullable = false, length = 100) // Thêm name
    private String tenChucVu;

    @Column(name = "ma_chuc_vu", nullable = false, length = 100, unique = true) // Thêm name và unique
    private String maChucVu;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @OneToMany(mappedBy = "chucVu", cascade = CascadeType.ALL, orphanRemoval = true) // Thêm cascade và orphanRemoval
    private Set<NhanVien> nhanViens; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}