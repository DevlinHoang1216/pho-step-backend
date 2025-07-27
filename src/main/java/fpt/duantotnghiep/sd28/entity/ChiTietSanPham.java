package fpt.duantotnghiep.sd28.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode; // Import this
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "chi_tiet_san_pham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietSanPham {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "so_luong_ton_kho", nullable = false)
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
    private Integer soLuongTonKho;

    @Column(name = "mo_ta_chi_tiet", length = 500)
    @Size(max = 500, message = "Mô tả chi tiết không được vượt quá 500 ký tự")
    private String moTaChiTiet;

    @Column(name = "gia_nhap", precision = 20, scale = 2, nullable = false)
    private BigDecimal giaNhap;

    @Column(name = "gia_ban", precision = 20, scale = 2, nullable = false)
    private BigDecimal giaBan;

    @Column(name = "ma_ctsp", length = 50, nullable = false, unique = true)
    @NotBlank(message = "Mã chi tiết sản phẩm không được để trống")
    @Size(max = 50, message = "Mã chi tiết sản phẩm không được vượt quá 50 ký tự")
    private String maCtsp;

    @Column(name = "ngay_nhap", nullable = false)
    private LocalDateTime ngayNhap;

    @Column(name = "trang_thai_san_pham_rieng", length = 50, nullable = false)
    @Size(max = 50, message = "Trạng thái không được vượt quá 50 ký tự")
    private String trangThaiSanPhamRieng; // "dang_kinh_doanh", "ngung_kinh_doanh", "het_hang"

    @Column(name = "ngay_tao", nullable = false, updatable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat", nullable = false)
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat_lieu", nullable = false)
    @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    private ChatLieu chatLieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mau_sac", nullable = false)
    @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    private MauSac mauSac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kich_co", nullable = false)
    @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    private KichCo kichCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", nullable = false)
    @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    private SanPham sanPham;

    @OneToMany(mappedBy = "chiTietSp", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    private Set<AnhSanPham> anhSanPhams;

    // Các mối quan hệ khác (HoaDonChiTiet, GioHangChiTiet) nếu có, đã được comment
    // để tránh lỗi "Invalid object name" nếu bảng chưa tồn tại hoặc ánh xạ sai.
    // @OneToMany(mappedBy = "chiTietSp", cascade = CascadeType.ALL, orphanRemoval = true)
    // @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    // private Set<HoaDonChiTiet> hoaDonChiTiets;

    // @OneToMany(mappedBy = "chiTietSp", cascade = CascadeType.ALL, orphanRemoval = true)
    // @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    // private Set<GioHangChiTiet> gioHangChiTiets;

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
        this.ngayNhap = LocalDateTime.now();
        if (this.trangThaiSanPhamRieng == null || !this.trangThaiSanPhamRieng.matches("dang_kinh_doanh|ngung_kinh_doanh|het_hang")) {
            this.trangThaiSanPhamRieng = "dang_kinh_doanh";
        }
        updateTrangThaiBasedOnSoLuong();
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
        updateTrangThaiBasedOnSoLuong();
    }

    // Phương thức này sẽ được gọi tự động khi soLuongTonKho thay đổi
    // Đảm bảo trạng thái "het_hang" được cập nhật tự động
    private void updateTrangThaiBasedOnSoLuong() {
        if (this.soLuongTonKho != null && this.soLuongTonKho == 0) {
            this.trangThaiSanPhamRieng = "het_hang";
        } else if (this.soLuongTonKho != null && this.soLuongTonKho > 0 && this.trangThaiSanPhamRieng.equals("het_hang")) {
            // Nếu số lượng tồn kho > 0 và trạng thái đang là "hết hàng", chuyển về "đang kinh doanh"
            this.trangThaiSanPhamRieng = "dang_kinh_doanh";
        }
        // Giữ nguyên trạng thái nếu nó là "ngung_kinh_doanh" hoặc nếu số lượng > 0 và trạng thái đã là "dang_kinh_doanh"
    }

    // Getter và Setter cho các trường nếu không dùng @Data
}
