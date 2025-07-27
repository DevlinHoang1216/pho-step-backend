package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.DanhGiaSanPham;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.dto.DanhGiaSanPhamDTO;
import fpt.duantotnghiep.sd28.repo.DanhGiaSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.KhachHangRepository;
import fpt.duantotnghiep.sd28.repo.SanPhamRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DanhGiaSanPhamService {

    private final DanhGiaSanPhamRepository danhGiaSanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;

    public DanhGiaSanPhamService(final DanhGiaSanPhamRepository danhGiaSanPhamRepository,
                                 final KhachHangRepository khachHangRepository,
                                 final SanPhamRepository sanPhamRepository) {
        this.danhGiaSanPhamRepository = danhGiaSanPhamRepository;
        this.khachHangRepository = khachHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    public List<DanhGiaSanPhamDTO> findAll() {
        final List<DanhGiaSanPham> danhGiaSanPhams = danhGiaSanPhamRepository.findAll(Sort.by("id"));
        return danhGiaSanPhams.stream()
                .map(danhGiaSanPham -> mapToDTO(danhGiaSanPham, new DanhGiaSanPhamDTO()))
                .toList();
    }

    public DanhGiaSanPhamDTO get(final Long id) {
        return danhGiaSanPhamRepository.findById(id)
                .map(danhGiaSanPham -> mapToDTO(danhGiaSanPham, new DanhGiaSanPhamDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DanhGiaSanPhamDTO danhGiaSanPhamDTO) {
        final DanhGiaSanPham danhGiaSanPham = new DanhGiaSanPham();
        mapToEntity(danhGiaSanPhamDTO, danhGiaSanPham);
        return danhGiaSanPhamRepository.save(danhGiaSanPham).getId();
    }

    public void update(final Long id, final DanhGiaSanPhamDTO danhGiaSanPhamDTO) {
        final DanhGiaSanPham danhGiaSanPham = danhGiaSanPhamRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(danhGiaSanPhamDTO, danhGiaSanPham);
        danhGiaSanPhamRepository.save(danhGiaSanPham);
    }

    public void delete(final Long id) {
        danhGiaSanPhamRepository.deleteById(id);
    }

    private DanhGiaSanPhamDTO mapToDTO(final DanhGiaSanPham danhGiaSanPham,
                                       final DanhGiaSanPhamDTO danhGiaSanPhamDTO) {
        danhGiaSanPhamDTO.setId(danhGiaSanPham.getId());
        danhGiaSanPhamDTO.setSoSao(danhGiaSanPham.getSoSao());
        danhGiaSanPhamDTO.setNoiDung(danhGiaSanPham.getNoiDung());
        danhGiaSanPhamDTO.setNgayDanhGia(danhGiaSanPham.getNgayDanhGia());
        danhGiaSanPhamDTO.setTrangThai(danhGiaSanPham.getTrangThai());
        danhGiaSanPhamDTO.setNgayCapNhat(danhGiaSanPham.getNgayCapNhat());
        danhGiaSanPhamDTO.setKhachHang(danhGiaSanPham.getKhachHang() == null ? null : danhGiaSanPham.getKhachHang().getId());
        danhGiaSanPhamDTO.setSanPham(danhGiaSanPham.getSanPham() == null ? null : danhGiaSanPham.getSanPham().getId());
        return danhGiaSanPhamDTO;
    }

    private DanhGiaSanPham mapToEntity(final DanhGiaSanPhamDTO danhGiaSanPhamDTO,
                                       final DanhGiaSanPham danhGiaSanPham) {
        danhGiaSanPham.setSoSao(danhGiaSanPhamDTO.getSoSao());
        danhGiaSanPham.setNoiDung(danhGiaSanPhamDTO.getNoiDung());
        danhGiaSanPham.setNgayDanhGia(danhGiaSanPhamDTO.getNgayDanhGia());
        danhGiaSanPham.setTrangThai(danhGiaSanPhamDTO.getTrangThai());
        danhGiaSanPham.setNgayCapNhat(danhGiaSanPhamDTO.getNgayCapNhat());
        final KhachHang khachHang = danhGiaSanPhamDTO.getKhachHang() == null ? null : khachHangRepository.findById(danhGiaSanPhamDTO.getKhachHang())
                .orElseThrow(() -> new NotFoundException("khachHang not found"));
        danhGiaSanPham.setKhachHang(khachHang);
        final SanPham sanPham = danhGiaSanPhamDTO.getSanPham() == null ? null : sanPhamRepository.findById(danhGiaSanPhamDTO.getSanPham())
                .orElseThrow(() -> new NotFoundException("sanPham not found"));
        danhGiaSanPham.setSanPham(sanPham);
        return danhGiaSanPham;
    }
}