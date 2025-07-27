package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.PhieuGiamGiaDTO;
import fpt.duantotnghiep.sd28.service.PhieuGiamGiaService;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
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
@RequestMapping(value = "/api/phieuGiamGias", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhieuGiamGiaController {

    private final PhieuGiamGiaService phieuGiamGiaService;

    public PhieuGiamGiaController(final PhieuGiamGiaService phieuGiamGiaService) {
        this.phieuGiamGiaService = phieuGiamGiaService;
    }

    @GetMapping
    public ResponseEntity<List<PhieuGiamGiaDTO>> getAllPhieuGiamGias() {
        return ResponseEntity.ok(phieuGiamGiaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuGiamGiaDTO> getPhieuGiamGia(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(phieuGiamGiaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createPhieuGiamGia(@RequestBody @Valid final PhieuGiamGiaDTO phieuGiamGiaDTO) {
        final UUID createdId = phieuGiamGiaService.create(phieuGiamGiaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updatePhieuGiamGia(@PathVariable(name = "id") final UUID id,
                                                   @RequestBody @Valid final PhieuGiamGiaDTO phieuGiamGiaDTO) {
        phieuGiamGiaService.update(id, phieuGiamGiaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePhieuGiamGia(@PathVariable(name = "id") final UUID id) {
        final ReferencedWarning referencedWarning = phieuGiamGiaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        phieuGiamGiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}