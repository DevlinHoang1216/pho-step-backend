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
public class PhieuGiamGiaDTO {

    private UUID id;

    @NotNull
    @Size(max = 100)
    private String maPhieuGiamGia;

    @NotNull
    @Size(max = 100)
    private String tenPhieuGiamGia;

    @NotNull
    @Size(max = 50)
    private String loaiGiamGia;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "90.08")
    private BigDecimal giaTriGiam;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "84.08")
    private BigDecimal hoaDonToiThieu;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "71.08")
    private BigDecimal soTienGiamToiDa;

    @NotNull
    private LocalDateTime ngayBatDau;

    @NotNull
    private LocalDateTime ngayKetThuc;

    @NotNull
    @Size(max = 50)
    private String trangThaiPhieu;

    @NotNull
    @Size(max = 50)
    private String loaiApDung;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private Long nhanVienTao; // Đã sửa từ UUID thành Long
}