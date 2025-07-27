package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.SanPhamDTO;
import fpt.duantotnghiep.sd28.dto.SanPhamUpdateDTO;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.repo.SanPhamRepository;
import fpt.duantotnghiep.sd28.service.SanPhamService;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/san-phams", produces = MediaType.APPLICATION_JSON_VALUE)
public class SanPhamController {

    private final SanPhamService sanPhamService;

    public SanPhamController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    @GetMapping
    public ResponseEntity<Page<SanPhamDTO>> getAllSanPhams(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(sanPhamService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDTO> getSanPham(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sanPhamService.get(id));
    }

    // Endpoint để cập nhật trạng thái sản phẩm (ví dụ: kích hoạt/ngừng kinh doanh)
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateSanPhamStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        try {
            String status = statusUpdate.get("trangThai");
            if (status == null || (!status.equals("dang_kinh_doanh") && !status.equals("ngung_kinh_doanh") && !status.equals("het_hang"))) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Trạng thái không hợp lệ");
                errorResponse.put("details", "Trạng thái phải là 'dang_kinh_doanh', 'ngung_kinh_doanh', hoặc 'het_hang'");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            sanPhamService.updateStatus(id, status);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Cập nhật trạng thái sản phẩm thành công");
            return ResponseEntity.ok(successResponse);
        } catch (NotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Sản phẩm không tồn tại");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi server khi cập nhật trạng thái");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Endpoint để xóa sản phẩm (đặt trạng thái ngừng kinh doanh)
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Map<String, String>> deleteSanPham(@PathVariable(name = "id") Long id) {
        try {
            sanPhamService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Sản phẩm không tồn tại");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            System.out.println("Error deleting product ID: " + id + ", Message: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi server");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Endpoint để kiểm tra cảnh báo tham chiếu trước khi xóa
    @GetMapping("/{id}/referenced-warning")
    public ResponseEntity<ReferencedWarning> getReferencedWarning(@PathVariable Long id) {
        ReferencedWarning warning = sanPhamService.getReferencedWarning(id);
        if (warning != null && warning.hasWarnings()) {
            return ResponseEntity.ok(warning);
        }
        return ResponseEntity.noContent().build();
    }

    // ***************************************************************
    // PHƯƠNG THỨC MỚI: Endpoint để thêm mới sản phẩm và các chi tiết của nó
    // ***************************************************************
    @PostMapping // Thêm mới sản phẩm và chi tiết của nó
    @ApiResponse(responseCode = "201", description = "Sản phẩm và các chi tiết được tạo thành công")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    @ApiResponse(responseCode = "500", description = "Lỗi server")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody SanPhamDTO sanPhamDto) {
        try {
            System.out.println("Received SanPhamDTO for creation: " + sanPhamDto); // Log để debug

            // Gọi service để xử lý logic thêm sản phẩm và chi tiết
            SanPhamDTO createdProduct = sanPhamService.createProductWithDetails(sanPhamDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (NotFoundException e) {
            // Xử lý các trường hợp không tìm thấy Danh mục, Thương hiệu, Màu sắc, Kích cỡ, Chất liệu
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi dữ liệu tham chiếu");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (DataIntegrityViolationException e) {
            // Xử lý lỗi trùng lặp mã sản phẩm/mã chi tiết sản phẩm
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi dữ liệu trùng lặp");
            errorResponse.put("details", "Mã sản phẩm hoặc mã chi tiết sản phẩm đã tồn tại. Vui lòng kiểm tra lại.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (ConstraintViolationException e) {
            // Xử lý lỗi validation từ @Valid
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi validation dữ liệu");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (IllegalArgumentException e) {
            // Xử lý các lỗi logic nghiệp vụ khác (ví dụ: trạng thái không hợp lệ)
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi dữ liệu không hợp lệ");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            // Xử lý các lỗi khác (database errors...)
            System.err.println("Error creating product: " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Lỗi server khi thêm sản phẩm");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Các exception handler hiện có
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<String> handleDataIntegrityViolation(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi dữ liệu: " + ex.getMessage());
    }
}
