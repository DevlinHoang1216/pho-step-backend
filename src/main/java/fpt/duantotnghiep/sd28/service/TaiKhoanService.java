package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.entity.TaiKhoan;
import fpt.duantotnghiep.sd28.dto.TaiKhoanDTO;
import fpt.duantotnghiep.sd28.repo.KhachHangRepository;
import fpt.duantotnghiep.sd28.repo.NhanVienRepository;
import fpt.duantotnghiep.sd28.repo.TaiKhoanRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaiKhoanService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;

    public TaiKhoanService(final TaiKhoanRepository taiKhoanRepository,
                           final NhanVienRepository nhanVienRepository,
                           final KhachHangRepository khachHangRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.khachHangRepository = khachHangRepository;
    }

    public List<TaiKhoanDTO> findAll() {
        final List<TaiKhoan> taiKhoans = taiKhoanRepository.findAll(Sort.by("id"));
        return taiKhoans.stream()
                .map(taiKhoan -> mapToDTO(taiKhoan, new TaiKhoanDTO()))
                .toList();
    }

    public TaiKhoanDTO get(final Long id) {
        return taiKhoanRepository.findById(id)
                .map(taiKhoan -> mapToDTO(taiKhoan, new TaiKhoanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TaiKhoanDTO taiKhoanDTO) {
        final TaiKhoan taiKhoan = new TaiKhoan();
        mapToEntity(taiKhoanDTO, taiKhoan);
        return taiKhoanRepository.save(taiKhoan).getId();
    }

    public void update(final Long id, final TaiKhoanDTO taiKhoanDTO) {
        final TaiKhoan taiKhoan = taiKhoanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(taiKhoanDTO, taiKhoan);
        taiKhoanRepository.save(taiKhoan);
    }

    public void delete(final Long id) {
        taiKhoanRepository.deleteById(id);
    }

    private TaiKhoanDTO mapToDTO(final TaiKhoan taiKhoan, final TaiKhoanDTO taiKhoanDTO) {
        taiKhoanDTO.setId(taiKhoan.getId());
        taiKhoanDTO.setTenDangNhap(taiKhoan.getTenDangNhap());
        taiKhoanDTO.setMatKhauHash(taiKhoan.getMatKhauHash());
        taiKhoanDTO.setEmail(taiKhoan.getEmail());
        taiKhoanDTO.setVaiTro(taiKhoan.getVaiTro());
        taiKhoanDTO.setTrangThai(taiKhoan.getTrangThai());
        taiKhoanDTO.setNgayTao(taiKhoan.getNgayTao());
        taiKhoanDTO.setNgayCapNhat(taiKhoan.getNgayCapNhat());
        return taiKhoanDTO;
    }

    private TaiKhoan mapToEntity(final TaiKhoanDTO taiKhoanDTO, final TaiKhoan taiKhoan) {
        taiKhoan.setTenDangNhap(taiKhoanDTO.getTenDangNhap());
        taiKhoan.setMatKhauHash(taiKhoanDTO.getMatKhauHash());
        taiKhoan.setEmail(taiKhoanDTO.getEmail());
        taiKhoan.setVaiTro(taiKhoanDTO.getVaiTro());
        taiKhoan.setTrangThai(taiKhoanDTO.getTrangThai());
        taiKhoan.setNgayTao(taiKhoanDTO.getNgayTao());
        taiKhoan.setNgayCapNhat(taiKhoanDTO.getNgayCapNhat());
        return taiKhoan;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TaiKhoan taiKhoan = taiKhoanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final NhanVien taiKhoanNhanVien = nhanVienRepository.findFirstByTaiKhoan(taiKhoan);
        if (taiKhoanNhanVien != null) {
            referencedWarning.setKey("taiKhoan.nhanVien.taiKhoan.referenced");
            referencedWarning.addParam(taiKhoanNhanVien.getId());
            return referencedWarning;
        }
        final KhachHang taiKhoanKhachHang = khachHangRepository.findFirstByTaiKhoan(taiKhoan);
        if (taiKhoanKhachHang != null) {
            referencedWarning.setKey("taiKhoan.khachHang.taiKhoan.referenced");
            referencedWarning.addParam(taiKhoanKhachHang.getId());
            return referencedWarning;
        }
        return null;
    }
}