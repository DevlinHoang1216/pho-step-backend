package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnhSanPhamDTO {

    private Long id;

    @NotNull(message = "ID chi tiết sản phẩm không được để trống")
    private UUID chiTietSpId;

    // Bỏ @NotBlank vì urlAnh sẽ được Backend tạo ra sau khi upload file
    private String urlAnh;

    @NotNull(message = "Trạng thái ảnh đại diện không được để trống")
    private Boolean laAnhDaiDien;
}
