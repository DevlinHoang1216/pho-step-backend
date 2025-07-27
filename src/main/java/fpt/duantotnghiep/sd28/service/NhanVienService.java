package fpt.duantotnghiep.sd28.service; // Đã thay đổi package

import fpt.duantotnghiep.sd28.entity.ChucVu;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.LichSuHoaDon;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.entity.PhieuGiamGia;
import fpt.duantotnghiep.sd28.entity.TaiKhoan;
import fpt.duantotnghiep.sd28.dto.NhanVienDTO;
import fpt.duantotnghiep.sd28.repo.ChucVuRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.repo.LichSuHoaDonRepository;
import fpt.duantotnghiep.sd28.repo.NhanVienRepository;
import fpt.duantotnghiep.sd28.repo.PhieuGiamGiaRepository;
import fpt.duantotnghiep.sd28.repo.TaiKhoanRepository; // Cần có TaiKhoanRepository
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import java.util.UUID; // Thêm import UUID
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class NhanVienService {

    private final NhanVienRepository nhanVienRepository;
    private final TaiKhoanRepository taiKhoanRepository; // Cần inject TaiKhoanRepository
    private final ChucVuRepository chucVuRepository;
    private final PhieuGiamGiaRepository phieuGiamGiaRepository;
    private final HoaDonRepository hoaDonRepository;
    private final LichSuHoaDonRepository lichSuHoaDonRepository;

    public NhanVienService(final NhanVienRepository nhanVienRepository,
                           final TaiKhoanRepository taiKhoanRepository, final ChucVuRepository chucVuRepository,
                           final PhieuGiamGiaRepository phieuGiamGiaRepository,
                           final HoaDonRepository hoaDonRepository,
                           final LichSuHoaDonRepository lichSuHoaDonRepository) {
        this.nhanVienRepository = nhanVienRepository;
        this.taiKhoanRepository = taiKhoanRepository;
        this.chucVuRepository = chucVuRepository;
        this.phieuGiamGiaRepository = phieuGiamGiaRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.lichSuHoaDonRepository = lichSuHoaDonRepository;
    }

    public List<NhanVienDTO> findAll() {
        final List<NhanVien> nhanViens = nhanVienRepository.findAll(Sort.by("id"));
        return nhanViens.stream()
                .map(nhanVien -> mapToDTO(nhanVien, new NhanVienDTO()))
                .toList();
    }

    public NhanVienDTO get(final Long id) { // Sửa Integer thành Long
        return nhanVienRepository.findById(id)
                .map(nhanVien -> mapToDTO(nhanVien, new NhanVienDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NhanVienDTO nhanVienDTO) { // Sửa Integer thành Long
        final NhanVien nhanVien = new NhanVien();
        mapToEntity(nhanVienDTO, nhanVien);
        return nhanVienRepository.save(nhanVien).getId();
    }

    public void update(final Long id, final NhanVienDTO nhanVienDTO) { // Sửa Integer thành Long
        final NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(nhanVienDTO, nhanVien);
        nhanVienRepository.save(nhanVien);
    }

    public void delete(final Long id) { // Sửa Integer thành Long
        nhanVienRepository.deleteById(id);
    }

    private NhanVienDTO mapToDTO(final NhanVien nhanVien, final NhanVienDTO nhanVienDTO) {
        nhanVienDTO.setId(nhanVien.getId());
        nhanVienDTO.setTenNhanVien(nhanVien.getTenNhanVien());
        nhanVienDTO.setNgaySinh(nhanVien.getNgaySinh());
        nhanVienDTO.setGioiTinh(nhanVien.getGioiTinh());
        nhanVienDTO.setSoDienThoai(nhanVien.getSoDienThoai());
        nhanVienDTO.setMaNhanVien(nhanVien.getMaNhanVien());
        nhanVienDTO.setCccd(nhanVien.getCccd());
        nhanVienDTO.setDiaChiSoNhaTenDuong(nhanVien.getDiaChiSoNhaTenDuong());
        nhanVienDTO.setDiaChiPhuongXa(nhanVien.getDiaChiPhuongXa());
        nhanVienDTO.setDiaChiQuanHuyen(nhanVien.getDiaChiQuanHuyen());
        nhanVienDTO.setDiaChiTinhThanh(nhanVien.getDiaChiTinhThanh());
        nhanVienDTO.setNgayTao(nhanVien.getNgayTao());
        nhanVienDTO.setNgayCapNhat(nhanVien.getNgayCapNhat());
        nhanVienDTO.setTrangThai(nhanVien.getTrangThai());
        // Kiểm tra TaiKhoan nếu ID là Long
        nhanVienDTO.setTaiKhoan(nhanVien.getTaiKhoan() == null ? null : nhanVien.getTaiKhoan().getId());
        nhanVienDTO.setChucVu(nhanVien.getChucVu() == null ? null : nhanVien.getChucVu().getId());
        return nhanVienDTO;
    }

    private NhanVien mapToEntity(final NhanVienDTO nhanVienDTO, final NhanVien nhanVien) {
        nhanVien.setTenNhanVien(nhanVienDTO.getTenNhanVien());
        nhanVien.setNgaySinh(nhanVienDTO.getNgaySinh());
        nhanVien.setGioiTinh(nhanVienDTO.getGioiTinh());
        nhanVien.setSoDienThoai(nhanVienDTO.getSoDienThoai());
        nhanVien.setMaNhanVien(nhanVienDTO.getMaNhanVien());
        nhanVien.setCccd(nhanVienDTO.getCccd());
        nhanVien.setDiaChiSoNhaTenDuong(nhanVienDTO.getDiaChiSoNhaTenDuong());
        nhanVien.setDiaChiPhuongXa(nhanVienDTO.getDiaChiPhuongXa());
        nhanVien.setDiaChiQuanHuyen(nhanVienDTO.getDiaChiQuanHuyen());
        nhanVien.setDiaChiTinhThanh(nhanVienDTO.getDiaChiTinhThanh());
        nhanVien.setNgayTao(nhanVienDTO.getNgayTao());
        nhanVien.setNgayCapNhat(nhanVienDTO.getNgayCapNhat());
        nhanVien.setTrangThai(nhanVienDTO.getTrangThai());
        // Sửa kiểu của findById cho taiKhoanRepository
        final TaiKhoan taiKhoan = nhanVienDTO.getTaiKhoan() == null ? null : taiKhoanRepository.findById(nhanVienDTO.getTaiKhoan()) // nhanVienDTO.getTaiKhoan() trả về Long
                .orElseThrow(() -> new NotFoundException("taiKhoan not found"));
        nhanVien.setTaiKhoan(taiKhoan);
        final ChucVu chucVu = nhanVienDTO.getChucVu() == null ? null : chucVuRepository.findById(nhanVienDTO.getChucVu()) // nhanVienDTO.getChucVu() trả về Long
                .orElseThrow(() -> new NotFoundException("chucVu not found"));
        nhanVien.setChucVu(chucVu);
        return nhanVien;
    }

    public ReferencedWarning getReferencedWarning(final Long id) { // Sửa Integer thành Long
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final PhieuGiamGia nhanVienTaoPhieuGiamGia = phieuGiamGiaRepository.findFirstByNhanVienTao(nhanVien);
        if (nhanVienTaoPhieuGiamGia != null) {
            referencedWarning.setKey("nhanVien.phieuGiamGia.nhanVienTao.referenced");
            referencedWarning.addParam(nhanVienTaoPhieuGiamGia.getId()); // ID của PhieuGiamGia là UUID
            return referencedWarning;
        }
        final HoaDon nhanVienTaoHoaDon = hoaDonRepository.findFirstByNhanVienTao(nhanVien);
        if (nhanVienTaoHoaDon != null) {
            referencedWarning.setKey("nhanVien.hoaDon.nhanVienTao.referenced");
            referencedWarning.addParam(nhanVienTaoHoaDon.getId()); // ID của HoaDon là UUID
            return referencedWarning;
        }
        final LichSuHoaDon nhanVienThucHienLichSuHoaDon = lichSuHoaDonRepository.findFirstByNhanVienThucHien(nhanVien);
        if (nhanVienThucHienLichSuHoaDon != null) {
            referencedWarning.setKey("nhanVien.lichSuHoaDon.nhanVienThucHien.referenced");
            // Cần xác định kiểu ID của LichSuHoaDon để addParam
            // referencedWarning.addParam(nhanVienThucHienLichSuHoaDon.getId());
            return referencedWarning;
        }
        return null;
    }

}