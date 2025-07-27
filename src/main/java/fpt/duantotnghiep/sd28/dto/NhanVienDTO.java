package fpt.duantotnghiep.sd28.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.util.UUID; // Không cần thiết nếu TaiKhoan và ChucVu không phải UUID

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhanVienDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String tenNhanVien;

    private LocalDate ngaySinh;

    @NotNull
    private Boolean gioiTinh;

    @Size(max = 20)
    private String soDienThoai;

    @NotNull
    @Size(max = 10)
    private String maNhanVien;

    @Size(max = 20)
    private String cccd;

    @Size(max = 255)
    private String diaChiSoNhaTenDuong;

    @Size(max = 100)
    private String diaChiPhuongXa;

    @Size(max = 100)
    private String diaChiQuanHuyen;

    @Size(max = 100)
    private String diaChiTinhThanh;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayCapNhat;

    @NotNull
    private Boolean trangThai;

    private Long taiKhoan; // Đã sửa từ UUID thành Long (giả định TaiKhoan có ID là Long)

    @NotNull
    private Long chucVu; // Đã là Long, phù hợp với NhanVienRepository
}