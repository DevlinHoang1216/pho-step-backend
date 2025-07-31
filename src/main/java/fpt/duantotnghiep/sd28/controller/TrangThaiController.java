package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.TrangThaiDTO;
import fpt.duantotnghiep.sd28.service.TrangThaiService;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/trangThais", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrangThaiController {

    private final TrangThaiService trangThaiService;

    public TrangThaiController(final TrangThaiService trangThaiService) {
        this.trangThaiService = trangThaiService;
    }

    @GetMapping
    public ResponseEntity<List<TrangThaiDTO>> getAllTrangThais() {
        return ResponseEntity.ok(trangThaiService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrangThaiDTO> getTrangThai(@PathVariable(name = "id") final Long id) { // Đã đổi từ Integer sang Long
        return ResponseEntity.ok(trangThaiService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createTrangThai( // Đã đổi từ Integer sang Long
                                                 @RequestBody @Valid final TrangThaiDTO trangThaiDTO) {
        final Long createdId = trangThaiService.create(trangThaiDTO); // Đã đổi từ Integer sang Long
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTrangThai(@PathVariable(name = "id") final Long id, // Đã đổi từ Integer sang Long
                                                @RequestBody @Valid final TrangThaiDTO trangThaiDTO) {
        trangThaiService.update(id, trangThaiDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrangThai(@PathVariable(name = "id") final Long id) { // Đã đổi từ Integer sang Long
        final ReferencedWarning referencedWarning = trangThaiService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        trangThaiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}