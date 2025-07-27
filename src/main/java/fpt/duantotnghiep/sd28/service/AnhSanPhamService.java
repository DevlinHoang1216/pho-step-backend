package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.AnhSanPham;
import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.dto.AnhSanPhamDTO;
import fpt.duantotnghiep.sd28.repo.AnhSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AnhSanPhamService {

    private final AnhSanPhamRepository anhSanPhamRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    // Đường dẫn tạm thời để lưu file. Trong thực tế, đây sẽ là đường dẫn tới Cloud Storage.
    // Đảm bảo thư mục này tồn tại hoặc được tạo tự động.
    private final String UPLOAD_DIR = "uploads/";

    public AnhSanPhamService(final AnhSanPhamRepository anhSanPhamRepository,
                             final ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.anhSanPhamRepository = anhSanPhamRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
        // Tạo thư mục uploads nếu nó chưa tồn tại
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            System.err.println("Không thể tạo thư mục uploads: " + e.getMessage());
            // Trong ứng dụng thực tế, bạn có thể muốn throw một exception hoặc log lỗi nghiêm trọng hơn
        }
    }

    public List<AnhSanPhamDTO> findAll() {
        final List<AnhSanPham> anhSanPhams = anhSanPhamRepository.findAll(Sort.by("id"));
        return anhSanPhams.stream()
                .map(anhSanPham -> mapToDTO(anhSanPham, new AnhSanPhamDTO()))
                .toList();
    }

    public AnhSanPhamDTO get(final Long id) {
        return anhSanPhamRepository.findById(id)
                .map(anhSanPham -> mapToDTO(anhSanPham, new AnhSanPhamDTO()))
                .orElseThrow(NotFoundException::new);
    }

    // Phương thức create ban đầu (nếu vẫn cần)
    public Long create(final MultipartFile file, final AnhSanPhamDTO anhSanPhamDTO) throws IOException {
        String imageUrl = saveFileAndGetUrl(file);
        anhSanPhamDTO.setUrlAnh(imageUrl);
        final AnhSanPham anhSanPham = new AnhSanPham();
        mapToEntity(anhSanPhamDTO, anhSanPham);
        return anhSanPhamRepository.save(anhSanPham).getId();
    }

    // PHƯƠNG THỨC MỚI ĐƯỢC GỌI TỪ CONTROLLER - ĐẢM BẢO PHƯƠNG THỨC NÀY TỒN TẠI
    public AnhSanPhamDTO createAndReturnDTO(final MultipartFile file, final AnhSanPhamDTO anhSanPhamDTO) throws IOException {
        String imageUrl = saveFileAndGetUrl(file);
        anhSanPhamDTO.setUrlAnh(imageUrl); // Gán URL đã tạo vào DTO
        final AnhSanPham anhSanPham = new AnhSanPham();
        mapToEntity(anhSanPhamDTO, anhSanPham);
        AnhSanPham savedAnhSanPham = anhSanPhamRepository.save(anhSanPham);
        return mapToDTO(savedAnhSanPham, new AnhSanPhamDTO()); // Trả về DTO đã được map từ entity đã lưu
    }

    public void update(final Long id, final AnhSanPhamDTO anhSanPhamDTO) {
        final AnhSanPham anhSanPham = anhSanPhamRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(anhSanPhamDTO, anhSanPham);
        anhSanPhamRepository.save(anhSanPham);
    }

    public void delete(final Long id) {
        // Trong thực tế, bạn cũng cần thêm logic để xóa file vật lý khỏi Cloud Storage
        // trước khi xóa bản ghi trong database.
        anhSanPhamRepository.deleteById(id);
    }

    // Phương thức mới để tạo AnhSanPham từ URL (không có MultipartFile)
    public AnhSanPhamDTO createFromUrl(final AnhSanPhamDTO anhSanPhamDTO) {
        final AnhSanPham anhSanPham = new AnhSanPham();
        mapToEntity(anhSanPhamDTO, anhSanPham);
        AnhSanPham savedAnhSanPham = anhSanPhamRepository.save(anhSanPham);
        return mapToDTO(savedAnhSanPham, new AnhSanPhamDTO());
    }
    /**
     * Phương thức giả định để lưu file và trả về URL.
     * Trong ứng dụng thực tế, bạn sẽ tích hợp với dịch vụ lưu trữ đám mây ở đây.
     *
     * @param file MultipartFile được tải lên
     * @return URL của file đã lưu
     * @throws IOException Nếu có lỗi trong quá trình lưu file
     */
    private String saveFileAndGetUrl(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không được rỗng.");
        }

        // Tạo thư mục UPLOAD_DIR nếu chưa tồn tại
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Tạo tên file duy nhất để tránh trùng lặp
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        // Lưu file vào thư mục tạm thời
        Files.copy(file.getInputStream(), filePath);

        // Trả về một URL giả định.
        // Trong môi trường production, đây sẽ là URL công khai từ Cloud Storage.
        // Ví dụ: "https://your-cloud-storage.com/images/" + fileName
        return "http://localhost:8080/uploads/" + fileName; // URL giả định
    }


    private AnhSanPhamDTO mapToDTO(final AnhSanPham anhSanPham, final AnhSanPhamDTO anhSanPhamDTO) {
        anhSanPhamDTO.setId(anhSanPham.getId());
        anhSanPhamDTO.setUrlAnh(anhSanPham.getUrlAnh());
        anhSanPhamDTO.setLaAnhDaiDien(anhSanPham.getLaAnhDaiDien());
        anhSanPhamDTO.setChiTietSpId(anhSanPham.getChiTietSp() == null ? null : anhSanPham.getChiTietSp().getId());
        return anhSanPhamDTO;
    }

    private AnhSanPham mapToEntity(final AnhSanPhamDTO anhSanPhamDTO, final AnhSanPham anhSanPham) {
        anhSanPham.setUrlAnh(anhSanPhamDTO.getUrlAnh());
        anhSanPham.setLaAnhDaiDien(anhSanPhamDTO.getLaAnhDaiDien());
        final ChiTietSanPham chiTietSp = anhSanPhamDTO.getChiTietSpId() == null ? null : chiTietSanPhamRepository.findById(anhSanPhamDTO.getChiTietSpId())
                .orElseThrow(() -> new NotFoundException("chiTietSp not found"));
        anhSanPham.setChiTietSp(chiTietSp);
        return anhSanPham;
    }
}
