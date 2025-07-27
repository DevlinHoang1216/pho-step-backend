package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GioHangDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String maPhienGioHang;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    @NotNull
    @Size(max = 50)
    private String trangThai;

    @NotNull
    private Long khachHang;
}