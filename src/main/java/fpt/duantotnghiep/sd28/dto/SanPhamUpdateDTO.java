package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamUpdateDTO {
    // ID is needed to identify which product to update
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String tenSanPham;

    private String urlAnhDaiDien; // urlAnhDaiDien may or may not be @NotBlank depending on your requirements, assuming it can be null/empty for an update if user doesn't provide a new image.
}