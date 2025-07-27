package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.dto.ChiTietSanPhamDTO;
import fpt.duantotnghiep.sd28.dto.SanPhamDTO;
import fpt.duantotnghiep.sd28.entity.*;
import fpt.duantotnghiep.sd28.repo.*;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final AnhSanPhamRepository anhSanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final ThuongHieuRepository thuongHieuRepository;
    private final MauSacRepository mauSacRepository;
    private final KichCoRepository kichCoRepository;
    private final ChatLieuRepository chatLieuRepository;

    public SanPhamService(SanPhamRepository sanPhamRepository,
                          ChiTietSanPhamRepository chiTietSanPhamRepository,
                          AnhSanPhamRepository anhSanPhamRepository,
                          DanhMucRepository danhMucRepository,
                          ThuongHieuRepository thuongHieuRepository,
                          MauSacRepository mauSacRepository,
                          KichCoRepository kichCoRepository,
                          ChatLieuRepository chatLieuRepository) {
        this.sanPhamRepository = sanPhamRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
        this.anhSanPhamRepository = anhSanPhamRepository;
        this.danhMucRepository = danhMucRepository;
        this.thuongHieuRepository = thuongHieuRepository;
        this.mauSacRepository = mauSacRepository;
        this.kichCoRepository = kichCoRepository;
        this.chatLieuRepository = chatLieuRepository;
    }

    public Page<SanPhamDTO> findAll(Pageable pageable) {
        return sanPhamRepository.findAllWithQuantity(pageable);
    }

    public SanPhamDTO get(final Long id) {
        return sanPhamRepository.findByIdWithQuantity(id)
                .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại"));
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại"));
        sanPham.setTrangThai(status);
        sanPham.setNgayCapNhat(LocalDateTime.now());
        sanPhamRepository.save(sanPham);
        if ("ngung_kinh_doanh".equals(status)) {
            chiTietSanPhamRepository.findBySanPham(sanPham).forEach(ctsp -> {
                if (!"ngung_kinh_doanh".equals(ctsp.getTrangThaiSanPhamRieng())) {
                    ctsp.setTrangThaiSanPhamRieng("ngung_kinh_doanh");
                    ctsp.setNgayCapNhat(LocalDateTime.now());
                    chiTietSanPhamRepository.save(ctsp);
                }
            });
        }
    }

    @Transactional
    public void delete(final Long id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại"));
        if (!"ngung_kinh_doanh".equals(sanPham.getTrangThai())) {
            sanPham.setTrangThai("ngung_kinh_doanh");
            sanPham.setNgayCapNhat(LocalDateTime.now());
            chiTietSanPhamRepository.findBySanPham(sanPham).forEach(ctsp -> {
                if (!"ngung_kinh_doanh".equals(ctsp.getTrangThaiSanPhamRieng())) {
                    ctsp.setTrangThaiSanPhamRieng("ngung_kinh_doanh");
                    ctsp.setNgayCapNhat(LocalDateTime.now());
                    chiTietSanPhamRepository.save(ctsp);
                }
            });

            sanPhamRepository.save(sanPham);
        }
    }

    public ReferencedWarning getReferencedWarning(Long id) {
        ReferencedWarning referencedWarning = new ReferencedWarning();
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại"));
        ChiTietSanPham sanPhamChiTietSanPham = chiTietSanPhamRepository.findFirstBySanPham(sanPham);
        if (sanPhamChiTietSanPham != null) {
            referencedWarning.setKey("sanPham.chiTietSanPham.sanPham.referenced");
            referencedWarning.addParam(sanPhamChiTietSanPham.getId());
            return referencedWarning;
        }
        return referencedWarning.hasWarnings() ? referencedWarning : null;
    }


    @Transactional
    public SanPhamDTO createProductWithDetails(SanPhamDTO sanPhamDto) {
        // 1. Validate và ánh xạ SanPhamDTO thành SanPham Entity
        SanPham sanPham = new SanPham();
        sanPham.setTenSanPham(sanPhamDto.getTenSanPham());
        sanPham.setMaSanPham(sanPhamDto.getMaSanPham());
        sanPham.setMoTaSanPham(sanPhamDto.getMoTaSanPham());
        sanPham.setUrlAnhDaiDien(sanPhamDto.getUrlAnhDaiDien());
        sanPham.setQuocGiaSanXuat(sanPhamDto.getQuocGiaSanXuat());
        sanPham.setTrangThai(sanPhamDto.getTrangThai() != null ? sanPhamDto.getTrangThai() : "dang_kinh_doanh");

        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDto.getDanhMuc())
                .orElseThrow(() -> new NotFoundException("Danh mục không tồn tại với ID: " + sanPhamDto.getDanhMuc()));
        sanPham.setDanhMuc(danhMuc);

        ThuongHieu thuongHieu = thuongHieuRepository.findById(sanPhamDto.getThuongHieu())
                .orElseThrow(() -> new NotFoundException("Thương hiệu không tồn tại với ID: " + sanPhamDto.getThuongHieu()));
        sanPham.setThuongHieu(thuongHieu);

        // Lưu SanPham để có ID tự tăng
        SanPham savedSanPham = sanPhamRepository.save(sanPham);

        // 2. Xử lý ChiTietSanPham và AnhSanPham
        if (sanPhamDto.getProductDetails() != null && !sanPhamDto.getProductDetails().isEmpty()) {
            for (ChiTietSanPhamDTO detailDto : sanPhamDto.getProductDetails()) {
                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                chiTietSanPham.setSanPham(savedSanPham);

                chiTietSanPham.setSoLuongTonKho(detailDto.getSoLuongTonKho());
                chiTietSanPham.setMoTaChiTiet(detailDto.getMoTaChiTiet());
                chiTietSanPham.setGiaNhap(detailDto.getGiaNhap());
                chiTietSanPham.setGiaBan(detailDto.getGiaBan());
                chiTietSanPham.setMaCtsp(detailDto.getMaCtsp());
                chiTietSanPham.setTrangThaiSanPhamRieng(detailDto.getTrangThaiSanPhamRieng() != null ? detailDto.getTrangThaiSanPhamRieng() : "dang_kinh_doanh");

                MauSac mauSac = mauSacRepository.findById(detailDto.getMauSac())
                        .orElseThrow(() -> new NotFoundException("Màu sắc không tồn tại với ID: " + detailDto.getMauSac()));
                chiTietSanPham.setMauSac(mauSac);

                KichCo kichCo = kichCoRepository.findById(detailDto.getKichCo())
                        .orElseThrow(() -> new NotFoundException("Kích cỡ không tồn tại với ID: " + detailDto.getKichCo()));
                chiTietSanPham.setKichCo(kichCo);

                if (detailDto.getChatLieu() != null) {
                    ChatLieu chatLieu = chatLieuRepository.findById(detailDto.getChatLieu())
                            .orElseThrow(() -> new NotFoundException("Chất liệu không tồn tại với ID: " + detailDto.getChatLieu()));
                    chiTietSanPham.setChatLieu(chatLieu);
                } else {
                    chiTietSanPham.setChatLieu(null);
                }

                ChiTietSanPham savedChiTietSanPham = chiTietSanPhamRepository.save(chiTietSanPham);

//                // Xử lý ảnh cho ChiTietSanPham
//                if (detailDto.getImages() != null && !detailDto.getImages().isEmpty()) {
//                    for (int i = 0; i < detailDto.getImages().size(); i++) {
//                        String imageUrl = detailDto.getImages().get(i);
//                        AnhSanPham anhSanPham = new AnhSanPham();
//                        anhSanPham.setChiTietSp(savedChiTietSanPham);
//                        anhSanPham.setUrlAnh(imageUrl);
//                        anhSanPham.setLaAnhDaiDien(i == 0);
//                        anhSanPhamRepository.save(anhSanPham);
//                    }
//                }
            }
            // Cập nhật trạng thái tổng thể của SanPham sau khi thêm các chi tiết
            // Gọi phương thức này sau khi tất cả chi tiết sản phẩm đã được lưu
            updateSanPhamStatusBasedOnChiTiet(savedSanPham);
        }

        // ***************************************************************
        // CẬP NHẬT ĐỂ ÁNH XẠ ĐẦY ĐỦ THÔNG TIN VỀ SanPhamDTO TRƯỚC KHI TRẢ VỀ
        // Lấy lại savedSanPham từ repository để đảm bảo các collection được load đầy đủ
        // ***************************************************************
        SanPham fullyLoadedSanPham = sanPhamRepository.findById(savedSanPham.getId())
                .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại sau khi tạo"));

        SanPhamDTO resultDto = new SanPhamDTO();
        resultDto.setId(fullyLoadedSanPham.getId());
        resultDto.setTenSanPham(fullyLoadedSanPham.getTenSanPham());
        resultDto.setMaSanPham(fullyLoadedSanPham.getMaSanPham());
        resultDto.setMoTaSanPham(fullyLoadedSanPham.getMoTaSanPham());
        resultDto.setUrlAnhDaiDien(fullyLoadedSanPham.getUrlAnhDaiDien());
        resultDto.setQuocGiaSanXuat(fullyLoadedSanPham.getQuocGiaSanXuat());
        resultDto.setTrangThai(fullyLoadedSanPham.getTrangThai());
        resultDto.setNgayTao(fullyLoadedSanPham.getNgayTao());
        resultDto.setNgayCapNhat(fullyLoadedSanPham.getNgayCapNhat());

        if (fullyLoadedSanPham.getDanhMuc() != null) {
            resultDto.setDanhMuc(fullyLoadedSanPham.getDanhMuc().getId());
        }
        if (fullyLoadedSanPham.getThuongHieu() != null) {
            resultDto.setThuongHieu(fullyLoadedSanPham.getThuongHieu().getId());
        }

        long totalQuantity = 0;
        if (fullyLoadedSanPham.getChiTietSanPhams() != null) {
            totalQuantity = fullyLoadedSanPham.getChiTietSanPhams().stream()
                    .filter(ctsp -> !"ngung_kinh_doanh".equals(ctsp.getTrangThaiSanPhamRieng()))
                    .mapToLong(ChiTietSanPham::getSoLuongTonKho)
                    .sum();
        }
        resultDto.setSoLuongTonKho(totalQuantity);

        if (fullyLoadedSanPham.getChiTietSanPhams() != null) {
            resultDto.setProductDetails(fullyLoadedSanPham.getChiTietSanPhams().stream()
                    .map(this::mapChiTietSanPhamToDTO)
                    .collect(Collectors.toList()));
        }

        return resultDto;
    }

    private void updateSanPhamStatusBasedOnChiTiet(SanPham sanPham) {
        long totalQuantity = chiTietSanPhamRepository.findBySanPham(sanPham).stream()
                .filter(ctsp -> !"ngung_kinh_doanh".equals(ctsp.getTrangThaiSanPhamRieng()))
                .mapToLong(ChiTietSanPham::getSoLuongTonKho)
                .sum();
        String newStatus = sanPham.getTrangThai();
        if (totalQuantity == 0) {
            newStatus = "het_hang";
        } else if ("het_hang".equals(sanPham.getTrangThai()) && totalQuantity > 0) {
            newStatus = "dang_kinh_doanh";
        }
        if (!"ngung_kinh_doanh".equals(sanPham.getTrangThai())) {
            sanPham.setTrangThai(newStatus);
        }
        sanPham.setNgayCapNhat(LocalDateTime.now());
        sanPhamRepository.save(sanPham);
    }

    private ChiTietSanPhamDTO mapChiTietSanPhamToDTO(ChiTietSanPham chiTietSanPham) {
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

//        if (chiTietSanPham.getAnhSanPhams() != null && !chiTietSanPham.getAnhSanPhams().isEmpty()) {
//            dto.setImages(chiTietSanPham.getAnhSanPhams().stream()
//                    .map(AnhSanPham::getUrlAnh)
//                    .collect(Collectors.toList()));
//        }

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
}
