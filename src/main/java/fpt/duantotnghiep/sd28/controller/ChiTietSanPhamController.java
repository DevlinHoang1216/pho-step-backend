package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.ChiTietSanPhamDTO;
import fpt.duantotnghiep.sd28.service.ChiTietSanPhamService;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/chi-tiet-san-phams", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChiTietSanPhamController {

    private final ChiTietSanPhamService chiTietSanPhamService;

    public ChiTietSanPhamController(ChiTietSanPhamService chiTietSanPhamService) {
        this.chiTietSanPhamService = chiTietSanPhamService;
    }

    @GetMapping
    public ResponseEntity<List<ChiTietSanPhamDTO>> getAllChiTietSanPham() {
        return ResponseEntity.ok(chiTietSanPhamService.findAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ChiTietSanPhamDTO>> filterChiTietSanPham(
            @RequestParam(required = false) Long sanPhamId,
            @RequestParam(required = false) Long thuongHieuId, // Thêm tham số lọc theo hãng
            @RequestParam(required = false) Long danhMucId,     // Thêm tham số lọc theo danh mục
            @RequestParam(required = false) Long chatLieuId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam(required = false) Long kichCoId,
            @RequestParam(required = false) String keyword) {
        // Truyền tất cả các tham số lọc xuống service
        return ResponseEntity.ok(chiTietSanPhamService.findByFilters(sanPhamId, thuongHieuId, danhMucId, chatLieuId, mauSacId, kichCoId, keyword));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateTrangThaiSanPhamRieng(
            @PathVariable UUID id,
            @RequestBody Map<String, Boolean> status) {
        try {
            boolean active = status.get("active");
            chiTietSanPhamService.toggleStatus(id, active);
            return ResponseEntity.ok("Cập nhật trạng thái thành công");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật trạng thái thất bại: " + e.getMessage());
        }
    }

    @GetMapping("/san-pham/{sanPhamId}")
    public ResponseEntity<List<ChiTietSanPhamDTO>> getVariantsBySanPhamId(@PathVariable Long sanPhamId) {
        return ResponseEntity.ok(chiTietSanPhamService.findBySanPhamId(sanPhamId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietSanPhamDTO> getChiTietSanPham(@PathVariable UUID id) {
        return ResponseEntity.ok(chiTietSanPhamService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createChiTietSanPham(@RequestBody @Valid ChiTietSanPhamDTO chiTietSanPhamDTO) {
        UUID createdId = chiTietSanPhamService.create(chiTietSanPhamDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateChiTietSanPham(@PathVariable UUID id,
                                                     @RequestBody @Valid ChiTietSanPhamDTO chiTietSanPhamDTO) {
        chiTietSanPhamService.update(id, chiTietSanPhamDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChiTietSanPham(@PathVariable UUID id) {
        try {
            ReferencedWarning referencedWarning = chiTietSanPhamService.getReferencedWarning(id);
            if (referencedWarning != null) {
                throw new ReferencedException(referencedWarning);
            }
            chiTietSanPhamService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ReferencedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
