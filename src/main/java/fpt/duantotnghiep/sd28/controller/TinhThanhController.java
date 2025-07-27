package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.TinhThanhDTO;
import fpt.duantotnghiep.sd28.service.TinhThanhService;
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
@RequestMapping(value = "/api/tinhThanhs", produces = MediaType.APPLICATION_JSON_VALUE)
public class TinhThanhController {

    private final TinhThanhService tinhThanhService;

    public TinhThanhController(final TinhThanhService tinhThanhService) {
        this.tinhThanhService = tinhThanhService;
    }

    @GetMapping
    public ResponseEntity<List<TinhThanhDTO>> getAllTinhThanhs() {
        return ResponseEntity.ok(tinhThanhService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TinhThanhDTO> getTinhThanh(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(tinhThanhService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTinhThanh(
            @RequestBody @Valid final TinhThanhDTO tinhThanhDTO) {
        final Long createdId = tinhThanhService.create(tinhThanhDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTinhThanh(@PathVariable(name = "id") final Long id,
                                                @RequestBody @Valid final TinhThanhDTO tinhThanhDTO) {
        tinhThanhService.update(id, tinhThanhDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTinhThanh(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = tinhThanhService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        tinhThanhService.delete(id);
        return ResponseEntity.noContent().build();
    }
}