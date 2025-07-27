package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.dto.ChiTietSanPhamDTO;
import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.ChatLieu;
import fpt.duantotnghiep.sd28.entity.KichCo;
import fpt.duantotnghiep.sd28.entity.MauSac;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.entity.AnhSanPham; // Đảm bảo import AnhSanPham
import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.ChatLieuRepository;
import fpt.duantotnghiep.sd28.repo.KichCoRepository;
import fpt.duantotnghiep.sd28.repo.MauSacRepository;
import fpt.duantotnghiep.sd28.repo.SanPhamRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChiTietSanPhamService {

    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ChatLieuRepository chatLieuRepository;
    private final MauSacRepository mauSacRepository;
    private final KichCoRepository kichCoRepository;

    public ChiTietSanPhamService(ChiTietSanPhamRepository chiTietSanPhamRepository,
                                 SanPhamRepository sanPhamRepository,
                                 ChatLieuRepository chatLieuRepository,
                                 MauSacRepository mauSacRepository,
                                 KichCoRepository kichCoRepository) {
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.chatLieuRepository = chatLieuRepository;
        this.mauSacRepository = mauSacRepository;
        this.kichCoRepository = kichCoRepository;
    }

    public List<ChiTietSanPhamDTO> findAll() {
        return chiTietSanPhamRepository.findAllWithDetails().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ChiTietSanPhamDTO> findBySanPhamId(Long sanPhamId) {
        return chiTietSanPhamRepository.findBySanPhamId(sanPhamId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ChiTietSanPhamDTO> findByKeyword(String keyword) {
        return chiTietSanPhamRepository.findByKeyword(keyword).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ChiTietSanPhamDTO> findByChatLieuId(Long chatLieuId) {
        return chiTietSanPhamRepository.findByChatLieuId(chatLieuId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ChiTietSanPhamDTO> findByMauSacId(Long mauSacId) {
        return chiTietSanPhamRepository.findByMauSacId(mauSacId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ChiTietSanPhamDTO> findByKichCoId(Long kichCoId) {
        return chiTietSanPhamRepository.findByKichCoId(kichCoId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Cập nhật phương thức findByFilters để bao gồm thương hiệu và danh mục
    public List<ChiTietSanPhamDTO> findByFilters(Long sanPhamId, Long thuongHieuId, Long danhMucId, Long chatLieuId, Long mauSacId, Long kichCoId, String keyword) {
        return chiTietSanPhamRepository.findByFilters(sanPhamId, thuongHieuId, danhMucId, chatLieuId, mauSacId, kichCoId, keyword).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ChiTietSanPhamDTO get(UUID id) {
        // Sử dụng truy vấn mới để load AnhSanPham cùng lúc
        return chiTietSanPhamRepository.findByIdWithDetailsAndImages(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại"));
    }

    @Transactional
    public UUID create(final ChiTietSanPhamDTO chiTietSanPhamDTO) {
        final ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
        mapToEntity(chiTietSanPhamDTO, chiTietSanPham);
        ChiTietSanPham savedChiTietSanPham = chiTietSanPhamRepository.save(chiTietSanPham);
        updateSanPhamStatus(savedChiTietSanPham.getSanPham());
        return savedChiTietSanPham.getId();
    }

    @Transactional
    public void updateTrangThaiSanPhamRieng(UUID id, String trangThaiSanPhamRieng) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (trangThaiSanPhamRieng == null || !trangThaiSanPhamRieng.matches("dang_kinh_doanh|ngung_kinh_doanh|het_hang")) {
            throw new IllegalArgumentException("trangThaiSanPhamRieng must be 'dang_kinh_doanh', 'ngung_kinh_doanh', or 'het_hang'");
        }
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại"));
        if (trangThaiSanPhamRieng.equals("het_hang") && chiTietSanPham.getSoLuongTonKho() > 0) {
            throw new IllegalArgumentException("Không thể đặt trạng thái 'het_hang' khi số lượng tồn kho lớn hơn 0");
        }
        chiTietSanPham.setTrangThaiSanPhamRieng(trangThaiSanPhamRieng);
        chiTietSanPham.setNgayCapNhat(LocalDateTime.now());
        chiTietSanPhamRepository.save(chiTietSanPham);
        updateSanPhamStatus(chiTietSanPham.getSanPham());
    }

    @Transactional
    public void toggleStatus(UUID id, boolean active) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại"));
        String newStatus;
        if (chiTietSanPham.getSoLuongTonKho() == 0 && active) {
            throw new IllegalArgumentException("Không thể đặt trạng thái 'dang_kinh_doanh' khi số lượng tồn kho bằng 0");
        } else if (chiTietSanPham.getSoLuongTonKho() == 0) {
            newStatus = "het_hang";
        } else {
            newStatus = active ? "dang_kinh_doanh" : "ngung_kinh_doanh";
        }
        chiTietSanPham.setTrangThaiSanPhamRieng(newStatus);
        chiTietSanPham.setNgayCapNhat(LocalDateTime.now());
        chiTietSanPhamRepository.save(chiTietSanPham);
        updateSanPhamStatus(chiTietSanPham.getSanPham());
    }

    @Transactional
    public void update(final UUID id, final ChiTietSanPhamDTO chiTietSanPhamDTO) {
        final ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại"));
        mapToEntity(chiTietSanPhamDTO, chiTietSanPham);
        chiTietSanPhamRepository.save(chiTietSanPham);
        updateSanPhamStatus(chiTietSanPham.getSanPham());
    }

    @Transactional
    public void delete(final UUID id) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại"));
        chiTietSanPham.setTrangThaiSanPhamRieng("ngung_kinh_doanh");
        chiTietSanPham.setNgayCapNhat(LocalDateTime.now());
        chiTietSanPhamRepository.save(chiTietSanPham);
        updateSanPhamStatus(chiTietSanPham.getSanPham());
    }

    private void updateSanPhamStatus(SanPham sanPham) {
        long totalQuantity = chiTietSanPhamRepository.findBySanPham(sanPham).stream()
                .filter(ctsp -> !"ngung_kinh_doanh".equals(ctsp.getTrangThaiSanPhamRieng()))
                .mapToLong(ChiTietSanPham::getSoLuongTonKho)
                .sum();
        String newStatus = totalQuantity == 0 ? "het_hang" : sanPham.getTrangThai().equals("ngung_kinh_doanh") ? "ngung_kinh_doanh" : "dang_kinh_doanh";
        sanPham.setTrangThai(newStatus);
        sanPham.setNgayCapNhat(LocalDateTime.now());
        sanPhamRepository.save(sanPham);
    }

    private ChiTietSanPhamDTO mapToDto(final ChiTietSanPham chiTietSanPham) {
        ChiTietSanPhamDTO dto = ChiTietSanPhamDTO.builder()
                .id(chiTietSanPham.getId())
                .soLuongTonKho(chiTietSanPham.getSoLuongTonKho())
                .moTaChiTiet(chiTietSanPham.getMoTaChiTiet())
                .giaNhap(chiTietSanPham.getGiaNhap())
                .giaBan(chiTietSanPham.getGiaBan())
                .maCtsp(chiTietSanPham.getMaCtsp())
                .ngayNhap(chiTietSanPham.getNgayNhap())
                .trangThaiSanPhamRieng(chiTietSanPham.getTrangThaiSanPhamRieng())
                .ngayTao(chiTietSanPham.getNgayTao())
                .ngayCapNhat(chiTietSanPham.getNgayCapNhat())
                .chatLieu(chiTietSanPham.getChatLieu() == null ? null : chiTietSanPham.getChatLieu().getId())
                .mauSac(chiTietSanPham.getMauSac() == null ? null : chiTietSanPham.getMauSac().getId())
                .kichCo(chiTietSanPham.getKichCo() == null ? null : chiTietSanPham.getKichCo().getId())
                .sanPham(chiTietSanPham.getSanPham() == null ? null : chiTietSanPham.getSanPham().getId())
                .build();

        // Ánh xạ các URL ảnh từ AnhSanPham entities
        // Đảm bảo collection được khởi tạo trước khi truy cập
        if (chiTietSanPham.getAnhSanPhams() != null && !chiTietSanPham.getAnhSanPhams().isEmpty()) {
            dto.setImages(chiTietSanPham.getAnhSanPhams().stream()
                    .map(anh -> anh.getUrlAnh()) // Đã thay đổi method reference thành lambda tường minh
                    .collect(Collectors.toList()));
        }

        if (chiTietSanPham.getSanPham() != null) {
            dto.setTenSanPham(chiTietSanPham.getSanPham().getTenSanPham());
            if (chiTietSanPham.getSanPham().getThuongHieu() != null) {
                dto.setTenThuongHieu(chiTietSanPham.getSanPham().getThuongHieu().getTenThuongHieu());
            }
            if (chiTietSanPham.getSanPham().getDanhMuc() != null) {
                dto.setTenDanhMuc(chiTietSanPham.getSanPham().getDanhMuc().getTenDanhMuc());
            }
        }
        if (chiTietSanPham.getChatLieu() != null) {
            dto.setTenChatLieu(chiTietSanPham.getChatLieu().getTenChatLieu());
        }
        if (chiTietSanPham.getMauSac() != null) {
            dto.setTenMauSac(chiTietSanPham.getMauSac().getTenMauSac());
        }
        if (chiTietSanPham.getKichCo() != null) {
            dto.setTenKichCo(chiTietSanPham.getKichCo().getTenKichCo());
        }
        return dto;
    }

    private ChiTietSanPham mapToEntity(final ChiTietSanPhamDTO chiTietSanPhamDTO,
                                       final ChiTietSanPham chiTietSanPham) {
        chiTietSanPham.setSoLuongTonKho(chiTietSanPhamDTO.getSoLuongTonKho());
        chiTietSanPham.setMoTaChiTiet(chiTietSanPhamDTO.getMoTaChiTiet());
        chiTietSanPham.setGiaNhap(chiTietSanPhamDTO.getGiaNhap());
        chiTietSanPham.setGiaBan(chiTietSanPhamDTO.getGiaBan());
        chiTietSanPham.setMaCtsp(chiTietSanPhamDTO.getMaCtsp());
        chiTietSanPham.setNgayNhap(chiTietSanPhamDTO.getNgayNhap());
        String trangThai = chiTietSanPhamDTO.getTrangThaiSanPhamRieng();
        if (trangThai != null && trangThai.matches("dang_kinh_doanh|ngung_kinh_doanh|het_hang")) {
            if (trangThai.equals("het_hang") && chiTietSanPhamDTO.getSoLuongTonKho() > 0) {
                throw new IllegalArgumentException("Không thể đặt trạng thái 'het_hang' khi số lượng tồn kho lớn hơn 0");
            }
            chiTietSanPham.setTrangThaiSanPhamRieng(trangThai);
        } else {
            chiTietSanPham.setTrangThaiSanPhamRieng("dang_kinh_doanh");
        }

        final SanPham sanPham = chiTietSanPhamDTO.getSanPham() == null ? null : sanPhamRepository.findById(chiTietSanPhamDTO.getSanPham())
                .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại"));
        chiTietSanPham.setSanPham(sanPham);
        final ChatLieu chatLieu = chiTietSanPhamDTO.getChatLieu() == null ? null : chatLieuRepository.findById(chiTietSanPhamDTO.getChatLieu())
                .orElseThrow(() -> new NotFoundException("Chất liệu không tồn tại"));
        chiTietSanPham.setChatLieu(chatLieu);
        final MauSac mauSac = chiTietSanPhamDTO.getMauSac() == null ? null : mauSacRepository.findById(chiTietSanPhamDTO.getMauSac())
                .orElseThrow(() -> new NotFoundException("Màu sắc không tồn tại"));
        chiTietSanPham.setMauSac(mauSac);
        final KichCo kichCo = chiTietSanPhamDTO.getKichCo() == null ? null : kichCoRepository.findById(chiTietSanPhamDTO.getKichCo())
                .orElseThrow(() -> new NotFoundException("Kích cỡ không tồn tại"));
        chiTietSanPham.setKichCo(kichCo);

        if (chiTietSanPham.getId() == null) {
            chiTietSanPham.setNgayTao(LocalDateTime.now());
        }
        chiTietSanPham.setNgayCapNhat(LocalDateTime.now());

        return chiTietSanPham;
    }

    public ReferencedWarning getReferencedWarning(UUID id) {
        ReferencedWarning referencedWarning = new ReferencedWarning();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chi tiết sản phẩm không tồn tại"));
        if (!chiTietSanPham.getAnhSanPhams().isEmpty()) {
            referencedWarning.addWarning("Ảnh sản phẩm", chiTietSanPham.getAnhSanPhams().size());
        }
//        if (!chiTietSanPham.getHoaDonChiTiets().isEmpty()) {
//            referencedWarning.addWarning("Hóa đơn chi tiết", chiTietSanPham.getHoaDonChiTiets().size());
//        }
//        if (!chiTietSanPham.getGioHangChiTiets().isEmpty()) {
//            referencedWarning.addWarning("Giỏ hàng chi tiết", chiTietSanPham.getGioHangChiTiets().size());
//        }
        return referencedWarning.hasWarnings() ? referencedWarning : null;
    }
}