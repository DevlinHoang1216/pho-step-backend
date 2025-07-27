package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.DanhGiaSanPhamDTO;
import fpt.duantotnghiep.sd28.service.DanhGiaSanPhamService;
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
@RequestMapping(value = "/api/danhGiaSanPhams", produces = MediaType.APPLICATION_JSON_VALUE)
public class DanhGiaSanPhamController {

    private final DanhGiaSanPhamService danhGiaSanPhamService;

    public DanhGiaSanPhamController(final DanhGiaSanPhamService danhGiaSanPhamService) {
        this.danhGiaSanPhamService = danhGiaSanPhamService;
    }

    @GetMapping
    public ResponseEntity<List<DanhGiaSanPhamDTO>> getAllDanhGiaSanPhams() {
        return ResponseEntity.ok(danhGiaSanPhamService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhGiaSanPhamDTO> getDanhGiaSanPham(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(danhGiaSanPhamService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDanhGiaSanPham(
            @RequestBody @Valid final DanhGiaSanPhamDTO danhGiaSanPhamDTO) {
        final Long createdId = danhGiaSanPhamService.create(danhGiaSanPhamDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDanhGiaSanPham(@PathVariable(name = "id") final Long id,
                                                     @RequestBody @Valid final DanhGiaSanPhamDTO danhGiaSanPhamDTO) {
        danhGiaSanPhamService.update(id, danhGiaSanPhamDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDanhGiaSanPham(@PathVariable(name = "id") final Long id) {
        danhGiaSanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}