package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChiTietThanhToan;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.PhuongThucThanhToan;
import fpt.duantotnghiep.sd28.dto.ChiTietThanhToanDTO;
import fpt.duantotnghiep.sd28.repo.ChiTietThanhToanRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.repo.PhuongThucThanhToanRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChiTietThanhToanService {

    private final ChiTietThanhToanRepository chiTietThanhToanRepository;
    private final HoaDonRepository hoaDonRepository;
    private final PhuongThucThanhToanRepository phuongThucThanhToanRepository;

    public ChiTietThanhToanService(final ChiTietThanhToanRepository chiTietThanhToanRepository,
                                   final HoaDonRepository hoaDonRepository,
                                   final PhuongThucThanhToanRepository phuongThucThanhToanRepository) {
        this.chiTietThanhToanRepository = chiTietThanhToanRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.phuongThucThanhToanRepository = phuongThucThanhToanRepository;
    }

    public List<ChiTietThanhToanDTO> findAll() {
        final List<ChiTietThanhToan> chiTietThanhToans = chiTietThanhToanRepository.findAll(Sort.by("id"));
        return chiTietThanhToans.stream()
                .map(chiTietThanhToan -> mapToDTO(chiTietThanhToan, new ChiTietThanhToanDTO()))
                .toList();
    }

    public ChiTietThanhToanDTO get(final UUID id) {
        return chiTietThanhToanRepository.findById(id)
                .map(chiTietThanhToan -> mapToDTO(chiTietThanhToan, new ChiTietThanhToanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ChiTietThanhToanDTO chiTietThanhToanDTO) {
        final ChiTietThanhToan chiTietThanhToan = new ChiTietThanhToan();
        mapToEntity(chiTietThanhToanDTO, chiTietThanhToan);
        return chiTietThanhToanRepository.save(chiTietThanhToan).getId();
    }

    public void update(final UUID id, final ChiTietThanhToanDTO chiTietThanhToanDTO) {
        final ChiTietThanhToan chiTietThanhToan = chiTietThanhToanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chiTietThanhToanDTO, chiTietThanhToan);
        chiTietThanhToanRepository.save(chiTietThanhToan);
    }

    public void delete(final UUID id) {
        chiTietThanhToanRepository.deleteById(id);
    }

    private ChiTietThanhToanDTO mapToDTO(final ChiTietThanhToan chiTietThanhToan,
                                         final ChiTietThanhToanDTO chiTietThanhToanDTO) {
        chiTietThanhToanDTO.setId(chiTietThanhToan.getId());
        chiTietThanhToanDTO.setSoTienThanhToan(chiTietThanhToan.getSoTienThanhToan());
        chiTietThanhToanDTO.setThoiGianThanhToan(chiTietThanhToan.getThoiGianThanhToan());
        chiTietThanhToanDTO.setTrangThaiThanhToan(chiTietThanhToan.getTrangThaiThanhToan());
        chiTietThanhToanDTO.setMaGiaoDichNganHang(chiTietThanhToan.getMaGiaoDichNganHang());
        chiTietThanhToanDTO.setNganHangThanhToan(chiTietThanhToan.getNganHangThanhToan());
        chiTietThanhToanDTO.setGhiChuThanhToan(chiTietThanhToan.getGhiChuThanhToan());
        chiTietThanhToanDTO.setNgayCapNhat(chiTietThanhToan.getNgayCapNhat());
        chiTietThanhToanDTO.setHoaDon(chiTietThanhToan.getHoaDon() == null ? null : chiTietThanhToan.getHoaDon().getId());
        chiTietThanhToanDTO.setPhuongThucThanhToan(chiTietThanhToan.getPhuongThucThanhToan() == null ? null : chiTietThanhToan.getPhuongThucThanhToan().getId());
        return chiTietThanhToanDTO;
    }

    private ChiTietThanhToan mapToEntity(final ChiTietThanhToanDTO chiTietThanhToanDTO,
                                         final ChiTietThanhToan chiTietThanhToan) {
        chiTietThanhToan.setSoTienThanhToan(chiTietThanhToanDTO.getSoTienThanhToan());
        chiTietThanhToan.setThoiGianThanhToan(chiTietThanhToanDTO.getThoiGianThanhToan());
        chiTietThanhToan.setTrangThaiThanhToan(chiTietThanhToanDTO.getTrangThaiThanhToan());
        chiTietThanhToan.setMaGiaoDichNganHang(chiTietThanhToanDTO.getMaGiaoDichNganHang());
        chiTietThanhToan.setNganHangThanhToan(chiTietThanhToanDTO.getNganHangThanhToan());
        chiTietThanhToan.setGhiChuThanhToan(chiTietThanhToanDTO.getGhiChuThanhToan());
        chiTietThanhToan.setNgayCapNhat(chiTietThanhToanDTO.getNgayCapNhat());
        final HoaDon hoaDon = chiTietThanhToanDTO.getHoaDon() == null ? null : hoaDonRepository.findById(chiTietThanhToanDTO.getHoaDon())
                .orElseThrow(() -> new NotFoundException("hoaDon not found"));
        chiTietThanhToan.setHoaDon(hoaDon);
        final PhuongThucThanhToan phuongThucThanhToan = chiTietThanhToanDTO.getPhuongThucThanhToan() == null ? null : phuongThucThanhToanRepository.findById(chiTietThanhToanDTO.getPhuongThucThanhToan())
                .orElseThrow(() -> new NotFoundException("phuongThucThanhToan not found"));
        chiTietThanhToan.setPhuongThucThanhToan(phuongThucThanhToan);
        return chiTietThanhToan;
    }
}