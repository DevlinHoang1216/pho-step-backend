package fpt.duantotnghiep.sd28.service; // Đã thay đổi package

import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.LichSuHoaDon;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.dto.LichSuHoaDonDTO; // Đảm bảo đúng package của DTO
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.repo.LichSuHoaDonRepository;
import fpt.duantotnghiep.sd28.repo.NhanVienRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import java.util.List;
import java.util.UUID; // Thêm import UUID
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LichSuHoaDonService {

    private final LichSuHoaDonRepository lichSuHoaDonRepository;
    private final HoaDonRepository hoaDonRepository;
    private final NhanVienRepository nhanVienRepository;

    public LichSuHoaDonService(final LichSuHoaDonRepository lichSuHoaDonRepository,
                               final HoaDonRepository hoaDonRepository, final NhanVienRepository nhanVienRepository) {
        this.lichSuHoaDonRepository = lichSuHoaDonRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    public List<LichSuHoaDonDTO> findAll() {
        final List<LichSuHoaDon> lichSuHoaDons = lichSuHoaDonRepository.findAll(Sort.by("id"));
        return lichSuHoaDons.stream()
                .map(lichSuHoaDon -> mapToDTO(lichSuHoaDon, new LichSuHoaDonDTO()))
                .toList();
    }

    public LichSuHoaDonDTO get(final Long id) { // Sửa Integer thành Long
        return lichSuHoaDonRepository.findById(id)
                .map(lichSuHoaDon -> mapToDTO(lichSuHoaDon, new LichSuHoaDonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LichSuHoaDonDTO lichSuHoaDonDTO) { // Sửa Integer thành Long
        final LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        mapToEntity(lichSuHoaDonDTO, lichSuHoaDon);
        return lichSuHoaDonRepository.save(lichSuHoaDon).getId();
    }

    public void update(final Long id, final LichSuHoaDonDTO lichSuHoaDonDTO) { // Sửa Integer thành Long
        final LichSuHoaDon lichSuHoaDon = lichSuHoaDonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lichSuHoaDonDTO, lichSuHoaDon);
        lichSuHoaDonRepository.save(lichSuHoaDon);
    }

    public void delete(final Long id) { // Sửa Integer thành Long
        lichSuHoaDonRepository.deleteById(id);
    }

    private LichSuHoaDonDTO mapToDTO(final LichSuHoaDon lichSuHoaDon,
                                     final LichSuHoaDonDTO lichSuHoaDonDTO) {
        lichSuHoaDonDTO.setId(lichSuHoaDon.getId());
        lichSuHoaDonDTO.setHanhDong(lichSuHoaDon.getHanhDong());
        lichSuHoaDonDTO.setMoTaHanhDong(lichSuHoaDon.getMoTaHanhDong());
        lichSuHoaDonDTO.setThoiGian(lichSuHoaDon.getThoiGian());
        // Lấy ID của HoaDon (UUID)
        lichSuHoaDonDTO.setHoaDon(lichSuHoaDon.getHoaDon() == null ? null : lichSuHoaDon.getHoaDon().getId());
        // Lấy ID của NhanVienThucHien (Long)
        lichSuHoaDonDTO.setNhanVienThucHien(lichSuHoaDon.getNhanVienThucHien() == null ? null : lichSuHoaDon.getNhanVienThucHien().getId());
        return lichSuHoaDonDTO;
    }

    private LichSuHoaDon mapToEntity(final LichSuHoaDonDTO lichSuHoaDonDTO,
                                     final LichSuHoaDon lichSuHoaDon) {
        lichSuHoaDon.setHanhDong(lichSuHoaDonDTO.getHanhDong());
        lichSuHoaDon.setMoTaHanhDong(lichSuHoaDonDTO.getMoTaHanhDong());
        lichSuHoaDon.setThoiGian(lichSuHoaDonDTO.getThoiGian());
        // findById cho HoaDonRepository cần UUID
        final HoaDon hoaDon = lichSuHoaDonDTO.getHoaDon() == null ? null : hoaDonRepository.findById(lichSuHoaDonDTO.getHoaDon())
                .orElseThrow(() -> new NotFoundException("hoaDon not found"));
        lichSuHoaDon.setHoaDon(hoaDon);
        // findById cho NhanVienRepository cần Long
        final NhanVien nhanVienThucHien = lichSuHoaDonDTO.getNhanVienThucHien() == null ? null : nhanVienRepository.findById(lichSuHoaDonDTO.getNhanVienThucHien())
                .orElseThrow(() -> new NotFoundException("nhanVienThucHien not found"));
        lichSuHoaDon.setNhanVienThucHien(nhanVienThucHien);
        return lichSuHoaDon;
    }

}