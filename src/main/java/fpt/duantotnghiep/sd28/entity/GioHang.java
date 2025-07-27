package fpt.duantotnghiep.sd28.entity;


import jakarta.persistence.*; // Import tất cả từ jakarta.persistence
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "gio_hang") // Chỉ định tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GioHang {

    @Id
    @GeneratedValue // Khi sử dụng UuidGenerator, @GeneratedValue không cần strategy
    @UuidGenerator // Tự động sinh UUID
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uniqueidentifier") // Thêm name
    private UUID id;

    @Column(name = "ma_phien_gio_hang", length = 255, unique = true) // Thêm name, kiểm tra độ dài, thêm unique
    private String maPhienGioHang;

    @Column(name = "ngay_tao", nullable = false, updatable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false) // Bỏ columnDefinition "datetime2"
    private LocalDateTime ngayCapNhat;

    @Column(name = "trang_thai", length = 50, nullable = false) // Thêm name, nullable=false (DEFAULT 'hoat_dong')
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang") // Sửa tên cột join theo DB là id_khach_hang
    private KhachHang khachHang;

    @OneToMany(mappedBy = "gioHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GioHangChiTiet> gioHangChiTiets; // Đổi tên để rõ ràng hơn

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        if (this.trangThai == null) {
            this.trangThai = "hoat_dong"; // Giá trị mặc định theo DB
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}