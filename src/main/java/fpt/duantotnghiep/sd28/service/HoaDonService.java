package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.ChiTietThanhToan;
import fpt.duantotnghiep.sd28.entity.DiaChiKhachHang;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.HoaDonChiTiet;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.LichSuHoaDon;
import fpt.duantotnghiep.sd28.entity.MaGiamGia;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.dto.HoaDonDTO;
import fpt.duantotnghiep.sd28.repo.ChiTietThanhToanRepository;
import fpt.duantotnghiep.sd28.repo.DiaChiKhachHangRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonChiTietRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.repo.KhachHangRepository;
import fpt.duantotnghiep.sd28.repo.LichSuHoaDonRepository;
import fpt.duantotnghiep.sd28.repo.MaGiamGiaRepository;
import fpt.duantotnghiep.sd28.repo.NhanVienRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HoaDonService {

    private final HoaDonRepository hoaDonRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final MaGiamGiaRepository maGiamGiaRepository;
    private final DiaChiKhachHangRepository diaChiKhachHangRepository;
    private final HoaDonChiTietRepository hoaDonChiTietRepository;
    private final ChiTietThanhToanRepository chiTietThanhToanRepository;
    private final LichSuHoaDonRepository lichSuHoaDonRepository;

    public HoaDonService(final HoaDonRepository hoaDonRepository,
                         final KhachHangRepository khachHangRepository,
                         final NhanVienRepository nhanVienRepository,
                         final MaGiamGiaRepository maGiamGiaRepository,
                         final DiaChiKhachHangRepository diaChiKhachHangRepository,
                         final HoaDonChiTietRepository hoaDonChiTietRepository,
                         final ChiTietThanhToanRepository chiTietThanhToanRepository,
                         final LichSuHoaDonRepository lichSuHoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
        this.khachHangRepository = khachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.maGiamGiaRepository = maGiamGiaRepository;
        this.diaChiKhachHangRepository = diaChiKhachHangRepository;
        this.hoaDonChiTietRepository = hoaDonChiTietRepository;
        this.chiTietThanhToanRepository = chiTietThanhToanRepository;
        this.lichSuHoaDonRepository = lichSuHoaDonRepository;
    }

    public List<HoaDonDTO> findAll() {
        final List<HoaDon> hoaDons = hoaDonRepository.findAll(Sort.by("id"));
        return hoaDons.stream()
                .map(hoaDon -> mapToDTO(hoaDon, new HoaDonDTO()))
                .toList();
    }

    public HoaDonDTO get(final UUID id) {
        return hoaDonRepository.findById(id)
                .map(hoaDon -> mapToDTO(hoaDon, new HoaDonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final HoaDonDTO hoaDonDTO) {
        final HoaDon hoaDon = new HoaDon();
        mapToEntity(hoaDonDTO, hoaDon);
        return hoaDonRepository.save(hoaDon).getId();
    }

    public void update(final UUID id, final HoaDonDTO hoaDonDTO) {
        final HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(hoaDonDTO, hoaDon);
        hoaDonRepository.save(hoaDon);
    }

    public void delete(final UUID id) {
        hoaDonRepository.deleteById(id);
    }

    private HoaDonDTO mapToDTO(final HoaDon hoaDon, final HoaDonDTO hoaDonDTO) {
        hoaDonDTO.setId(hoaDon.getId());
        hoaDonDTO.setMaHoaDon(hoaDon.getMaHoaDon());
        hoaDonDTO.setNgayTao(hoaDon.getNgayTao());
        hoaDonDTO.setNgayThanhToan(hoaDon.getNgayThanhToan());
        hoaDonDTO.setTongTienSanPham(hoaDon.getTongTienSanPham());
        hoaDonDTO.setPhiVanChuyen(hoaDon.getPhiVanChuyen());
        hoaDonDTO.setTongTienThanhToan(hoaDon.getTongTienThanhToan());
        hoaDonDTO.setTrangThaiDonHang(hoaDon.getTrangThaiDonHang());
        hoaDonDTO.setMaVanDon(hoaDon.getMaVanDon());
        hoaDonDTO.setTenDonViVanChuyen(hoaDon.getTenDonViVanChuyen());
        hoaDonDTO.setNgayGiaoHangDuKien(hoaDon.getNgayGiaoHangDuKien());
        hoaDonDTO.setGhiChu(hoaDon.getGhiChu());
        hoaDonDTO.setNgayCapNhat(hoaDon.getNgayCapNhat());
        hoaDonDTO.setKhachHang(hoaDon.getKhachHang() == null ? null : hoaDon.getKhachHang().getId());
        hoaDonDTO.setNhanVienTao(hoaDon.getNhanVienTao() == null ? null : hoaDon.getNhanVienTao().getId());
        hoaDonDTO.setMaGiamGia(hoaDon.getMaGiamGia() == null ? null : hoaDon.getMaGiamGia().getId());
        hoaDonDTO.setDiaChiGiaoHang(hoaDon.getDiaChiGiaoHang() == null ? null : hoaDon.getDiaChiGiaoHang().getId());
        return hoaDonDTO;
    }

    private HoaDon mapToEntity(final HoaDonDTO hoaDonDTO, final HoaDon hoaDon) {
        hoaDon.setMaHoaDon(hoaDonDTO.getMaHoaDon());
        hoaDon.setNgayTao(hoaDonDTO.getNgayTao());
        hoaDon.setNgayThanhToan(hoaDonDTO.getNgayThanhToan());
        hoaDon.setTongTienSanPham(hoaDonDTO.getTongTienSanPham());
        hoaDon.setPhiVanChuyen(hoaDonDTO.getPhiVanChuyen());
        hoaDon.setTongTienThanhToan(hoaDonDTO.getTongTienThanhToan());
        hoaDon.setTrangThaiDonHang(hoaDonDTO.getTrangThaiDonHang());
        hoaDon.setMaVanDon(hoaDonDTO.getMaVanDon());
        hoaDon.setTenDonViVanChuyen(hoaDonDTO.getTenDonViVanChuyen());
        hoaDon.setNgayGiaoHangDuKien(hoaDonDTO.getNgayGiaoHangDuKien());
        hoaDon.setGhiChu(hoaDonDTO.getGhiChu());
        hoaDon.setNgayCapNhat(hoaDonDTO.getNgayCapNhat());
        final KhachHang khachHang = hoaDonDTO.getKhachHang() == null ? null : khachHangRepository.findById(hoaDonDTO.getKhachHang())
                .orElseThrow(() -> new NotFoundException("khachHang not found"));
        hoaDon.setKhachHang(khachHang);
        final NhanVien nhanVienTao = hoaDonDTO.getNhanVienTao() == null ? null : nhanVienRepository.findById(hoaDonDTO.getNhanVienTao())
                .orElseThrow(() -> new NotFoundException("nhanVienTao not found"));
        hoaDon.setNhanVienTao(nhanVienTao);
        final MaGiamGia maGiamGia = hoaDonDTO.getMaGiamGia() == null ? null : maGiamGiaRepository.findById(hoaDonDTO.getMaGiamGia())
                .orElseThrow(() -> new NotFoundException("maGiamGia not found"));
        hoaDon.setMaGiamGia(maGiamGia);
        final DiaChiKhachHang diaChiGiaoHang = hoaDonDTO.getDiaChiGiaoHang() == null ? null : diaChiKhachHangRepository.findById(hoaDonDTO.getDiaChiGiaoHang())
                .orElseThrow(() -> new NotFoundException("diaChiGiaoHang not found"));
        hoaDon.setDiaChiGiaoHang(diaChiGiaoHang);
        return hoaDon;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final HoaDonChiTiet hoaDonHoaDonChiTiet = hoaDonChiTietRepository.findFirstByHoaDon(hoaDon);
        if (hoaDonHoaDonChiTiet != null) {
            referencedWarning.setKey("hoaDon.hoaDonChiTiet.hoaDon.referenced");
            referencedWarning.addParam(hoaDonHoaDonChiTiet.getId());
            return referencedWarning;
        }
        final ChiTietThanhToan hoaDonChiTietThanhToan = chiTietThanhToanRepository.findFirstByHoaDon(hoaDon);
        if (hoaDonChiTietThanhToan != null) {
            referencedWarning.setKey("hoaDon.chiTietThanhToan.hoaDon.referenced");
            referencedWarning.addParam(hoaDonChiTietThanhToan.getId());
            return referencedWarning;
        }
        final LichSuHoaDon hoaDonLichSuHoaDon = lichSuHoaDonRepository.findFirstByHoaDon(hoaDon);
        if (hoaDonLichSuHoaDon != null) {
            referencedWarning.setKey("hoaDon.lichSuHoaDon.hoaDon.referenced");
            referencedWarning.addParam(hoaDonLichSuHoaDon.getId());
            return referencedWarning;
        }
        return null;
    }
}