package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID; // Giữ lại import UUID
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaGiamGiaDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String maCode;

    @NotNull
    private Boolean daSuDung;

    private UUID idHoaDonDaSuDung; // Nếu ID của HoaDon là UUID

    private LocalDateTime ngayTao;

    private LocalDateTime ngaySuDung;

    @NotNull
    private UUID phieuGiamGia; // Đã sửa từ Long thành UUID

    private Long khachHangDuocCap; // Giả định KhachHang có ID là Long
}