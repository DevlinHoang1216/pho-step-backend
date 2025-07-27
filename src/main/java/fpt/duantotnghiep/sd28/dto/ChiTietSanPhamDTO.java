package fpt.duantotnghiep.sd28.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietSanPhamDTO {

    private UUID id; // ID của ChiTietSanPham (có thể null khi thêm mới)

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
    private Integer soLuongTonKho;

    @Size(max = 500, message = "Mô tả chi tiết không được vượt quá 500 ký tự")
    private String moTaChiTiet;

    @NotNull(message = "Giá nhập không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá nhập phải lớn hơn hoặc bằng 0")
    private BigDecimal giaNhap;

    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá bán phải lớn hơn hoặc bằng 0")
    private BigDecimal giaBan;

    @NotBlank(message = "Mã chi tiết sản phẩm không được để trống")
    @Size(max = 50, message = "Mã chi tiết sản phẩm không được vượt quá 50 ký tự")
    private String maCtsp;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayNhap;

    @Schema(description = "Trạng thái chi tiết sản phẩm: Đang Bán, Ngừng Kinh Doanh, Hết Hàng")
    @Size(max = 50, message = "Trạng thái không được vượt quá 50 ký tự")
    private String trangThaiSanPhamRieng; // Trạng thái riêng của chi tiết sản phẩm

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayTao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayCapNhat;

    @NotNull(message = "Chất liệu không được để trống")
    private Long chatLieu; // ID của ChatLieu
    private String tenChatLieu; // Tên chất liệu để hiển thị

    @NotNull(message = "Màu sắc không được để trống")
    private Long mauSac; // ID của MauSac
    private String tenMauSac; // Tên màu sắc để hiển thị

    @NotNull(message = "Kích cỡ không được để trống")
    private Long kichCo; // ID của KichCo
    private String tenKichCo; // Tên kích cỡ để hiển thị

    // ID của SanPham cha mà chi tiết này thuộc về
    // Frontend sẽ không gửi trường này khi thêm mới (vì nó chưa có)
    // Backend sẽ gán nó sau khi SanPham cha được lưu
    private Long sanPham;
    private String tenSanPham; // Tên sản phẩm để hiển thị
    private String urlAnhDaiDien; // URL ảnh đại diện của sản phẩm gốc để hiển thị

    private Long thuongHieu; // ID của ThuongHieu
    private String tenThuongHieu; // Tên thương hiệu để hiển thị

    private Long danhMuc; // ID của DanhMuc
    private String tenDanhMuc; // Tên danh mục để hiển thị

    // Danh sách URL ảnh của biến thể này (đã được upload từ Frontend)
    private List<String> images;

    // Constructor cho JPQL query (giữ nguyên nếu bạn vẫn dùng)
    public ChiTietSanPhamDTO(UUID id, Integer soLuongTonKho, String moTaChiTiet, BigDecimal giaNhap, BigDecimal giaBan,
                             String maCtsp, LocalDateTime ngayNhap, String trangThaiSanPhamRieng,
                             LocalDateTime ngayTao, LocalDateTime ngayCapNhat,
                             Long chatLieu, String tenChatLieu, Long mauSac, String tenMauSac, Long kichCo, String tenKichCo,
                             Long sanPham, String tenSanPham, String urlAnhDaiDien, Long thuongHieu, String tenThuongHieu, Long danhMuc, String tenDanhMuc) {
        this.id = id;
        this.soLuongTonKho = soLuongTonKho;
        this.moTaChiTiet = moTaChiTiet;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.maCtsp = maCtsp;
        this.ngayNhap = ngayNhap;
        this.trangThaiSanPhamRieng = trangThaiSanPhamRieng;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.chatLieu = chatLieu;
        this.tenChatLieu = tenChatLieu;
        this.mauSac = mauSac;
        this.tenMauSac = tenMauSac;
        this.kichCo = kichCo;
        this.tenKichCo = tenKichCo;
        this.sanPham = sanPham;
        this.tenSanPham = tenSanPham;
        this.urlAnhDaiDien = urlAnhDaiDien;
        this.thuongHieu = thuongHieu;
        this.tenThuongHieu = tenThuongHieu;
        this.tenDanhMuc = tenDanhMuc;
    }
}
