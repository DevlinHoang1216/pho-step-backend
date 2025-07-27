package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChatLieuDTO {
    private Long id;

    @NotBlank(message = "Tên chất liệu không được để trống")
    @Size(max = 100, message = "Tên chất liệu không được vượt quá 100 ký tự")
    private String tenChatLieu;

    @NotBlank(message = "Mã chất liệu không được để trống")
    @Size(max = 10, message = "Mã chất liệu không được vượt quá 10 ký tự")
    private String maChatLieu;
}