package fpt.duantotnghiep.sd28.dto; // CORRECTED PACKAGE


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoaDonChiTietDTO {

    private Long id;

    @NotNull
    private Integer soLuong;

    @NotNull
    @Digits(integer = 20, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "68.08")
    private BigDecimal donGia;

    @NotNull
    private UUID hoaDon;

    @NotNull
    private UUID chiTietSp;
}