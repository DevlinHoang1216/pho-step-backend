package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.LichSuHoaDonDTO;
import fpt.duantotnghiep.sd28.service.LichSuHoaDonService;
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
@RequestMapping(value = "/api/lichSuHoaDons", produces = MediaType.APPLICATION_JSON_VALUE)
public class LichSuHoaDonController {

    private final LichSuHoaDonService lichSuHoaDonService;

    public LichSuHoaDonController(final LichSuHoaDonService lichSuHoaDonService) {
        this.lichSuHoaDonService = lichSuHoaDonService;
    }

    @GetMapping
    public ResponseEntity<List<LichSuHoaDonDTO>> getAllLichSuHoaDons() {
        return ResponseEntity.ok(lichSuHoaDonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuHoaDonDTO> getLichSuHoaDon(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(lichSuHoaDonService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLichSuHoaDon(@RequestBody @Valid final LichSuHoaDonDTO lichSuHoaDonDTO) {
        final Long createdId = lichSuHoaDonService.create(lichSuHoaDonDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLichSuHoaDon(@PathVariable(name = "id") final Long id,
                                                   @RequestBody @Valid final LichSuHoaDonDTO lichSuHoaDonDTO) {
        lichSuHoaDonService.update(id, lichSuHoaDonDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLichSuHoaDon(@PathVariable(name = "id") final Long id) {
        lichSuHoaDonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}