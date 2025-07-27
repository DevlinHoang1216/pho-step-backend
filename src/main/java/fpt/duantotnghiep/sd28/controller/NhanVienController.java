package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.NhanVienDTO;
import fpt.duantotnghiep.sd28.service.NhanVienService;
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
@RequestMapping(value = "/api/nhanViens", produces = MediaType.APPLICATION_JSON_VALUE)
public class NhanVienController {

    private final NhanVienService nhanVienService;

    public NhanVienController(final NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public ResponseEntity<List<NhanVienDTO>> getAllNhanViens() {
        return ResponseEntity.ok(nhanVienService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVienDTO> getNhanVien(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(nhanVienService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createNhanVien(@RequestBody @Valid final NhanVienDTO nhanVienDTO) {
        final Long createdId = nhanVienService.create(nhanVienDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateNhanVien(@PathVariable(name = "id") final Long id,
                                               @RequestBody @Valid final NhanVienDTO nhanVienDTO) {
        nhanVienService.update(id, nhanVienDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = nhanVienService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        nhanVienService.delete(id);
        return ResponseEntity.noContent().build();
    }
}