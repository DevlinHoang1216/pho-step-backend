package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.dto.AnhSanPhamDTO;
import fpt.duantotnghiep.sd28.entity.AnhSanPham;
import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.repo.AnhSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnhSanPhamService {

    private final AnhSanPhamRepository anhSanPhamRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    // Đường dẫn lưu trữ ảnh (có thể cấu hình trong application.properties)
    private final String UPLOAD_DIR = "uploads/images/";

    public AnhSanPhamService(AnhSanPhamRepository anhSanPhamRepository, ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.anhSanPhamRepository = anhSanPhamRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
        // Đảm bảo thư mục upload tồn tại
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    // Lấy tất cả ảnh của một chi tiết sản phẩm
    public List<AnhSanPhamDTO> getImagesByChiTietSanPhamId(UUID chiTietSpId) {
        List<AnhSanPham> images = anhSanPhamRepository.findByChiTietSp_Id(chiTietSpId);
        return images.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Tải ảnh lên và lưu vào DB
    @Transactional
    public AnhSanPhamDTO uploadImage(MultipartFile file, UUID chiTietSpId, Boolean laAnhDaiDien) throws IOException {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietSpId)
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại với ID: " + chiTietSpId));

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = "/images/" + fileName; // URL để truy cập ảnh từ frontend (cần cấu hình Resource Handler)

        // Nếu ảnh mới là ảnh đại diện, đặt lại tất cả ảnh khác của chi tiết sản phẩm này về không phải ảnh đại diện
        if (laAnhDaiDien) {
            anhSanPhamRepository.resetLaAnhDaiDienForChiTietSanPham(chiTietSpId);
        } else {
            // Nếu không có ảnh đại diện nào khác và ảnh này không phải là ảnh đại diện,
            // kiểm tra xem có ảnh nào là ảnh đại diện không. Nếu không, đặt ảnh này làm ảnh đại diện.
            if (!anhSanPhamRepository.findByChiTietSp_IdAndLaAnhDaiDienIsTrue(chiTietSpId).isPresent()) {
                laAnhDaiDien = true;
            }
        }


        AnhSanPham anhSanPham = AnhSanPham.builder()
                .urlAnh(imageUrl)
                .laAnhDaiDien(laAnhDaiDien)
                .chiTietSp(chiTietSanPham)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build();

        return mapToDTO(anhSanPhamRepository.save(anhSanPham));
    }

    // Thêm ảnh từ URL và lưu vào DB
    @Transactional
    public AnhSanPhamDTO addImageFromUrl(AnhSanPhamDTO anhSanPhamDTO) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(anhSanPhamDTO.getChiTietSpId())
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại với ID: " + anhSanPhamDTO.getChiTietSpId()));

        // Nếu ảnh mới là ảnh đại diện, đặt lại tất cả ảnh khác của chi tiết sản phẩm này về không phải ảnh đại diện
        if (anhSanPhamDTO.getLaAnhDaiDien()) {
            anhSanPhamRepository.resetLaAnhDaiDienForChiTietSanPham(anhSanPhamDTO.getChiTietSpId());
        } else {
            // Nếu không có ảnh đại diện nào khác và ảnh này không phải là ảnh đại diện,
            // kiểm tra xem có ảnh nào là ảnh đại diện không. Nếu không, đặt ảnh này làm ảnh đại diện.
            if (!anhSanPhamRepository.findByChiTietSp_IdAndLaAnhDaiDienIsTrue(anhSanPhamDTO.getChiTietSpId()).isPresent()) {
                anhSanPhamDTO.setLaAnhDaiDien(true);
            }
        }

        AnhSanPham anhSanPham = AnhSanPham.builder()
                .urlAnh(anhSanPhamDTO.getUrlAnh())
                .laAnhDaiDien(anhSanPhamDTO.getLaAnhDaiDien())
                .chiTietSp(chiTietSanPham)
                .ngayTao(LocalDateTime.now())
                .ngayCapNhat(LocalDateTime.now())
                .build();

        return mapToDTO(anhSanPhamRepository.save(anhSanPham));
    }

    // Cập nhật ảnh (chủ yếu là trạng thái laAnhDaiDien)
    @Transactional
    public AnhSanPhamDTO updateImage(Long id, AnhSanPhamDTO anhSanPhamDTO) {
        AnhSanPham existingImage = anhSanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ảnh sản phẩm không tồn tại với ID: " + id));

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(anhSanPhamDTO.getChiTietSpId())
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại với ID: " + anhSanPhamDTO.getChiTietSpId()));

        // Nếu ảnh được cập nhật là ảnh đại diện, đặt lại tất cả ảnh khác của chi tiết sản phẩm này về không phải ảnh đại diện
        if (anhSanPhamDTO.getLaAnhDaiDien()) {
            anhSanPhamRepository.resetLaAnhDaiDienForChiTietSanPham(existingImage.getChiTietSp().getId());
        } else {
            // Nếu ảnh này không còn là ảnh đại diện, và không có ảnh đại diện nào khác,
            // thì cần chọn một ảnh khác làm đại diện (hoặc để trống nếu không có ảnh nào khác)
            // Logic này có thể phức tạp hơn, tùy thuộc vào yêu cầu.
            // Hiện tại, nếu bỏ chọn ảnh đại diện, và không chọn ảnh khác, sẽ không có ảnh đại diện.
            // Nếu ảnh này bị bỏ chọn đại diện, và nó là ảnh duy nhất, thì sau đó sẽ không có ảnh đại diện.
            // Nếu có nhiều ảnh và ảnh này bị bỏ chọn đại diện, nhưng có ảnh khác đang là đại diện, thì không cần làm gì.
            // Nếu ảnh này bị bỏ chọn đại diện, và không có ảnh nào khác đang là đại diện,
            // và vẫn còn ảnh khác, thì có thể tự động chọn ảnh đầu tiên còn lại làm đại diện.
            // Tuy nhiên, để đơn giản, ta sẽ chỉ reset nếu laAnhDaiDien là true.
        }

        existingImage.setUrlAnh(anhSanPhamDTO.getUrlAnh()); // Có thể cập nhật URL nếu cần
        existingImage.setLaAnhDaiDien(anhSanPhamDTO.getLaAnhDaiDien());
        existingImage.setChiTietSp(chiTietSanPham); // Đảm bảo liên kết đúng chi tiết sản phẩm
        existingImage.setNgayCapNhat(LocalDateTime.now());

        return mapToDTO(anhSanPhamRepository.save(existingImage));
    }

    // Xóa ảnh
    @Transactional
    public void deleteImage(Long id) {
        AnhSanPham anhSanPham = anhSanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ảnh sản phẩm không tồn tại với ID: " + id));

        // Xóa file vật lý nếu URL là local
        if (anhSanPham.getUrlAnh().startsWith("/images/")) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR + anhSanPham.getUrlAnh().substring("/images/".length()));
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Could not delete image file: " + anhSanPham.getUrlAnh() + " - " + e.getMessage());
                // Không ném exception để không chặn việc xóa bản ghi DB
            }
        }

        anhSanPhamRepository.delete(anhSanPham);

        // Sau khi xóa, kiểm tra nếu ảnh bị xóa là ảnh đại diện và còn ảnh khác,
        // thì chọn ảnh đầu tiên còn lại làm ảnh đại diện mới.
        if (anhSanPham.getLaAnhDaiDien()) {
            List<AnhSanPham> remainingImages = anhSanPhamRepository.findByChiTietSp_Id(anhSanPham.getChiTietSp().getId());
            if (!remainingImages.isEmpty()) {
                AnhSanPham firstRemaining = remainingImages.get(0);
                firstRemaining.setLaAnhDaiDien(true);
                anhSanPhamRepository.save(firstRemaining);
            }
        }
    }

    // Mapper từ Entity sang DTO
    private AnhSanPhamDTO mapToDTO(AnhSanPham anhSanPham) {
        return AnhSanPhamDTO.builder()
                .id(anhSanPham.getId())
                .urlAnh(anhSanPham.getUrlAnh())
                .laAnhDaiDien(anhSanPham.getLaAnhDaiDien())
                .chiTietSpId(anhSanPham.getChiTietSp().getId())
                .ngayTao(anhSanPham.getNgayTao())
                .ngayCapNhat(anhSanPham.getNgayCapNhat())
                .build();
    }
}
