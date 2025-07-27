package fpt.duantotnghiep.sd28.controller;

import fpt.duantotnghiep.sd28.dto.ChatLieuDTO;
import fpt.duantotnghiep.sd28.service.ChatLieuService;
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
@RequestMapping(value = "/api/chatLieus", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatLieuController {

    private final ChatLieuService chatLieuService;

    public ChatLieuController(final ChatLieuService chatLieuService) {
        this.chatLieuService = chatLieuService;
    }

    @GetMapping
    public ResponseEntity<List<ChatLieuDTO>> getAllChatLieus() {
        return ResponseEntity.ok(chatLieuService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatLieuDTO> getChatLieu(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(chatLieuService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createChatLieu(
            @RequestBody @Valid final ChatLieuDTO chatLieuDTO) {
        final Long createdId = chatLieuService.create(chatLieuDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateChatLieu(@PathVariable(name = "id") final Long id,
                                               @RequestBody @Valid final ChatLieuDTO chatLieuDTO) {
        chatLieuService.update(id, chatLieuDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChatLieu(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = chatLieuService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        chatLieuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}