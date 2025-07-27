package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChatLieu;
import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.dto.ChatLieuDTO;
import fpt.duantotnghiep.sd28.repo.ChatLieuRepository;
import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChatLieuService {

    private final ChatLieuRepository chatLieuRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    public ChatLieuService(final ChatLieuRepository chatLieuRepository,
                           final ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.chatLieuRepository = chatLieuRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
    }

    public List<ChatLieuDTO> findAll() {
        final List<ChatLieu> chatLieus = chatLieuRepository.findAll(Sort.by("id"));
        return chatLieus.stream()
                .map(chatLieu -> mapToDTO(chatLieu, new ChatLieuDTO()))
                .toList();
    }

    public ChatLieuDTO get(final Long id) {
        return chatLieuRepository.findById(id)
                .map(chatLieu -> mapToDTO(chatLieu, new ChatLieuDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ChatLieuDTO chatLieuDTO) {
        final ChatLieu chatLieu = new ChatLieu();
        mapToEntity(chatLieuDTO, chatLieu);
        return chatLieuRepository.save(chatLieu).getId();
    }

    public void update(final Long id, final ChatLieuDTO chatLieuDTO) {
        final ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chatLieuDTO, chatLieu);
        chatLieuRepository.save(chatLieu);
    }

    public void delete(final Long id) {
        chatLieuRepository.deleteById(id);
    }

    private ChatLieuDTO mapToDTO(final ChatLieu chatLieu, final ChatLieuDTO chatLieuDTO) {
        chatLieuDTO.setId(chatLieu.getId());
        chatLieuDTO.setTenChatLieu(chatLieu.getTenChatLieu());
        chatLieuDTO.setMaChatLieu(chatLieu.getMaChatLieu());
        return chatLieuDTO;
    }

    private ChatLieu mapToEntity(final ChatLieuDTO chatLieuDTO, final ChatLieu chatLieu) {
        chatLieu.setTenChatLieu(chatLieuDTO.getTenChatLieu());
        chatLieu.setMaChatLieu(chatLieuDTO.getMaChatLieu());
        return chatLieu;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final ChiTietSanPham chatLieuChiTietSanPham = chiTietSanPhamRepository.findFirstByChatLieu(chatLieu);
        if (chatLieuChiTietSanPham != null) {
            referencedWarning.setKey("chatLieu.chiTietSanPham.chatLieu.referenced");
            referencedWarning.addParam(chatLieuChiTietSanPham.getId());
            return referencedWarning;
        }
        return null;
    }
}