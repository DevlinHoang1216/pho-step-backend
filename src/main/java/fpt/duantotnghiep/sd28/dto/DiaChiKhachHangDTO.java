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
public class DiaChiKhachHangDTO {

    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @NotNull
    @Size(max = 100)
    private String tenNguoiNhan;

    @NotNull
    @Size(max = 20)
    private String soDienThoaiNguoiNhan;

    @NotNull
    @Size(max = 255)
    private String soNhaTenDuong;

    private String ghiChu;

    private Boolean laDiaChiMacDinh;

    private LocalDateTime ngayTao; // Đổi từ OffsetDateTime sang LocalDateTime

    private LocalDateTime ngayCapNhat; // Đổi từ OffsetDateTime sang LocalDateTime

    @NotNull
    private Long khachHang; // Sửa kiểu dữ liệu từ Integer sang Long

    @NotNull
    private Long phuongXa; // Sửa kiểu dữ liệu từ Integer sang Long
}