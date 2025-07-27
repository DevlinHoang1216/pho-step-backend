package fpt.duantotnghiep.sd28.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime; // Đổi từ OffsetDateTime sang LocalDateTime
import lombok.AllArgsConstructor; // Thêm AllArgsConstructor
import lombok.Builder; // Thêm Builder
import lombok.Data; // Thay thế Getter và Setter bằng Data
import lombok.NoArgsConstructor; // Thêm NoArgsConstructor


@Data // Thay thế @Getter và @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChucVuDTO {

    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @NotNull
    @Size(max = 100)
    private String tenChucVu;

    @NotNull
    @Size(max = 100)
    private String maChucVu;

    private LocalDateTime ngayTao; // Đổi từ OffsetDateTime sang LocalDateTime

    private LocalDateTime ngayCapNhat; // Đổi từ OffsetDateTime sang LocalDateTime
}