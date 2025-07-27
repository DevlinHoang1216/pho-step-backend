package fpt.duantotnghiep.sd28.service; // Đã thay đổi package

import fpt.duantotnghiep.sd28.entity.DanhGiaSanPham;
import fpt.duantotnghiep.sd28.entity.DiaChiKhachHang;
import fpt.duantotnghiep.sd28.entity.GioHang;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.MaGiamGia;
import fpt.duantotnghiep.sd28.entity.TaiKhoan;
import fpt.duantotnghiep.sd28.dto.KhachHangDTO;
import fpt.duantotnghiep.sd28.repo.DanhGiaSanPhamRepository;
import fpt.duantotnghiep.sd28.repo.DiaChiKhachHangRepository;
import fpt.duantotnghiep.sd28.repo.GioHangRepository;
import fpt.duantotnghiep.sd28.repo.HoaDonRepository;
import fpt.duantotnghiep.sd28.repo.KhachHangRepository;
import fpt.duantotnghiep.sd28.repo.MaGiamGiaRepository;
import fpt.duantotnghiep.sd28.repo.TaiKhoanRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class KhachHangService {

    private final KhachHangRepository khachHangRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final DiaChiKhachHangRepository diaChiKhachHangRepository;
    private final MaGiamGiaRepository maGiamGiaRepository;
    private final HoaDonRepository hoaDonRepository;
    private final GioHangRepository gioHangRepository;
    private final DanhGiaSanPhamRepository danhGiaSanPhamRepository;

    public KhachHangService(final KhachHangRepository khachHangRepository,
                            final TaiKhoanRepository taiKhoanRepository,
                            final DiaChiKhachHangRepository diaChiKhachHangRepository,
                            final MaGiamGiaRepository maGiamGiaRepository,
                            final HoaDonRepository hoaDonRepository,
                            final GioHangRepository gioHangRepository,
                            final DanhGiaSanPhamRepository danhGiaSanPhamRepository) {
        this.khachHangRepository = khachHangRepository;
        this.taiKhoanRepository = taiKhoanRepository;
        this.diaChiKhachHangRepository = diaChiKhachHangRepository;
        this.maGiamGiaRepository = maGiamGiaRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.gioHangRepository = gioHangRepository;
        this.danhGiaSanPhamRepository = danhGiaSanPhamRepository;
    }

    public List<KhachHangDTO> findAll() {
        final List<KhachHang> khachHangs = khachHangRepository.findAll(Sort.by("id"));
        return khachHangs.stream()
                .map(khachHang -> mapToDTO(khachHang, new KhachHangDTO()))
                .toList();
    }

    public KhachHangDTO get(final Long id) {
        return khachHangRepository.findById(id)
                .map(khachHang -> mapToDTO(khachHang, new KhachHangDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final KhachHangDTO khachHangDTO) {
        final KhachHang khachHang = new KhachHang();
        mapToEntity(khachHangDTO, khachHang);
        return khachHangRepository.save(khachHang).getId();
    }

    public void update(final Long id, final KhachHangDTO khachHangDTO) {
        final KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(khachHangDTO, khachHang);
        khachHangRepository.save(khachHang);
    }

    public void delete(final Long id) {
        khachHangRepository.deleteById(id);
    }

    private KhachHangDTO mapToDTO(final KhachHang khachHang,
                                  final KhachHangDTO khachHangDTO) {
        khachHangDTO.setId(khachHang.getId());
        khachHangDTO.setTenKhachHang(khachHang.getTenKhachHang());
        khachHangDTO.setSoDienThoai(khachHang.getSoDienThoai());
        khachHangDTO.setGioiTinh(khachHang.getGioiTinh());
        khachHangDTO.setNgaySinh(khachHang.getNgaySinh());
        khachHangDTO.setMaKhachHang(khachHang.getMaKhachHang());
        khachHangDTO.setNgayTao(khachHang.getNgayTao());
        khachHangDTO.setNgayCapNhat(khachHang.getNgayCapNhat());
        khachHangDTO.setTaiKhoan(khachHang.getTaiKhoan() == null ? null : khachHang.getTaiKhoan().getId());
        // Không có trường email và trangThai trong KhachHangDTO hiện tại,
        // nếu bạn muốn có, cần thêm vào KhachHangDTO và entity KhachHang.
        return khachHangDTO;
    }

    private KhachHang mapToEntity(final KhachHangDTO khachHangDTO,
                                  final KhachHang khachHang) {
        khachHang.setTenKhachHang(khachHangDTO.getTenKhachHang());
        khachHang.setSoDienThoai(khachHangDTO.getSoDienThoai());
        khachHang.setGioiTinh(khachHangDTO.getGioiTinh());
        khachHang.setNgaySinh(khachHangDTO.getNgaySinh());
        khachHang.setMaKhachHang(khachHangDTO.getMaKhachHang());
        khachHang.setNgayTao(khachHangDTO.getNgayTao());
        khachHang.setNgayCapNhat(khachHangDTO.getNgayCapNhat());
        final TaiKhoan taiKhoan = khachHangDTO.getTaiKhoan() == null ? null : taiKhoanRepository.findById(khachHangDTO.getTaiKhoan())
                .orElseThrow(() -> new NotFoundException("taiKhoan not found"));
        khachHang.setTaiKhoan(taiKhoan);
        // Không có trường email và trangThai trong KhachHangDTO hiện tại.
        return khachHang;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final DiaChiKhachHang khachHangDiaChiKhachHang = diaChiKhachHangRepository.findFirstByKhachHang(khachHang);
        if (khachHangDiaChiKhachHang != null) {
            referencedWarning.setKey("khachHang.diaChiKhachHang.khachHang.referenced");
            referencedWarning.addParam(khachHangDiaChiKhachHang.getId());
            return referencedWarning;
        }
        final MaGiamGia khachHangDuocCapMaGiamGia = maGiamGiaRepository.findFirstByKhachHangDuocCap(khachHang);
        if (khachHangDuocCapMaGiamGia != null) {
            referencedWarning.setKey("khachHang.maGiamGia.khachHangDuocCap.referenced");
            referencedWarning.addParam(khachHangDuocCapMaGiamGia.getId());
            return referencedWarning;
        }
        final HoaDon khachHangHoaDon = hoaDonRepository.findFirstByKhachHang(khachHang);
        if (khachHangHoaDon != null) {
            referencedWarning.setKey("khachHang.hoaDon.khachHang.referenced");
            referencedWarning.addParam(khachHangHoaDon.getId());
            return referencedWarning;
        }
        final GioHang khachHangGioHang = gioHangRepository.findFirstByKhachHang(khachHang);
        if (khachHangGioHang != null) {
            referencedWarning.setKey("khachHang.gioHang.khachHang.referenced");
            referencedWarning.addParam(khachHangGioHang.getId());
            return referencedWarning;
        }
        final DanhGiaSanPham khachHangDanhGiaSanPham = danhGiaSanPhamRepository.findFirstByKhachHang(khachHang);
        if (khachHangDanhGiaSanPham != null) {
            referencedWarning.setKey("khachHang.danhGiaSanPham.khachHang.referenced");
            referencedWarning.addParam(khachHangDanhGiaSanPham.getId());
            return referencedWarning;
        }
        return null;
    }
}