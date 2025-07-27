package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.MaGiamGiaDTO;
import fpt.duantotnghiep.sd28.service.MaGiamGiaService;
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
@RequestMapping(value = "/api/maGiamGias", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaGiamGiaController {

    private final MaGiamGiaService maGiamGiaService;

    public MaGiamGiaController(final MaGiamGiaService maGiamGiaService) {
        this.maGiamGiaService = maGiamGiaService;
    }

    @GetMapping
    public ResponseEntity<List<MaGiamGiaDTO>> getAllMaGiamGias() {
        return ResponseEntity.ok(maGiamGiaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaGiamGiaDTO> getMaGiamGia(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(maGiamGiaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMaGiamGia(@RequestBody @Valid final MaGiamGiaDTO maGiamGiaDTO) {
        final Long createdId = maGiamGiaService.create(maGiamGiaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMaGiamGia(@PathVariable(name = "id") final Long id,
                                                @RequestBody @Valid final MaGiamGiaDTO maGiamGiaDTO) {
        maGiamGiaService.update(id, maGiamGiaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMaGiamGia(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = maGiamGiaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        maGiamGiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}