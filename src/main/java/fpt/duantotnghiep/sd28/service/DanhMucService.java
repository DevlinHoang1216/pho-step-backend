package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.DanhMuc;
import fpt.duantotnghiep.sd28.dto.DanhMucDTO;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.repo.DanhMucRepository;
import fpt.duantotnghiep.sd28.repo.SanPhamRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DanhMucService {

    private final DanhMucRepository danhMucRepository;
    private final SanPhamRepository sanPhamRepository;

    public DanhMucService(DanhMucRepository danhMucRepository, SanPhamRepository sanPhamRepository) {
        this.danhMucRepository = danhMucRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    public List<DanhMucDTO> findAll() {
        // Đã sửa đổi để sử dụng phương thức findAllOrderedByName() từ repository
        // để lấy danh sách danh mục đã sắp xếp theo tên, phục vụ cho combobox
        List<DanhMuc> danhMucs = danhMucRepository.findAllOrderedByName();
        return danhMucs.stream()
                .map(danhMuc -> mapToDTO(danhMuc, new DanhMucDTO()))
                .toList();
    }

    public DanhMucDTO get(Long id) {
        return danhMucRepository.findById(id)
                .map(danhMuc -> mapToDTO(danhMuc, new DanhMucDTO()))
                .orElseThrow(() -> new NotFoundException("Danh mục không tồn tại"));
    }

    @Transactional
    public Long create(DanhMucDTO danhMucDTO) {
        DanhMuc danhMuc = new DanhMuc();
        mapToEntity(danhMucDTO, danhMuc);
        return danhMucRepository.save(danhMuc).getId();
    }

    @Transactional
    public void update(Long id, DanhMucDTO danhMucDTO) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Danh mục không tồn tại"));
        mapToEntity(danhMucDTO, danhMuc);
        danhMuc.setNgayCapNhat(LocalDateTime.now());
        danhMucRepository.save(danhMuc);
    }

    @Transactional
    public void delete(Long id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Danh mục không tồn tại"));
        danhMucRepository.delete(danhMuc);
    }

    private DanhMucDTO mapToDTO(DanhMuc danhMuc, DanhMucDTO danhMucDTO) {
        danhMucDTO.setId(danhMuc.getId());
        danhMucDTO.setTenDanhMuc(danhMuc.getTenDanhMuc());
        danhMucDTO.setMaDanhMuc(danhMuc.getMaDanhMuc());
        return danhMucDTO;
    }

    private DanhMuc mapToEntity(DanhMucDTO danhMucDTO, DanhMuc danhMuc) {
        danhMuc.setTenDanhMuc(danhMucDTO.getTenDanhMuc());
        danhMuc.setMaDanhMuc(danhMucDTO.getMaDanhMuc());
        return danhMuc;
    }

    public ReferencedWarning getReferencedWarning(Long id) {
        ReferencedWarning referencedWarning = new ReferencedWarning();
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Danh mục không tồn tại"));
        SanPham sanPham = sanPhamRepository.findFirstByDanhMuc(danhMuc);
        if (sanPham != null) {
            referencedWarning.setKey("danhMuc.sanPham.danhMuc.referenced");
            referencedWarning.addParam(sanPham.getId());
            return referencedWarning;
        }
        return null;
    }
}