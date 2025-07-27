package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.ChiTietThanhToanDTO;
import fpt.duantotnghiep.sd28.service.ChiTietThanhToanService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/chiTietThanhToans", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChiTietThanhToanController {

    private final ChiTietThanhToanService chiTietThanhToanService;

    public ChiTietThanhToanController(final ChiTietThanhToanService chiTietThanhToanService) {
        this.chiTietThanhToanService = chiTietThanhToanService;
    }

    @GetMapping
    public ResponseEntity<List<ChiTietThanhToanDTO>> getAllChiTietThanhToans() {
        return ResponseEntity.ok(chiTietThanhToanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietThanhToanDTO> getChiTietThanhToan(
            @PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(chiTietThanhToanService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createChiTietThanhToan(
            @RequestBody @Valid final ChiTietThanhToanDTO chiTietThanhToanDTO) {
        final UUID createdId = chiTietThanhToanService.create(chiTietThanhToanDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateChiTietThanhToan(@PathVariable(name = "id") final UUID id,
                                                       @RequestBody @Valid final ChiTietThanhToanDTO chiTietThanhToanDTO) {
        chiTietThanhToanService.update(id, chiTietThanhToanDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChiTietThanhToan(@PathVariable(name = "id") final UUID id) {
        chiTietThanhToanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}