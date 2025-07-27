package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.TaiKhoanDTO;
import fpt.duantotnghiep.sd28.service.TaiKhoanService;
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
@RequestMapping(value = "/api/taiKhoans", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaiKhoanController {

    private final TaiKhoanService taiKhoanService;

    public TaiKhoanController(final TaiKhoanService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    @GetMapping
    public ResponseEntity<List<TaiKhoanDTO>> getAllTaiKhoans() {
        return ResponseEntity.ok(taiKhoanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaiKhoanDTO> getTaiKhoan(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(taiKhoanService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTaiKhoan(
            @RequestBody @Valid final TaiKhoanDTO taiKhoanDTO) {
        final Long createdId = taiKhoanService.create(taiKhoanDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTaiKhoan(@PathVariable(name = "id") final Long id,
                                               @RequestBody @Valid final TaiKhoanDTO taiKhoanDTO) {
        taiKhoanService.update(id, taiKhoanDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTaiKhoan(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = taiKhoanService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        taiKhoanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}