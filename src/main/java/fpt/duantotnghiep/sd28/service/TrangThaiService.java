package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.dto.TrangThaiDTO;
import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.entity.TrangThai;

import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.SanPhamRepository;
import fpt.duantotnghiep.sd28.repo.TrangThaiRepository;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TrangThaiService {

    private final TrangThaiRepository trangThaiRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    public TrangThaiService(final TrangThaiRepository trangThaiRepository,
                            final SanPhamRepository sanPhamRepository,
                            final ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.trangThaiRepository = trangThaiRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
    }

    public List<TrangThaiDTO> findAll() {
        final List<TrangThai> trangThais = trangThaiRepository.findAll(Sort.by("id"));
        return trangThais.stream()
                .map(trangThai -> mapToDTO(trangThai, new TrangThaiDTO()))
                .toList();
    }

    public TrangThaiDTO get(final Long id) { // Đã đổi từ Integer sang Long
        return trangThaiRepository.findById(id)
                .map(trangThai -> mapToDTO(trangThai, new TrangThaiDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TrangThaiDTO trangThaiDTO) { // Đã đổi từ Integer sang Long
        final TrangThai trangThai = new TrangThai();
        mapToEntity(trangThaiDTO, trangThai);
        return trangThaiRepository.save(trangThai).getId();
    }

    public void update(final Long id, final TrangThaiDTO trangThaiDTO) { // Đã đổi từ Integer sang Long
        final TrangThai trangThai = trangThaiRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(trangThaiDTO, trangThai);
        trangThaiRepository.save(trangThai);
    }

    public void delete(final Long id) { // Đã đổi từ Integer sang Long
        trangThaiRepository.deleteById(id);
    }

    private TrangThaiDTO mapToDTO(final TrangThai trangThai, final TrangThaiDTO trangThaiDTO) {
        trangThaiDTO.setId(trangThai.getId());
        trangThaiDTO.setTenTrangThai(trangThai.getTenTrangThai());
        trangThaiDTO.setMoTa(trangThai.getMoTa());
        return trangThaiDTO;
    }

    private TrangThai mapToEntity(final TrangThaiDTO trangThaiDTO, final TrangThai trangThai) {
        trangThai.setTenTrangThai(trangThaiDTO.getTenTrangThai());
        trangThai.setMoTa(trangThaiDTO.getMoTa());
        return trangThai;
    }

    public ReferencedWarning getReferencedWarning(final Long id) { // Đã đổi từ Integer sang Long
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TrangThai trangThai = trangThaiRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SanPham trangThaiSanPham = sanPhamRepository.findFirstByTrangThai(trangThai);
        if (trangThaiSanPham != null) {
            referencedWarning.setKey("trangThai.sanPham.trangThai.referenced");
            referencedWarning.addParam(trangThaiSanPham.getId());
            return referencedWarning;
        }
        // Kiểm tra tham chiếu từ ChiTietSanPham
        final ChiTietSanPham trangThaiRiengChiTietSanPham = chiTietSanPhamRepository.findFirstByTrangThaiRieng(trangThai);
        if (trangThaiRiengChiTietSanPham != null) {
            referencedWarning.setKey("trangThai.chiTietSanPham.trangThaiRieng.referenced");
            referencedWarning.addParam(trangThaiRiengChiTietSanPham.getId());
            return referencedWarning;
        }
        return null; // Trả về null nếu không có cảnh báo
    }
}
