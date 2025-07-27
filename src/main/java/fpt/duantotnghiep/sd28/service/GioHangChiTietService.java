package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.GioHang;
import fpt.duantotnghiep.sd28.entity.GioHangChiTiet;
import fpt.duantotnghiep.sd28.dto.GioHangChiTietDTO;
import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.GioHangChiTietRepository;
import fpt.duantotnghiep.sd28.repo.GioHangRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GioHangChiTietService {

    private final GioHangChiTietRepository gioHangChiTietRepository;
    private final GioHangRepository gioHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    public GioHangChiTietService(final GioHangChiTietRepository gioHangChiTietRepository,
                                 final GioHangRepository gioHangRepository,
                                 final ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.gioHangChiTietRepository = gioHangChiTietRepository;
        this.gioHangRepository = gioHangRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
    }

    public List<GioHangChiTietDTO> findAll() {
        final List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findAll(Sort.by("id"));
        return gioHangChiTiets.stream()
                .map(gioHangChiTiet -> mapToDTO(gioHangChiTiet, new GioHangChiTietDTO()))
                .toList();
    }

    public GioHangChiTietDTO get(final Long id) {
        return gioHangChiTietRepository.findById(id)
                .map(gioHangChiTiet -> mapToDTO(gioHangChiTiet, new GioHangChiTietDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GioHangChiTietDTO gioHangChiTietDTO) {
        final GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        mapToEntity(gioHangChiTietDTO, gioHangChiTiet);
        return gioHangChiTietRepository.save(gioHangChiTiet).getId();
    }

    public void update(final Long id, final GioHangChiTietDTO gioHangChiTietDTO) {
        final GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(gioHangChiTietDTO, gioHangChiTiet);
        gioHangChiTietRepository.save(gioHangChiTiet);
    }

    public void delete(final Long id) {
        gioHangChiTietRepository.deleteById(id);
    }

    private GioHangChiTietDTO mapToDTO(final GioHangChiTiet gioHangChiTiet,
                                       final GioHangChiTietDTO gioHangChiTietDTO) {
        gioHangChiTietDTO.setId(gioHangChiTiet.getId());
        gioHangChiTietDTO.setSoLuong(gioHangChiTiet.getSoLuong());
        gioHangChiTietDTO.setGiaBanHienTai(gioHangChiTiet.getGiaBanHienTai());
        gioHangChiTietDTO.setNgayThemVao(gioHangChiTiet.getNgayThemVao());
        gioHangChiTietDTO.setGioHang(gioHangChiTiet.getGioHang() == null ? null : gioHangChiTiet.getGioHang().getId());
        gioHangChiTietDTO.setChiTietSp(gioHangChiTiet.getChiTietSp() == null ? null : gioHangChiTiet.getChiTietSp().getId());
        return gioHangChiTietDTO;
    }

    private GioHangChiTiet mapToEntity(final GioHangChiTietDTO gioHangChiTietDTO,
                                       final GioHangChiTiet gioHangChiTiet) {
        gioHangChiTiet.setSoLuong(gioHangChiTietDTO.getSoLuong());
        gioHangChiTiet.setGiaBanHienTai(gioHangChiTietDTO.getGiaBanHienTai());
        gioHangChiTiet.setNgayThemVao(gioHangChiTietDTO.getNgayThemVao());
        final GioHang gioHang = gioHangChiTietDTO.getGioHang() == null ? null : gioHangRepository.findById(gioHangChiTietDTO.getGioHang())
                .orElseThrow(() -> new NotFoundException("gioHang not found"));
        gioHangChiTiet.setGioHang(gioHang);
        final ChiTietSanPham chiTietSp = gioHangChiTietDTO.getChiTietSp() == null ? null : chiTietSanPhamRepository.findById(gioHangChiTietDTO.getChiTietSp())
                .orElseThrow(() -> new NotFoundException("chiTietSp not found"));
        gioHangChiTiet.setChiTietSp(chiTietSp);
        return gioHangChiTiet;
    }
}