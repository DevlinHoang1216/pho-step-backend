package fpt.duantotnghiep.sd28.service; // Đã thay đổi package

import fpt.duantotnghiep.sd28.entity.MaGiamGia;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.entity.PhieuGiamGia;
import fpt.duantotnghiep.sd28.dto.PhieuGiamGiaDTO;
import fpt.duantotnghiep.sd28.repo.MaGiamGiaRepository;
import fpt.duantotnghiep.sd28.repo.NhanVienRepository;
import fpt.duantotnghiep.sd28.repo.PhieuGiamGiaRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PhieuGiamGiaService {

    private final PhieuGiamGiaRepository phieuGiamGiaRepository;
    private final NhanVienRepository nhanVienRepository;
    private final MaGiamGiaRepository maGiamGiaRepository;

    public PhieuGiamGiaService(final PhieuGiamGiaRepository phieuGiamGiaRepository,
                               final NhanVienRepository nhanVienRepository,
                               final MaGiamGiaRepository maGiamGiaRepository) {
        this.phieuGiamGiaRepository = phieuGiamGiaRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.maGiamGiaRepository = maGiamGiaRepository;
    }

    public List<PhieuGiamGiaDTO> findAll() {
        final List<PhieuGiamGia> phieuGiamGias = phieuGiamGiaRepository.findAll(Sort.by("id"));
        return phieuGiamGias.stream()
                .map(phieuGiamGia -> mapToDTO(phieuGiamGia, new PhieuGiamGiaDTO()))
                .toList();
    }

    public PhieuGiamGiaDTO get(final UUID id) {
        return phieuGiamGiaRepository.findById(id)
                .map(phieuGiamGia -> mapToDTO(phieuGiamGia, new PhieuGiamGiaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final PhieuGiamGiaDTO phieuGiamGiaDTO) {
        final PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        mapToEntity(phieuGiamGiaDTO, phieuGiamGia);
        return phieuGiamGiaRepository.save(phieuGiamGia).getId();
    }

    public void update(final UUID id, final PhieuGiamGiaDTO phieuGiamGiaDTO) {
        final PhieuGiamGia phieuGiamGia = phieuGiamGiaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(phieuGiamGiaDTO, phieuGiamGia);
        phieuGiamGiaRepository.save(phieuGiamGia);
    }

    public void delete(final UUID id) {
        phieuGiamGiaRepository.deleteById(id);
    }

    private PhieuGiamGiaDTO mapToDTO(final PhieuGiamGia phieuGiamGia,
                                     final PhieuGiamGiaDTO phieuGiamGiaDTO) {
        phieuGiamGiaDTO.setId(phieuGiamGia.getId());
        phieuGiamGiaDTO.setMaPhieuGiamGia(phieuGiamGia.getMaPhieuGiamGia());
        phieuGiamGiaDTO.setTenPhieuGiamGia(phieuGiamGia.getTenPhieuGiamGia());
        phieuGiamGiaDTO.setLoaiGiamGia(phieuGiamGia.getLoaiGiamGia());
        phieuGiamGiaDTO.setGiaTriGiam(phieuGiamGia.getGiaTriGiam());
        phieuGiamGiaDTO.setHoaDonToiThieu(phieuGiamGia.getHoaDonToiThieu());
        phieuGiamGiaDTO.setSoTienGiamToiDa(phieuGiamGia.getSoTienGiamToiDa());
        phieuGiamGiaDTO.setNgayBatDau(phieuGiamGia.getNgayBatDau());
        phieuGiamGiaDTO.setNgayKetThuc(phieuGiamGia.getNgayKetThuc());
        phieuGiamGiaDTO.setTrangThaiPhieu(phieuGiamGia.getTrangThaiPhieu());
        phieuGiamGiaDTO.setLoaiApDung(phieuGiamGia.getLoaiApDung());
        phieuGiamGiaDTO.setNgayTao(phieuGiamGia.getNgayTao());
        phieuGiamGiaDTO.setNgayCapNhat(phieuGiamGia.getNgayCapNhat());
        phieuGiamGiaDTO.setNhanVienTao(phieuGiamGia.getNhanVienTao() == null ? null : phieuGiamGia.getNhanVienTao().getId()); // ID của NhanVien là Long
        return phieuGiamGiaDTO;
    }

    private PhieuGiamGia mapToEntity(final PhieuGiamGiaDTO phieuGiamGiaDTO,
                                     final PhieuGiamGia phieuGiamGia) {
        phieuGiamGia.setMaPhieuGiamGia(phieuGiamGiaDTO.getMaPhieuGiamGia());
        phieuGiamGia.setTenPhieuGiamGia(phieuGiamGiaDTO.getTenPhieuGiamGia());
        phieuGiamGia.setLoaiGiamGia(phieuGiamGiaDTO.getLoaiGiamGia());
        phieuGiamGia.setGiaTriGiam(phieuGiamGiaDTO.getGiaTriGiam());
        phieuGiamGia.setHoaDonToiThieu(phieuGiamGiaDTO.getHoaDonToiThieu());
        phieuGiamGia.setSoTienGiamToiDa(phieuGiamGiaDTO.getSoTienGiamToiDa());
        phieuGiamGia.setNgayBatDau(phieuGiamGiaDTO.getNgayBatDau());
        phieuGiamGia.setNgayKetThuc(phieuGiamGiaDTO.getNgayKetThuc());
        phieuGiamGia.setTrangThaiPhieu(phieuGiamGiaDTO.getTrangThaiPhieu());
        phieuGiamGia.setLoaiApDung(phieuGiamGiaDTO.getLoaiApDung());
        phieuGiamGia.setNgayTao(phieuGiamGiaDTO.getNgayTao());
        phieuGiamGia.setNgayCapNhat(phieuGiamGiaDTO.getNgayCapNhat());
        // Sửa kiểu của findById cho nhanVienRepository
        final NhanVien nhanVienTao = phieuGiamGiaDTO.getNhanVienTao() == null ? null : nhanVienRepository.findById(phieuGiamGiaDTO.getNhanVienTao()) // phieuGiamGiaDTO.getNhanVienTao() trả về Long
                .orElseThrow(() -> new NotFoundException("nhanVienTao not found"));
        phieuGiamGia.setNhanVienTao(nhanVienTao);
        return phieuGiamGia;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PhieuGiamGia phieuGiamGia = phieuGiamGiaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final MaGiamGia phieuGiamGiaMaGiamGia = maGiamGiaRepository.findFirstByPhieuGiamGia(phieuGiamGia);
        if (phieuGiamGiaMaGiamGia != null) {
            referencedWarning.setKey("phieuGiamGia.maGiamGia.phieuGiamGia.referenced");
            referencedWarning.addParam(phieuGiamGiaMaGiamGia.getId()); // ID của MaGiamGia là Long
            return referencedWarning;
        }
        return null;
    }

}