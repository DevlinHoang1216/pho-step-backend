package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.HoaDonDTO;
import fpt.duantotnghiep.sd28.service.HoaDonService;
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
@RequestMapping(value = "/api/hoaDons", produces = MediaType.APPLICATION_JSON_VALUE)
public class HoaDonController {

    private final HoaDonService hoaDonService;

    public HoaDonController(final HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @GetMapping
    public ResponseEntity<List<HoaDonDTO>> getAllHoaDons() {
        return ResponseEntity.ok(hoaDonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDonDTO> getHoaDon(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(hoaDonService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createHoaDon(@RequestBody @Valid final HoaDonDTO hoaDonDTO) {
        final UUID createdId = hoaDonService.create(hoaDonDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateHoaDon(@PathVariable(name = "id") final UUID id,
                                             @RequestBody @Valid final HoaDonDTO hoaDonDTO) {
        hoaDonService.update(id, hoaDonDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHoaDon(@PathVariable(name = "id") final UUID id) {
        final ReferencedWarning referencedWarning = hoaDonService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        hoaDonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}