package fpt.duantotnghiep.sd28.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
public class GioHangChiTietDTO {

    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @NotNull
    private Integer soLuong;

    @NotNull
    @Digits(integer = 20, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "94.08")
    private BigDecimal giaBanHienTai;

    private LocalDateTime ngayThemVao; // Đổi từ OffsetDateTime sang LocalDateTime

    @NotNull
    private UUID gioHang; // Giữ nguyên UUID

    @NotNull
    private UUID chiTietSp; // Giữ nguyên UUID
}