package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaiKhoanDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String tenDangNhap;

    @NotNull
    @Size(max = 255)
    private String matKhauHash;

    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 50)
    private String vaiTro;

    @NotNull
    private Boolean trangThai;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;
}