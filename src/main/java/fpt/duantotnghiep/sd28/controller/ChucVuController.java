package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.ChucVuDTO;
import fpt.duantotnghiep.sd28.service.ChucVuService;
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
@RequestMapping(value = "/api/chucVus", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChucVuController {

    private final ChucVuService chucVuService;

    public ChucVuController(final ChucVuService chucVuService) {
        this.chucVuService = chucVuService;
    }

    @GetMapping
    public ResponseEntity<List<ChucVuDTO>> getAllChucVus() {
        return ResponseEntity.ok(chucVuService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChucVuDTO> getChucVu(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(chucVuService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createChucVu(@RequestBody @Valid final ChucVuDTO chucVuDTO) {
        final Long createdId = chucVuService.create(chucVuDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateChucVu(@PathVariable(name = "id") final Long id,
                                             @RequestBody @Valid final ChucVuDTO chucVuDTO) {
        chucVuService.update(id, chucVuDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChucVu(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = chucVuService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        chucVuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}