package fpt.duantotnghiep.sd28.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "mau_sac")
public class MauSac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_mau_sac", nullable = false)
    private String tenMauSac;

    @Column(name = "ma_mau_sac", nullable = false, unique = true)
    private String maMauSac;

    @Column(name = "hex") // Chỉ giữ cột HEX cho thông tin màu
    private String hex;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;


    @OneToMany(mappedBy = "mauSac", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude // Exclude from equals and hashCode
    private Set<ChiTietSanPham> chiTietSanPhams; // Assuming MauSac has a collection of ChiTiet

    // Nếu bạn có các cột khác trong bảng mau_sac mà không phải là 'hex',
    // ví dụ như 'trang_thai', hãy thêm vào đây tương ứng.
    // Ví dụ:
    // @Column(name = "trang_thai")
    // private String trangThai;
}