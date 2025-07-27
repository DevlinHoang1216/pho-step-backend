package fpt.duantotnghiep.sd28.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.duantotnghiep.sd28.dto.AnhSanPhamDTO;
import fpt.duantotnghiep.sd28.service.AnhSanPhamService;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/anhSanPhams")
public class AnhSanPhamController {

    private final AnhSanPhamService anhSanPhamService;
    private final ObjectMapper objectMapper;

    public AnhSanPhamController(final AnhSanPhamService anhSanPhamService, ObjectMapper objectMapper) {
        this.anhSanPhamService = anhSanPhamService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnhSanPhamDTO>> getAllAnhSanPhams() {
        return ResponseEntity.ok(anhSanPhamService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnhSanPhamDTO> getAnhSanPham(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(anhSanPhamService.get(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    public ResponseEntity<AnhSanPhamDTO> createAnhSanPham( // Thay đổi kiểu trả về thành AnhSanPhamDTO
                                                           @RequestPart("file") MultipartFile file,
                                                           @RequestPart(value = "data", required = false) String anhSanPhamDtoJson) throws IOException {
        AnhSanPhamDTO anhSanPhamDTO = new AnhSanPhamDTO();
        if (anhSanPhamDtoJson != null && !anhSanPhamDtoJson.isEmpty()) {
            anhSanPhamDTO = objectMapper.readValue(anhSanPhamDtoJson, AnhSanPhamDTO.class);
        }

        // Đặt laAnhDaiDien mặc định là false nếu không được chỉ định
        if (anhSanPhamDTO.getLaAnhDaiDien() == null) {
            anhSanPhamDTO.setLaAnhDaiDien(false);
        }

        // Gọi phương thức createAndReturnDTO từ service
        final AnhSanPhamDTO createdAnhSanPham = anhSanPhamService.createAndReturnDTO(file, anhSanPhamDTO);
        return new ResponseEntity<>(createdAnhSanPham, HttpStatus.CREATED);
    }

    // Phương thức mới để thêm ảnh chỉ từ URL (không có file)
    @PostMapping(value = "/add-url", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    public ResponseEntity<AnhSanPhamDTO> addAnhSanPhamFromUrl(
            @RequestBody @Valid AnhSanPhamDTO anhSanPhamDTO) {
        // Đảm bảo URL ảnh đã có trong DTO
        if (anhSanPhamDTO.getUrlAnh() == null || anhSanPhamDTO.getUrlAnh().isEmpty()) {
            throw new IllegalArgumentException("URL ảnh không được để trống.");
        }
        // Đặt laAnhDaiDien mặc định là false nếu không được chỉ định
        if (anhSanPhamDTO.getLaAnhDaiDien() == null) {
            anhSanPhamDTO.setLaAnhDaiDien(false);
        }
        final AnhSanPhamDTO createdAnhSanPham = anhSanPhamService.createFromUrl(anhSanPhamDTO); // Phương thức mới trong Service
        return new ResponseEntity<>(createdAnhSanPham, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> updateAnhSanPham(@PathVariable(name = "id") final Long id,
                                                 @RequestBody @Valid final AnhSanPhamDTO anhSanPhamDTO) {
        anhSanPhamService.update(id, anhSanPhamDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAnhSanPham(@PathVariable(name = "id") final Long id) {
        anhSanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ReferencedException.class)
    public ResponseEntity<String> handleReferencedException(ReferencedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        System.err.println("IOException occurred: " + ex.getMessage());
        return new ResponseEntity<>("Lỗi xử lý dữ liệu hoặc file: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("Đã xảy ra lỗi không mong muốn: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
