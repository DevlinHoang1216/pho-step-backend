package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.PhuongXaDTO;
import fpt.duantotnghiep.sd28.service.PhuongXaService;
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
@RequestMapping(value = "/api/phuongXas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhuongXaController {

    private final PhuongXaService phuongXaService;

    public PhuongXaController(final PhuongXaService phuongXaService) {
        this.phuongXaService = phuongXaService;
    }

    @GetMapping
    public ResponseEntity<List<PhuongXaDTO>> getAllPhuongXas() {
        return ResponseEntity.ok(phuongXaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhuongXaDTO> getPhuongXa(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(phuongXaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPhuongXa(@RequestBody @Valid final PhuongXaDTO phuongXaDTO) {
        final Long createdId = phuongXaService.create(phuongXaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePhuongXa(@PathVariable(name = "id") final Long id,
                                               @RequestBody @Valid final PhuongXaDTO phuongXaDTO) {
        phuongXaService.update(id, phuongXaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePhuongXa(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = phuongXaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        phuongXaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}