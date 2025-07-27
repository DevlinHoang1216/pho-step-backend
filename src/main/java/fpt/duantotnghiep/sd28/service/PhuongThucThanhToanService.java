// PhuongThucThanhToanService.java
package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChiTietThanhToan;
import fpt.duantotnghiep.sd28.entity.PhuongThucThanhToan;
import fpt.duantotnghiep.sd28.dto.PhuongThucThanhToanDTO;
import fpt.duantotnghiep.sd28.repo.ChiTietThanhToanRepository;
import fpt.duantotnghiep.sd28.repo.PhuongThucThanhToanRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PhuongThucThanhToanService {

    private final PhuongThucThanhToanRepository phuongThucThanhToanRepository;
    private final ChiTietThanhToanRepository chiTietThanhToanRepository;

    public PhuongThucThanhToanService(
            final PhuongThucThanhToanRepository phuongThucThanhToanRepository,
            final ChiTietThanhToanRepository chiTietThanhToanRepository) {
        this.phuongThucThanhToanRepository = phuongThucThanhToanRepository;
        this.chiTietThanhToanRepository = chiTietThanhToanRepository;
    }

    public List<PhuongThucThanhToanDTO> findAll() {
        final List<PhuongThucThanhToan> phuongThucThanhToans = phuongThucThanhToanRepository.findAll(Sort.by("id"));
        return phuongThucThanhToans.stream()
                .map(phuongThucThanhToan -> mapToDTO(phuongThucThanhToan, new PhuongThucThanhToanDTO()))
                .toList();
    }

    public PhuongThucThanhToanDTO get(final Long id) { // Changed to Long
        return phuongThucThanhToanRepository.findById(id) // Changed to Long
                .map(phuongThucThanhToan -> mapToDTO(phuongThucThanhToan, new PhuongThucThanhToanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PhuongThucThanhToanDTO phuongThucThanhToanDTO) { // Changed to Long
        final PhuongThucThanhToan phuongThucThanhToan = new PhuongThucThanhToan();
        mapToEntity(phuongThucThanhToanDTO, phuongThucThanhToan);
        return phuongThucThanhToanRepository.save(phuongThucThanhToan).getId();
    }

    public void update(final Long id, final PhuongThucThanhToanDTO phuongThucThanhToanDTO) { // Changed to Long
        final PhuongThucThanhToan phuongThucThanhToan = phuongThucThanhToanRepository.findById(id) // Changed to Long
                .orElseThrow(NotFoundException::new);
        mapToEntity(phuongThucThanhToanDTO, phuongThucThanhToan);
        phuongThucThanhToanRepository.save(phuongThucThanhToan);
    }

    public void delete(final Long id) { // Changed to Long
        phuongThucThanhToanRepository.deleteById(id); // Changed to Long
    }

    private PhuongThucThanhToanDTO mapToDTO(final PhuongThucThanhToan phuongThucThanhToan,
                                            final PhuongThucThanhToanDTO phuongThucThanhToanDTO) {
        phuongThucThanhToanDTO.setId(phuongThucThanhToan.getId());
        phuongThucThanhToanDTO.setTenPhuongThuc(phuongThucThanhToan.getTenPhuongThuc());
        phuongThucThanhToanDTO.setMoTa(phuongThucThanhToan.getMoTa());
        phuongThucThanhToanDTO.setNgayTao(phuongThucThanhToan.getNgayTao());
        phuongThucThanhToanDTO.setNgayCapNhat(phuongThucThanhToan.getNgayCapNhat());
        return phuongThucThanhToanDTO;
    }

    private PhuongThucThanhToan mapToEntity(final PhuongThucThanhToanDTO phuongThucThanhToanDTO,
                                            final PhuongThucThanhToan phuongThucThanhToan) {
        phuongThucThanhToan.setTenPhuongThuc(phuongThucThanhToanDTO.getTenPhuongThuc());
        phuongThucThanhToan.setMoTa(phuongThucThanhToanDTO.getMoTa());
        phuongThucThanhToan.setNgayTao(phuongThucThanhToanDTO.getNgayTao());
        phuongThucThanhToan.setNgayCapNhat(phuongThucThanhToanDTO.getNgayCapNhat());
        return phuongThucThanhToan;
    }

    public ReferencedWarning getReferencedWarning(final Long id) { // Changed to Long
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PhuongThucThanhToan phuongThucThanhToan = phuongThucThanhToanRepository.findById(id) // Changed to Long
                .orElseThrow(NotFoundException::new);
        final ChiTietThanhToan phuongThucThanhToanChiTietThanhToan = chiTietThanhToanRepository.findFirstByPhuongThucThanhToan(phuongThucThanhToan);
        if (phuongThucThanhToanChiTietThanhToan != null) {
            referencedWarning.setKey("phuongThucThanhToan.chiTietThanhToan.phuongThucThanhToan.referenced");
            referencedWarning.addParam(phuongThucThanhToanChiTietThanhToan.getId());
            return referencedWarning;
        }
        return null;
    }

}