package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.HoaDonChiTiet;
import fpt.duantotnghiep.sd28.dto.HoaDonChiTietDTO;
import fpt.duantotnghiep.sd28.repo.ChiTietSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonChiTietRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HoaDonChiTietService {

    private final HoaDonChiTietRepository hoaDonChiTietRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    public HoaDonChiTietService(final HoaDonChiTietRepository hoaDonChiTietRepository,
                                final HoaDonRepository hoaDonRepository,
                                final ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.hoaDonChiTietRepository = hoaDonChiTietRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
    }

    public List<HoaDonChiTietDTO> findAll() {
        final List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findAll(Sort.by("id"));
        return hoaDonChiTiets.stream()
                .map(hoaDonChiTiet -> mapToDTO(hoaDonChiTiet, new HoaDonChiTietDTO()))
                .toList();
    }

    public HoaDonChiTietDTO get(final Long id) {
        return hoaDonChiTietRepository.findById(id)
                .map(hoaDonChiTiet -> mapToDTO(hoaDonChiTiet, new HoaDonChiTietDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final HoaDonChiTietDTO hoaDonChiTietDTO) {
        final HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
        mapToEntity(hoaDonChiTietDTO, hoaDonChiTiet);
        return hoaDonChiTietRepository.save(hoaDonChiTiet).getId();
    }

    public void update(final Long id, final HoaDonChiTietDTO hoaDonChiTietDTO) {
        final HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(hoaDonChiTietDTO, hoaDonChiTiet);
        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    public void delete(final Long id) {
        hoaDonChiTietRepository.deleteById(id);
    }

    private HoaDonChiTietDTO mapToDTO(final HoaDonChiTiet hoaDonChiTiet,
                                      final HoaDonChiTietDTO hoaDonChiTietDTO) {
        hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
        hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
        hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
        hoaDonChiTietDTO.setHoaDon(hoaDonChiTiet.getHoaDon() == null ? null : hoaDonChiTiet.getHoaDon().getId());
        hoaDonChiTietDTO.setChiTietSp(hoaDonChiTiet.getChiTietSp() == null ? null : hoaDonChiTiet.getChiTietSp().getId()); // Đã sửa
        return hoaDonChiTietDTO;
    }

    private HoaDonChiTiet mapToEntity(final HoaDonChiTietDTO hoaDonChiTietDTO,
                                      final HoaDonChiTiet hoaDonChiTiet) {
        hoaDonChiTiet.setSoLuong(hoaDonChiTietDTO.getSoLuong());
        hoaDonChiTiet.setDonGia(hoaDonChiTietDTO.getDonGia());
        final HoaDon hoaDon = hoaDonChiTietDTO.getHoaDon() == null ? null : hoaDonRepository.findById(hoaDonChiTietDTO.getHoaDon())
                .orElseThrow(() -> new NotFoundException("hoaDon not found"));
        hoaDonChiTiet.setHoaDon(hoaDon);
        final ChiTietSanPham chiTietSp = hoaDonChiTietDTO.getChiTietSp() == null ? null : chiTietSanPhamRepository.findById(hoaDonChiTietDTO.getChiTietSp())
                .orElseThrow(() -> new NotFoundException("chiTietSp not found"));
        hoaDonChiTiet.setChiTietSp(chiTietSp);
        return hoaDonChiTiet;
    }
}