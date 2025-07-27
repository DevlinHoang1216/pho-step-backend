package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.DiaChiKhachHang;
import fpt.duantotnghiep.sd28.entity.PhuongXa;
import fpt.duantotnghiep.sd28.entity.QuanHuyen;
import fpt.duantotnghiep.sd28.dto.PhuongXaDTO;
import fpt.duantotnghiep.sd28.repo.DiaChiKhachHangRepository;
import fpt.duantotnghiep.sd28.repo.PhuongXaRepository;
import fpt.duantotnghiep.sd28.repo.QuanHuyenRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PhuongXaService {

    private final PhuongXaRepository phuongXaRepository;
    private final QuanHuyenRepository quanHuyenRepository;
    private final DiaChiKhachHangRepository diaChiKhachHangRepository;

    public PhuongXaService(final PhuongXaRepository phuongXaRepository,
                           final QuanHuyenRepository quanHuyenRepository,
                           final DiaChiKhachHangRepository diaChiKhachHangRepository) {
        this.phuongXaRepository = phuongXaRepository;
        this.quanHuyenRepository = quanHuyenRepository;
        this.diaChiKhachHangRepository = diaChiKhachHangRepository;
    }

    public List<PhuongXaDTO> findAll() {
        final List<PhuongXa> phuongXas = phuongXaRepository.findAll(Sort.by("id"));
        return phuongXas.stream()
                .map(phuongXa -> mapToDTO(phuongXa, new PhuongXaDTO()))
                .toList();
    }

    public PhuongXaDTO get(final Long id) {
        return phuongXaRepository.findById(id)
                .map(phuongXa -> mapToDTO(phuongXa, new PhuongXaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PhuongXaDTO phuongXaDTO) {
        final PhuongXa phuongXa = new PhuongXa();
        mapToEntity(phuongXaDTO, phuongXa);
        return phuongXaRepository.save(phuongXa).getId();
    }

    public void update(final Long id, final PhuongXaDTO phuongXaDTO) {
        final PhuongXa phuongXa = phuongXaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(phuongXaDTO, phuongXa);
        phuongXaRepository.save(phuongXa);
    }

    public void delete(final Long id) {
        phuongXaRepository.deleteById(id);
    }

    private PhuongXaDTO mapToDTO(final PhuongXa phuongXa, final PhuongXaDTO phuongXaDTO) {
        phuongXaDTO.setId(phuongXa.getId());
        phuongXaDTO.setTenPhuongXa(phuongXa.getTenPhuongXa());
        phuongXaDTO.setMaXa(phuongXa.getMaXa());
        phuongXaDTO.setQuanHuyen(phuongXa.getQuanHuyen() == null ? null : phuongXa.getQuanHuyen().getId());
        return phuongXaDTO;
    }

    private PhuongXa mapToEntity(final PhuongXaDTO phuongXaDTO, final PhuongXa phuongXa) {
        phuongXa.setTenPhuongXa(phuongXaDTO.getTenPhuongXa());
        phuongXa.setMaXa(phuongXaDTO.getMaXa());
        final Long quanHuyenId = phuongXaDTO.getQuanHuyen();
        final QuanHuyen quanHuyen = quanHuyenId == null ? null : quanHuyenRepository.findById(quanHuyenId)
                .orElseThrow(() -> new NotFoundException("quanHuyen not found"));
        phuongXa.setQuanHuyen(quanHuyen);
        return phuongXa;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PhuongXa phuongXa = phuongXaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final DiaChiKhachHang phuongXaDiaChiKhachHang = diaChiKhachHangRepository.findFirstByPhuongXa(phuongXa);
        if (phuongXaDiaChiKhachHang != null) {
            referencedWarning.setKey("phuongXa.diaChiKhachHang.phuongXa.referenced");
            referencedWarning.addParam(phuongXaDiaChiKhachHang.getId());
            return referencedWarning;
        }
        return null;
    }
}