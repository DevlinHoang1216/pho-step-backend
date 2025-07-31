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

    // Trường này lưu URL hoặc đường dẫn tương đối của ảnh đại diện chung của sản phẩm
    // Frontend sẽ thêm baseUrl vào để hiển thị
    private String urlAnhDaiDien;

    @Size(max = 100, message = "Quốc gia sản xuất không được vượt quá 100 ký tự")
    private String quocGiaSanXuat;

    @Schema(description = "Tổng số lượng tồn kho từ chi tiết sản phẩm")
    private Long soLuongTonKho; // Sẽ được tính toán ở Backend

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayTao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "Danh mục không được để trống")
    private Long idDanhMuc; // ID của DanhMuc

    @NotNull(message = "ID thương hiệu không được để trống")
    private Long idThuongHieu;

    @NotNull(message = "Trạng thái không được để trống")
    private Long idTrangThai; // ID của TrangThai
    private String tenTrangThai; // Tên trạng thái để hiển thị

    // Danh sách các biến thể (ChiTietSanPham) sẽ được gửi từ Frontend
    // và xử lý tại Backend
    @Schema(description = "Danh sách các biến thể (ChiTietSanPham) của sản phẩm")
    private List<ChiTietSanPhamDTO> productDetails;

    // Constructor for JPQL query (đã sửa đổi để nhận Long idTrangThai và String tenTrangThai)
    public SanPhamDTO(Long id, String tenSanPham, String maSanPham, String moTaSanPham, String urlAnhDaiDien,
                      String quocGiaSanXuat, Long idTrangThai, String tenTrangThai, // Đã sửa đổi
                      Long soLuongTonKho, LocalDateTime ngayTao,
                      LocalDateTime ngayCapNhat, Long danhMuc, Long thuongHieu) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.maSanPham = maSanPham;
        this.moTaSanPham = moTaSanPham;
        this.urlAnhDaiDien = urlAnhDaiDien; // Gán giá trị urlAnhDaiDien nhận được từ query
        this.quocGiaSanXuat = quocGiaSanXuat;
        this.idTrangThai = idTrangThai;
        this.tenTrangThai = tenTrangThai;
        this.soLuongTonKho = soLuongTonKho;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.idDanhMuc = danhMuc;
        this.idThuongHieu = thuongHieu;
    }
}
