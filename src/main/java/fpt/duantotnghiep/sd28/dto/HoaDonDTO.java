package fpt.duantotnghiep.sd28.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoaDonDTO {

    private UUID id;

    @NotNull
    @Size(max = 50)
    private String maHoaDon;

    @NotNull
    private LocalDateTime ngayTao;

    private LocalDateTime ngayThanhToan;

    @NotNull
    @Digits(integer = 20, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "98.08")
    private BigDecimal tongTienSanPham;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "52.08")
    private BigDecimal phiVanChuyen;

    @NotNull
    @Digits(integer = 20, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "35.08")
    private BigDecimal tongTienThanhToan;

    @NotNull
    @Size(max = 50)
    private String trangThaiDonHang;

    @Size(max = 100)
    private String maVanDon;

    @Size(max = 100)
    private String tenDonViVanChuyen;

    private LocalDateTime ngayGiaoHangDuKien;

    private String ghiChu;

    @NotNull
    private LocalDateTime ngayCapNhat;

    private Long khachHang;

    private Long nhanVienTao;

    private Long maGiamGia;

    @NotNull
    private Long diaChiGiaoHang;
}