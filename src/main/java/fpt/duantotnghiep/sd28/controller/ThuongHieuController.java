package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.ThuongHieuDTO;
import fpt.duantotnghiep.sd28.service.ThuongHieuService;
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
@RequestMapping(value = "/api/thuongHieus", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    public ThuongHieuController(final ThuongHieuService thuongHieuService) {
        this.thuongHieuService = thuongHieuService;
    }

    @GetMapping
    public ResponseEntity<List<ThuongHieuDTO>> getAllThuongHieus() {
        return ResponseEntity.ok(thuongHieuService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuongHieuDTO> getThuongHieu(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(thuongHieuService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createThuongHieu(
            @RequestBody @Valid final ThuongHieuDTO thuongHieuDTO) {
        final Long createdId = thuongHieuService.create(thuongHieuDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateThuongHieu(@PathVariable(name = "id") final Long id,
                                                 @RequestBody @Valid final ThuongHieuDTO thuongHieuDTO) {
        thuongHieuService.update(id, thuongHieuDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteThuongHieu(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = thuongHieuService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        thuongHieuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}