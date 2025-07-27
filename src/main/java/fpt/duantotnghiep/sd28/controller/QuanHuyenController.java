package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.QuanHuyenDTO;
import fpt.duantotnghiep.sd28.service.QuanHuyenService;
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
@RequestMapping(value = "/api/quanHuyens", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuanHuyenController {

    private final QuanHuyenService quanHuyenService;

    public QuanHuyenController(final QuanHuyenService quanHuyenService) {
        this.quanHuyenService = quanHuyenService;
    }

    @GetMapping
    public ResponseEntity<List<QuanHuyenDTO>> getAllQuanHuyens() {
        return ResponseEntity.ok(quanHuyenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuanHuyenDTO> getQuanHuyen(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(quanHuyenService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createQuanHuyen(
            @RequestBody @Valid final QuanHuyenDTO quanHuyenDTO) {
        final Long createdId = quanHuyenService.create(quanHuyenDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateQuanHuyen(@PathVariable(name = "id") final Long id,
                                                @RequestBody @Valid final QuanHuyenDTO quanHuyenDTO) {
        quanHuyenService.update(id, quanHuyenDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuanHuyen(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = quanHuyenService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        quanHuyenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}