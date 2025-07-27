package fpt.duantotnghiep.sd28.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class SanPhamDTO {
    private Long id; // ID của SanPham, có thể null khi thêm mới

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String tenSanPham;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    @Size(max = 50, message = "Mã sản phẩm không được vượt quá 50 ký tự")
    private String maSanPham;

    private String moTaSanPham;

    private String urlAnhDaiDien; // URL ảnh đại diện chung của sản phẩm (nếu có)

    @Size(max = 100, message = "Quốc gia sản xuất không được vượt quá 100 ký tự")
    private String quocGiaSanXuat;

    @Schema(description = "Trạng thái sản phẩm: dang_kinh_doanh, ngung_kinh_doanh, het_hang")
    private String trangThai; // Trạng thái của sản phẩm chung

    @Schema(description = "Tổng số lượng tồn kho từ chi tiết sản phẩm")
    private Long soLuongTonKho; // Sẽ được tính toán ở Backend

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayTao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "Danh mục không được để trống")
    private Long danhMuc; // ID của DanhMuc

    @NotNull(message = "Thương hiệu không được để trống")
    private Long thuongHieu; // ID của ThuongHieu

    // Danh sách các biến thể (ChiTietSanPham) sẽ được gửi từ Frontend
    // và xử lý tại Backend
    @Schema(description = "Danh sách các biến thể (ChiTietSanPham) của sản phẩm")
    private List<ChiTietSanPhamDTO> productDetails;

    // Constructor for JPQL query (giữ nguyên nếu bạn vẫn dùng)
    public SanPhamDTO(Long id, String tenSanPham, String maSanPham, String moTaSanPham, String urlAnhDaiDien,
                      String quocGiaSanXuat, String trangThai, Long soLuongTonKho, LocalDateTime ngayTao,
                      LocalDateTime ngayCapNhat, Long danhMuc, Long thuongHieu) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.maSanPham = maSanPham;
        this.moTaSanPham = moTaSanPham;
        this.urlAnhDaiDien = urlAnhDaiDien;
        this.quocGiaSanXuat = quocGiaSanXuat;
        this.trangThai = trangThai;
        this.soLuongTonKho = soLuongTonKho;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.danhMuc = danhMuc;
        this.thuongHieu = thuongHieu;
    }
}
