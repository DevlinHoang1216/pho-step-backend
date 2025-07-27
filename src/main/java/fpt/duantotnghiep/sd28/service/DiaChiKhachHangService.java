package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.DiaChiKhachHang;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.dto.DiaChiKhachHangDTO;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.PhuongXa;
import fpt.duantotnghiep.sd28.repo.DiaChiKhachHangRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.repo.KhachHangRepository;
import fpt.duantotnghiep.sd28.repo.PhuongXaRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DiaChiKhachHangService {

    private final DiaChiKhachHangRepository diaChiKhachHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final PhuongXaRepository phuongXaRepository;
    private final HoaDonRepository hoaDonRepository; // Thêm repository để kiểm tra tham chiếu

    public DiaChiKhachHangService(final DiaChiKhachHangRepository diaChiKhachHangRepository,
                                  final KhachHangRepository khachHangRepository,
                                  final PhuongXaRepository phuongXaRepository,
                                  final HoaDonRepository hoaDonRepository) {
        this.diaChiKhachHangRepository = diaChiKhachHangRepository;
        this.khachHangRepository = khachHangRepository;
        this.phuongXaRepository = phuongXaRepository;
        this.hoaDonRepository = hoaDonRepository;
    }

    public List<DiaChiKhachHangDTO> findAll() {
        final List<DiaChiKhachHang> diaChiKhachHangs = diaChiKhachHangRepository.findAll(Sort.by("id"));
        return diaChiKhachHangs.stream()
                .map(diaChiKhachHang -> mapToDTO(diaChiKhachHang, new DiaChiKhachHangDTO()))
                .toList();
    }

    public DiaChiKhachHangDTO get(final Long id) {
        return diaChiKhachHangRepository.findById(id)
                .map(diaChiKhachHang -> mapToDTO(diaChiKhachHang, new DiaChiKhachHangDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DiaChiKhachHangDTO diaChiKhachHangDTO) {
        final DiaChiKhachHang diaChiKhachHang = new DiaChiKhachHang();
        mapToEntity(diaChiKhachHangDTO, diaChiKhachHang);
        return diaChiKhachHangRepository.save(diaChiKhachHang).getId();
    }

    public void update(final Long id, final DiaChiKhachHangDTO diaChiKhachHangDTO) {
        final DiaChiKhachHang diaChiKhachHang = diaChiKhachHangRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(diaChiKhachHangDTO, diaChiKhachHang);
        diaChiKhachHangRepository.save(diaChiKhachHang);
    }

    public void delete(final Long id) {
        diaChiKhachHangRepository.deleteById(id);
    }

    private DiaChiKhachHangDTO mapToDTO(final DiaChiKhachHang diaChiKhachHang,
                                        final DiaChiKhachHangDTO diaChiKhachHangDTO) {
        diaChiKhachHangDTO.setId(diaChiKhachHang.getId());
        diaChiKhachHangDTO.setTenNguoiNhan(diaChiKhachHang.getTenNguoiNhan());
        diaChiKhachHangDTO.setSoDienThoaiNguoiNhan(diaChiKhachHang.getSoDienThoaiNguoiNhan());
        diaChiKhachHangDTO.setSoNhaTenDuong(diaChiKhachHang.getSoNhaTenDuong());
        diaChiKhachHangDTO.setGhiChu(diaChiKhachHang.getGhiChu());
        diaChiKhachHangDTO.setLaDiaChiMacDinh(diaChiKhachHang.getLaDiaChiMacDinh());
        diaChiKhachHangDTO.setNgayTao(diaChiKhachHang.getNgayTao());
        diaChiKhachHangDTO.setNgayCapNhat(diaChiKhachHang.getNgayCapNhat());
        diaChiKhachHangDTO.setKhachHang(diaChiKhachHang.getKhachHang() == null ? null : diaChiKhachHang.getKhachHang().getId());
        diaChiKhachHangDTO.setPhuongXa(diaChiKhachHang.getPhuongXa() == null ? null : diaChiKhachHang.getPhuongXa().getId());
        return diaChiKhachHangDTO;
    }

    private DiaChiKhachHang mapToEntity(final DiaChiKhachHangDTO diaChiKhachHangDTO,
                                        final DiaChiKhachHang diaChiKhachHang) {
        diaChiKhachHang.setTenNguoiNhan(diaChiKhachHangDTO.getTenNguoiNhan());
        diaChiKhachHang.setSoDienThoaiNguoiNhan(diaChiKhachHangDTO.getSoDienThoaiNguoiNhan());
        diaChiKhachHang.setSoNhaTenDuong(diaChiKhachHangDTO.getSoNhaTenDuong());
        diaChiKhachHang.setGhiChu(diaChiKhachHangDTO.getGhiChu());
        diaChiKhachHang.setLaDiaChiMacDinh(diaChiKhachHangDTO.getLaDiaChiMacDinh());
        diaChiKhachHang.setNgayTao(diaChiKhachHangDTO.getNgayTao());
        diaChiKhachHang.setNgayCapNhat(diaChiKhachHangDTO.getNgayCapNhat());
        final KhachHang khachHang = diaChiKhachHangDTO.getKhachHang() == null ? null : khachHangRepository.findById(diaChiKhachHangDTO.getKhachHang())
                .orElseThrow(() -> new NotFoundException("khachHang not found"));
        diaChiKhachHang.setKhachHang(khachHang);
        final PhuongXa phuongXa = diaChiKhachHangDTO.getPhuongXa() == null ? null : phuongXaRepository.findById(diaChiKhachHangDTO.getPhuongXa())
                .orElseThrow(() -> new NotFoundException("phuongXa not found"));
        diaChiKhachHang.setPhuongXa(phuongXa);
        return diaChiKhachHang;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final DiaChiKhachHang diaChiKhachHang = diaChiKhachHangRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final HoaDon hoaDon = hoaDonRepository.findFirstByDiaChiGiaoHang(diaChiKhachHang);
        if (hoaDon != null) {
            referencedWarning.setKey("diaChiKhachHang.hoaDon.diaChiGiaoHang.referenced");
            referencedWarning.addParam(hoaDon.getId());
            return referencedWarning;
        }
        return null;
    }
}