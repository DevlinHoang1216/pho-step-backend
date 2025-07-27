package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.KhachHangDTO;
import fpt.duantotnghiep.sd28.service.KhachHangService;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/khachHangs", produces = MediaType.APPLICATION_JSON_VALUE)
public class KhachHangController {

    private final KhachHangService khachHangService;

    public KhachHangController(final KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @GetMapping
    public ResponseEntity<List<KhachHangDTO>> getAllKhachHangs() {
        return ResponseEntity.ok(khachHangService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHangDTO> getKhachHang(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(khachHangService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createKhachHang(@RequestBody @Valid final KhachHangDTO khachHangDTO) {
        final Long createdId = khachHangService.create(khachHangDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateKhachHang(@PathVariable(name = "id") final Long id,
                                                @RequestBody @Valid final KhachHangDTO khachHangDTO) {
        khachHangService.update(id, khachHangDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = khachHangService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        khachHangService.delete(id);
        return ResponseEntity.noContent().build();
    }
}