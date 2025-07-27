package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ThuongHieuDTO {
    private Long id;

    @NotBlank(message = "Tên thương hiệu không được để trống")
    @Size(max = 100, message = "Tên thương hiệu không được vượt quá 100 ký tự")
    private String tenThuongHieu;

    @NotBlank(message = "Mã thương hiệu không được để trống")
    @Size(max = 20, message = "Mã thương hiệu không được vượt quá 20 ký tự")
    private String maThuongHieu;
}