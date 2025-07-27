package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LichSuHoaDonDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String hanhDong;

    private String moTaHanhDong;

    private LocalDateTime thoiGian;

    @NotNull
    private UUID hoaDon; // OK: HoaDonRepository dùng UUID

    private Long nhanVienThucHien; // Sửa từ UUID thành Long: NhanVienRepository dùng Long
}