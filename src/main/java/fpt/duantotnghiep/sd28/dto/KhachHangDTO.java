package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.util.UUID; // Không cần thiết nếu TaiKhoan là Long

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhachHangDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String tenKhachHang;

    @Size(max = 20)
    private String soDienThoai;

    @NotNull
    private Boolean gioiTinh;

    private LocalDate ngaySinh;

    @NotNull
    @Size(max = 20)
    private String maKhachHang;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    private Long taiKhoan; // Sửa từ UUID thành Long (Giả định TaiKhoan ID là Long)
}