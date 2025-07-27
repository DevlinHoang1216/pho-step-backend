package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChucVu;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.dto.ChucVuDTO;
import fpt.duantotnghiep.sd28.repo.ChucVuRepository;
import fpt.duantotnghiep.sd28.repo.NhanVienRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChucVuService {

    private final ChucVuRepository chucVuRepository;
    private final NhanVienRepository nhanVienRepository;

    public ChucVuService(final ChucVuRepository chucVuRepository,
                         final NhanVienRepository nhanVienRepository) {
        this.chucVuRepository = chucVuRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    public List<ChucVuDTO> findAll() {
        final List<ChucVu> chucVus = chucVuRepository.findAll(Sort.by("id"));
        return chucVus.stream()
                .map(chucVu -> mapToDTO(chucVu, new ChucVuDTO()))
                .toList();
    }

    public ChucVuDTO get(final Long id) {
        return chucVuRepository.findById(id)
                .map(chucVu -> mapToDTO(chucVu, new ChucVuDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ChucVuDTO chucVuDTO) {
        final ChucVu chucVu = new ChucVu();
        mapToEntity(chucVuDTO, chucVu);
        return chucVuRepository.save(chucVu).getId();
    }

    public void update(final Long id, final ChucVuDTO chucVuDTO) {
        final ChucVu chucVu = chucVuRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chucVuDTO, chucVu);
        chucVuRepository.save(chucVu);
    }

    public void delete(final Long id) {
        chucVuRepository.deleteById(id);
    }

    private ChucVuDTO mapToDTO(final ChucVu chucVu, final ChucVuDTO chucVuDTO) {
        chucVuDTO.setId(chucVu.getId());
        chucVuDTO.setTenChucVu(chucVu.getTenChucVu());
        chucVuDTO.setMaChucVu(chucVu.getMaChucVu());
        chucVuDTO.setNgayTao(chucVu.getNgayTao());
        chucVuDTO.setNgayCapNhat(chucVu.getNgayCapNhat());
        return chucVuDTO;
    }

    private ChucVu mapToEntity(final ChucVuDTO chucVuDTO, final ChucVu chucVu) {
        chucVu.setTenChucVu(chucVuDTO.getTenChucVu());
        chucVu.setMaChucVu(chucVuDTO.getMaChucVu());
        chucVu.setNgayTao(chucVuDTO.getNgayTao());
        chucVu.setNgayCapNhat(chucVuDTO.getNgayCapNhat());
        return chucVu;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ChucVu chucVu = chucVuRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final NhanVien chucVuNhanVien = nhanVienRepository.findFirstByChucVu(chucVu);
        if (chucVuNhanVien != null) {
            referencedWarning.setKey("chucVu.nhanVien.chucVu.referenced");
            referencedWarning.addParam(chucVuNhanVien.getId());
            return referencedWarning;
        }
        return null;
    }
}