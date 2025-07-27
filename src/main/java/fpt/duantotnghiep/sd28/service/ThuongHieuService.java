package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.entity.ThuongHieu;
import fpt.duantotnghiep.sd28.dto.ThuongHieuDTO;
import fpt.duantotnghiep.sd28.repo.SanPhamRepository;
import fpt.duantotnghiep.sd28.repo.ThuongHieuRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ThuongHieuService {

    private final ThuongHieuRepository thuongHieuRepository;
    private final SanPhamRepository sanPhamRepository;

    public ThuongHieuService(final ThuongHieuRepository thuongHieuRepository,
                             final SanPhamRepository sanPhamRepository) {
        this.thuongHieuRepository = thuongHieuRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    public List<ThuongHieuDTO> findAll() {
        final List<ThuongHieu> thuongHieus = thuongHieuRepository.findAll(Sort.by("id"));
        return thuongHieus.stream()
                .map(thuongHieu -> mapToDTO(thuongHieu, new ThuongHieuDTO()))
                .toList();
    }

    public ThuongHieuDTO get(final Long id) {
        return thuongHieuRepository.findById(id)
                .map(thuongHieu -> mapToDTO(thuongHieu, new ThuongHieuDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ThuongHieuDTO thuongHieuDTO) {
        final ThuongHieu thuongHieu = new ThuongHieu();
        mapToEntity(thuongHieuDTO, thuongHieu);
        return thuongHieuRepository.save(thuongHieu).getId();
    }

    public void update(final Long id, final ThuongHieuDTO thuongHieuDTO) {
        final ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(thuongHieuDTO, thuongHieu);
        thuongHieuRepository.save(thuongHieu);
    }

    public void delete(final Long id) {
        thuongHieuRepository.deleteById(id);
    }

    private ThuongHieuDTO mapToDTO(final ThuongHieu thuongHieu, final ThuongHieuDTO thuongHieuDTO) {
        thuongHieuDTO.setId(thuongHieu.getId());
        thuongHieuDTO.setTenThuongHieu(thuongHieu.getTenThuongHieu());
        thuongHieuDTO.setMaThuongHieu(thuongHieu.getMaThuongHieu());

        return thuongHieuDTO;
    }

    private ThuongHieu mapToEntity(final ThuongHieuDTO thuongHieuDTO, final ThuongHieu thuongHieu) {
        thuongHieu.setTenThuongHieu(thuongHieuDTO.getTenThuongHieu());
        thuongHieu.setMaThuongHieu(thuongHieuDTO.getMaThuongHieu());
        return thuongHieu;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SanPham thuongHieuSanPham = sanPhamRepository.findFirstByThuongHieu(thuongHieu);
        if (thuongHieuSanPham != null) {
            referencedWarning.setKey("thuongHieu.sanPham.thuongHieu.referenced");
            referencedWarning.addParam(thuongHieuSanPham.getId());
            return referencedWarning;
        }
        return null;
    }
}