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
public class DanhGiaSanPhamDTO {

    private Long id; // Sửa kiểu dữ liệu từ Integer sang Long

    @NotNull
    private Integer soSao;

    private String noiDung;

    private LocalDateTime ngayDanhGia; // Đổi từ OffsetDateTime sang LocalDateTime

    @Size(max = 50)
    private String trangThai;

    private LocalDateTime ngayCapNhat; // Đổi từ OffsetDateTime sang LocalDateTime

    @NotNull
    private Long khachHang; // Sửa kiểu dữ liệu từ Integer sang Long

    @NotNull
    private Long sanPham; // Sửa kiểu dữ liệu từ Integer sang Long
}