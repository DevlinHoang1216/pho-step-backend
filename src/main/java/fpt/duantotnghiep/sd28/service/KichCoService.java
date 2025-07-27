package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.KichCo;
import fpt.duantotnghiep.sd28.dto.KichCoDTO;
import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.KichCoRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KichCoService {

    private final KichCoRepository kichCoRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    public KichCoService(final KichCoRepository kichCoRepository,
                         final ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.kichCoRepository = kichCoRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
    }

    public List<KichCoDTO> findAll() {
        return kichCoRepository.findAllOrderedByName()
                .stream()
                .map(kichCo -> mapToDTO(kichCo, new KichCoDTO()))
                .collect(Collectors.toList());
    }

    public KichCoDTO get(final Long id) {
        return mapToDTO(kichCoRepository.findById(id)
                .orElseThrow(NotFoundException::new), new KichCoDTO());
    }

    public Long create(final KichCoDTO kichCoDTO) {
        // 1. Kiểm tra tính duy nhất của maKichCo
        if (kichCoRepository.existsByMaKichCo(kichCoDTO.getMaKichCo())) {
            throw new IllegalArgumentException("Mã kích cỡ '" + kichCoDTO.getMaKichCo() + "' đã tồn tại.");
        }
        // 2. Tạo đối tượng KichCo và gán giá trị từ DTO
        KichCo kichCo = new KichCo();
        mapToEntity(kichCoDTO, kichCo); // Đây là nơi tenKichCo và maKichCo được gán
        kichCo.setNgayTao(java.time.LocalDateTime.now());
        kichCoRepository.save(kichCo);
        return kichCo.getId();
    }

    public void update(final Long id, final KichCoDTO kichCoDTO) {
        final KichCo kichCo = kichCoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        if (!kichCo.getMaKichCo().equals(kichCoDTO.getMaKichCo()) &&
                kichCoRepository.existsByMaKichCo(kichCoDTO.getMaKichCo())) {
            throw new IllegalArgumentException("Mã kích cỡ '" + kichCoDTO.getMaKichCo() + "' đã tồn tại.");
        }
        mapToEntity(kichCoDTO, kichCo);
        kichCo.setNgayCapNhat(java.time.LocalDateTime.now());
        kichCoRepository.save(kichCo);
    }

    public void delete(final Long id) {
        kichCoRepository.deleteById(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final KichCo kichCo = kichCoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final ChiTietSanPham kichCoChiTietSanPham = chiTietSanPhamRepository.findFirstByKichCo(kichCo);
        if (kichCoChiTietSanPham != null) {
            referencedWarning.addWarning("Kích cỡ này đang được sử dụng bởi sản phẩm và không thể xóa.", 1);
        }
        return referencedWarning;
    }

    private KichCoDTO mapToDTO(final KichCo kichCo, final KichCoDTO kichCoDTO) {
        kichCoDTO.setId(kichCo.getId());
        kichCoDTO.setTenKichCo(kichCo.getTenKichCo());
        kichCoDTO.setMaKichCo(kichCo.getMaKichCo());
        kichCoDTO.setNgayTao(kichCo.getNgayTao());
        kichCoDTO.setNgayCapNhat(kichCo.getNgayCapNhat());
        return kichCoDTO;
    }

    private KichCo mapToEntity(final KichCoDTO kichCoDTO, final KichCo kichCo) {
        kichCo.setTenKichCo(kichCoDTO.getTenKichCo());
        kichCo.setMaKichCo(kichCoDTO.getMaKichCo());
        return kichCo;
    }
}