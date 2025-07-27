package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.DanhMucDTO;
import fpt.duantotnghiep.sd28.service.DanhMucService;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/danh-mucs", produces = MediaType.APPLICATION_JSON_VALUE)
public class DanhMucController {

    private final DanhMucService danhMucService;

    public DanhMucController(DanhMucService danhMucService) {
        this.danhMucService = danhMucService;
    }

    @GetMapping
    public ResponseEntity<List<DanhMucDTO>> getAllDanhMucs() {
        return ResponseEntity.ok(danhMucService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhMucDTO> getDanhMuc(@PathVariable Long id) {
        return ResponseEntity.ok(danhMucService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDanhMuc(@RequestBody @Valid DanhMucDTO danhMucDTO) {
        Long createdId = danhMucService.create(danhMucDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDanhMuc(@PathVariable Long id, @RequestBody @Valid DanhMucDTO danhMucDTO) {
        danhMucService.update(id, danhMucDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Long id) {
        ReferencedWarning referencedWarning = danhMucService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        danhMucService.delete(id);
        return ResponseEntity.noContent().build();
    }
}