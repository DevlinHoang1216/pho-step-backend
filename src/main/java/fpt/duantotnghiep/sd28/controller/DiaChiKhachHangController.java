package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.DiaChiKhachHangDTO;
import fpt.duantotnghiep.sd28.service.DiaChiKhachHangService;
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
@RequestMapping(value = "/api/diaChiKhachHangs", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiaChiKhachHangController {

    private final DiaChiKhachHangService diaChiKhachHangService;

    public DiaChiKhachHangController(final DiaChiKhachHangService diaChiKhachHangService) {
        this.diaChiKhachHangService = diaChiKhachHangService;
    }

    @GetMapping
    public ResponseEntity<List<DiaChiKhachHangDTO>> getAllDiaChiKhachHangs() {
        return ResponseEntity.ok(diaChiKhachHangService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaChiKhachHangDTO> getDiaChiKhachHang(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(diaChiKhachHangService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDiaChiKhachHang(@RequestBody @Valid final DiaChiKhachHangDTO diaChiKhachHangDTO) {
        final Long createdId = diaChiKhachHangService.create(diaChiKhachHangDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDiaChiKhachHang(@PathVariable(name = "id") final Long id,
                                                      @RequestBody @Valid final DiaChiKhachHangDTO diaChiKhachHangDTO) {
        diaChiKhachHangService.update(id, diaChiKhachHangDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDiaChiKhachHang(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = diaChiKhachHangService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        diaChiKhachHangService.delete(id);
        return ResponseEntity.noContent().build();
    }
}