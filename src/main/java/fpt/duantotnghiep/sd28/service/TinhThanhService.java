package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.QuanHuyen;
import fpt.duantotnghiep.sd28.entity.TinhThanh;
import fpt.duantotnghiep.sd28.dto.TinhThanhDTO;
import fpt.duantotnghiep.sd28.repo.QuanHuyenRepository;
import fpt.duantotnghiep.sd28.repo.TinhThanhRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TinhThanhService {

    private final TinhThanhRepository tinhThanhRepository;
    private final QuanHuyenRepository quanHuyenRepository;

    public TinhThanhService(final TinhThanhRepository tinhThanhRepository,
                            final QuanHuyenRepository quanHuyenRepository) {
        this.tinhThanhRepository = tinhThanhRepository;
        this.quanHuyenRepository = quanHuyenRepository;
    }

    public List<TinhThanhDTO> findAll() {
        final List<TinhThanh> tinhThanhs = tinhThanhRepository.findAll(Sort.by("id"));
        return tinhThanhs.stream()
                .map(tinhThanh -> mapToDTO(tinhThanh, new TinhThanhDTO()))
                .toList();
    }

    public TinhThanhDTO get(final Long id) {
        return tinhThanhRepository.findById(id)
                .map(tinhThanh -> mapToDTO(tinhThanh, new TinhThanhDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TinhThanhDTO tinhThanhDTO) {
        final TinhThanh tinhThanh = new TinhThanh();
        mapToEntity(tinhThanhDTO, tinhThanh);
        return tinhThanhRepository.save(tinhThanh).getId();
    }

    public void update(final Long id, final TinhThanhDTO tinhThanhDTO) {
        final TinhThanh tinhThanh = tinhThanhRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tinhThanhDTO, tinhThanh);
        tinhThanhRepository.save(tinhThanh);
    }

    public void delete(final Long id) {
        tinhThanhRepository.deleteById(id);
    }

    private TinhThanhDTO mapToDTO(final TinhThanh tinhThanh, final TinhThanhDTO tinhThanhDTO) {
        tinhThanhDTO.setId(tinhThanh.getId());
        tinhThanhDTO.setTenTinhThanh(tinhThanh.getTenTinhThanh());
        tinhThanhDTO.setMaVung(tinhThanh.getMaVung());
        return tinhThanhDTO;
    }

    private TinhThanh mapToEntity(final TinhThanhDTO tinhThanhDTO, final TinhThanh tinhThanh) {
        tinhThanh.setTenTinhThanh(tinhThanhDTO.getTenTinhThanh());
        tinhThanh.setMaVung(tinhThanhDTO.getMaVung());
        return tinhThanh;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TinhThanh tinhThanh = tinhThanhRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final QuanHuyen tinhThanhQuanHuyen = quanHuyenRepository.findFirstByTinhThanh(tinhThanh);
        if (tinhThanhQuanHuyen != null) {
            referencedWarning.setKey("tinhThanh.quanHuyen.tinhThanh.referenced");
            referencedWarning.addParam(tinhThanhQuanHuyen.getId());
            return referencedWarning;
        }
        return null;
    }
}