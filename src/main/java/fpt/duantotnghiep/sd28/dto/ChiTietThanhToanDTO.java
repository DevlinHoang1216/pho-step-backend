package fpt.duantotnghiep.sd28.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import java.util.UUID;
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Data // Thay thế @Getter và @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietThanhToanDTO {

    private UUID id; // Giữ nguyên UUID

    @NotNull
    @Digits(integer = 20, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "13.08")
    private BigDecimal soTienThanhToan;

    private LocalDateTime thoiGianThanhToan; // Đổi từ OffsetDateTime sang LocalDateTime

    @Size(max = 50)
    private String trangThaiThanhToan;

    @Size(max = 255)
    private String maGiaoDichNganHang;

    @Size(max = 100)
    private String nganHangThanhToan;

    private String ghiChuThanhToan;

    private LocalDateTime ngayCapNhat; // Đổi từ OffsetDateTime sang LocalDateTime

    @NotNull
    private UUID hoaDon; // Giữ nguyên UUID

    @NotNull
    private Long phuongThucThanhToan; // Sửa kiểu dữ liệu từ Integer sang Long
}