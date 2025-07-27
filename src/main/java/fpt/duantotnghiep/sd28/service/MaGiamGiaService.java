package fpt.duantotnghiep.sd28.service; // Đã thay đổi package

import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.MaGiamGia;
import fpt.duantotnghiep.sd28.entity.PhieuGiamGia;
import fpt.duantotnghiep.sd28.dto.MaGiamGiaDTO;
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.repo.KhachHangRepository;
import fpt.duantotnghiep.sd28.repo.MaGiamGiaRepository;
import fpt.duantotnghiep.sd28.repo.PhieuGiamGiaRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import java.util.UUID; // Thêm import UUID
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MaGiamGiaService {

    private final MaGiamGiaRepository maGiamGiaRepository;
    private final PhieuGiamGiaRepository phieuGiamGiaRepository;
    private final KhachHangRepository khachHangRepository;
    private final HoaDonRepository hoaDonRepository;

    public MaGiamGiaService(final MaGiamGiaRepository maGiamGiaRepository,
                            final PhieuGiamGiaRepository phieuGiamGiaRepository,
                            final KhachHangRepository khachHangRepository,
                            final HoaDonRepository hoaDonRepository) {
        this.maGiamGiaRepository = maGiamGiaRepository;
        this.phieuGiamGiaRepository = phieuGiamGiaRepository;
        this.khachHangRepository = khachHangRepository;
        this.hoaDonRepository = hoaDonRepository;
    }

    public List<MaGiamGiaDTO> findAll() {
        final List<MaGiamGia> maGiamGias = maGiamGiaRepository.findAll(Sort.by("id"));
        return maGiamGias.stream()
                .map(maGiamGia -> mapToDTO(maGiamGia, new MaGiamGiaDTO()))
                .toList();
    }

    public MaGiamGiaDTO get(final Long id) { // Sửa Integer thành Long
        return maGiamGiaRepository.findById(id)
                .map(maGiamGia -> mapToDTO(maGiamGia, new MaGiamGiaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MaGiamGiaDTO maGiamGiaDTO) { // Sửa Integer thành Long
        final MaGiamGia maGiamGia = new MaGiamGia();
        mapToEntity(maGiamGiaDTO, maGiamGia);
        return maGiamGiaRepository.save(maGiamGia).getId();
    }

    public void update(final Long id, final MaGiamGiaDTO maGiamGiaDTO) { // Sửa Integer thành Long
        final MaGiamGia maGiamGia = maGiamGiaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(maGiamGiaDTO, maGiamGia);
        maGiamGiaRepository.save(maGiamGia);
    }

    public void delete(final Long id) { // Sửa Integer thành Long
        maGiamGiaRepository.deleteById(id);
    }

    private MaGiamGiaDTO mapToDTO(final MaGiamGia maGiamGia, final MaGiamGiaDTO maGiamGiaDTO) {
        maGiamGiaDTO.setId(maGiamGia.getId());
        maGiamGiaDTO.setMaCode(maGiamGia.getMaCode());
        maGiamGiaDTO.setDaSuDung(maGiamGia.getDaSuDung());
        maGiamGiaDTO.setIdHoaDonDaSuDung(maGiamGia.getIdHoaDonDaSuDung()); // Giữ nguyên UUID nếu HoaDon có ID là UUID
        maGiamGiaDTO.setNgayTao(maGiamGia.getNgayTao());
        maGiamGiaDTO.setNgaySuDung(maGiamGia.getNgaySuDung());
        maGiamGiaDTO.setPhieuGiamGia(maGiamGia.getPhieuGiamGia() == null ? null : maGiamGia.getPhieuGiamGia().getId()); // ID của PhieuGiamGia là UUID
        maGiamGiaDTO.setKhachHangDuocCap(maGiamGia.getKhachHangDuocCap() == null ? null : maGiamGia.getKhachHangDuocCap().getId()); // ID của KhachHang giả định là Long
        return maGiamGiaDTO;
    }

    private MaGiamGia mapToEntity(final MaGiamGiaDTO maGiamGiaDTO, final MaGiamGia maGiamGia) {
        maGiamGia.setMaCode(maGiamGiaDTO.getMaCode());
        maGiamGia.setDaSuDung(maGiamGiaDTO.getDaSuDung());
        maGiamGia.setIdHoaDonDaSuDung(maGiamGiaDTO.getIdHoaDonDaSuDung());
        maGiamGia.setNgayTao(maGiamGiaDTO.getNgayTao());
        maGiamGia.setNgaySuDung(maGiamGiaDTO.getNgaySuDung());
        // Sửa kiểu của findById cho phieuGiamGiaRepository
        final PhieuGiamGia phieuGiamGia = maGiamGiaDTO.getPhieuGiamGia() == null ? null : phieuGiamGiaRepository.findById(maGiamGiaDTO.getPhieuGiamGia()) // maGiamGiaDTO.getPhieuGiamGia() trả về UUID
                .orElseThrow(() -> new NotFoundException("phieuGiamGia not found"));
        maGiamGia.setPhieuGiamGia(phieuGiamGia);
        // Kiểm tra lại KhachHangRepository nếu ID của KhachHang không phải Long
        final KhachHang khachHangDuocCap = maGiamGiaDTO.getKhachHangDuocCap() == null ? null : khachHangRepository.findById(maGiamGiaDTO.getKhachHangDuocCap())
                .orElseThrow(() -> new NotFoundException("khachHangDuocCap not found"));
        maGiamGia.setKhachHangDuocCap(khachHangDuocCap);
        return maGiamGia;
    }

    public ReferencedWarning getReferencedWarning(final Long id) { // Sửa Integer thành Long
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final MaGiamGia maGiamGia = maGiamGiaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final HoaDon maGiamGiaHoaDon = hoaDonRepository.findFirstByMaGiamGia(maGiamGia);
        if (maGiamGiaHoaDon != null) {
            referencedWarning.setKey("maGiamGia.hoaDon.maGiamGia.referenced");
            referencedWarning.addParam(maGiamGiaHoaDon.getId()); // ID của HoaDon là UUID
            return referencedWarning;
        }
        return null;
    }

}