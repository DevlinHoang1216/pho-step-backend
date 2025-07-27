package fpt.duantotnghiep.sd28.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KichCoDTO {

    private Long id;

    @NotBlank(message = "Tên kích cỡ không được để trống")
    @Size(max = 100, message = "Tên kích cỡ không được vượt quá 100 ký tự")
    @Pattern(regexp = "^\\d+(\\.\\d{1})?$", message = "Kích cỡ phải là số nguyên hoặc số thập phân với một chữ số sau dấu chấm (VD: 36, 36.7)")
    private String tenKichCo;

    @NotBlank(message = "Mã kích cỡ không được để trống")
    @Size(max = 20, message = "Mã kích cỡ không được vượt quá 20 ký tự")
    @Pattern(regexp = "^SIZE_\\d+(_\\d)?_\\d{4}$", message = "Mã kích cỡ không đúng định dạng.")
    private String maKichCo;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayTao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayCapNhat;
}