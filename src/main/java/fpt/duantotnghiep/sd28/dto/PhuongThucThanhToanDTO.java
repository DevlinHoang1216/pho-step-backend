package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhuongThucThanhToanDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String tenPhuongThuc;

    private String moTa;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;
}