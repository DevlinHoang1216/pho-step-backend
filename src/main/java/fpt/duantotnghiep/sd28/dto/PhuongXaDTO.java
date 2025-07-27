package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhuongXaDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String tenPhuongXa;

    @Size(max = 10)
    private String maXa;

    @NotNull
    private Long quanHuyen;
}