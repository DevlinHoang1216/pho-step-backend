package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.GioHangChiTietDTO;
import fpt.duantotnghiep.sd28.service.GioHangChiTietService;
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
@RequestMapping(value = "/api/gioHangChiTiets", produces = MediaType.APPLICATION_JSON_VALUE)
public class GioHangChiTietController {

    private final GioHangChiTietService gioHangChiTietService;

    public GioHangChiTietController(final GioHangChiTietService gioHangChiTietService) {
        this.gioHangChiTietService = gioHangChiTietService;
    }

    @GetMapping
    public ResponseEntity<List<GioHangChiTietDTO>> getAllGioHangChiTiets() {
        return ResponseEntity.ok(gioHangChiTietService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GioHangChiTietDTO> getGioHangChiTiet(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(gioHangChiTietService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGioHangChiTiet(@RequestBody @Valid final GioHangChiTietDTO gioHangChiTietDTO) {
        final Long createdId = gioHangChiTietService.create(gioHangChiTietDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateGioHangChiTiet(@PathVariable(name = "id") final Long id,
                                                     @RequestBody @Valid final GioHangChiTietDTO gioHangChiTietDTO) {
        gioHangChiTietService.update(id, gioHangChiTietDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGioHangChiTiet(@PathVariable(name = "id") final Long id) {
        gioHangChiTietService.delete(id);
        return ResponseEntity.noContent().build();
    }
}