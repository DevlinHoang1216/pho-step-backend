package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.HoaDonChiTietDTO;
import fpt.duantotnghiep.sd28.service.HoaDonChiTietService;
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
@RequestMapping(value = "/api/hoaDonChiTiets", produces = MediaType.APPLICATION_JSON_VALUE)
public class HoaDonChiTietController {

    private final HoaDonChiTietService hoaDonChiTietService;

    public HoaDonChiTietController(final HoaDonChiTietService hoaDonChiTietService) {
        this.hoaDonChiTietService = hoaDonChiTietService;
    }

    @GetMapping
    public ResponseEntity<List<HoaDonChiTietDTO>> getAllHoaDonChiTiets() {
        return ResponseEntity.ok(hoaDonChiTietService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDonChiTietDTO> getHoaDonChiTiet(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(hoaDonChiTietService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createHoaDonChiTiet(@RequestBody @Valid final HoaDonChiTietDTO hoaDonChiTietDTO) {
        final Long createdId = hoaDonChiTietService.create(hoaDonChiTietDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateHoaDonChiTiet(@PathVariable(name = "id") final Long id,
                                                    @RequestBody @Valid final HoaDonChiTietDTO hoaDonChiTietDTO) {
        hoaDonChiTietService.update(id, hoaDonChiTietDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHoaDonChiTiet(@PathVariable(name = "id") final Long id) {
        hoaDonChiTietService.delete(id);
        return ResponseEntity.noContent().build();
    }
}