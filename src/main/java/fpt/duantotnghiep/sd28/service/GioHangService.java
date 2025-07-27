package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.entity.GioHang;
import fpt.duantotnghiep.sd28.entity.GioHangChiTiet;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.dto.GioHangDTO;
import fpt.duantotnghiep.sd28.repo.GioHangChiTietRepository;
import fpt.duantotnghiep.sd28.repo.GioHangRepository;
import fpt.duantotnghiep.sd28.repo.KhachHangRepository;
import fpt.duantotnghiep.sd28.util.NotFoundException;
import fpt.duantotnghiep.sd28.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GioHangService {

    private final GioHangRepository gioHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final GioHangChiTietRepository gioHangChiTietRepository;

    public GioHangService(final GioHangRepository gioHangRepository,
                          final KhachHangRepository khachHangRepository,
                          final GioHangChiTietRepository gioHangChiTietRepository) {
        this.gioHangRepository = gioHangRepository;
        this.khachHangRepository = khachHangRepository;
        this.gioHangChiTietRepository = gioHangChiTietRepository;
    }

    public List<GioHangDTO> findAll() {
        final List<GioHang> gioHangs = gioHangRepository.findAll(Sort.by("id"));
        return gioHangs.stream()
                .map(gioHang -> mapToDTO(gioHang, new GioHangDTO()))
                .toList();
    }

    public GioHangDTO get(final UUID id) {
        return gioHangRepository.findById(id)
                .map(gioHang -> mapToDTO(gioHang, new GioHangDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final GioHangDTO gioHangDTO) {
        final GioHang gioHang = new GioHang();
        mapToEntity(gioHangDTO, gioHang);
        return gioHangRepository.save(gioHang).getId();
    }

    public void update(final UUID id, final GioHangDTO gioHangDTO) {
        final GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(gioHangDTO, gioHang);
        gioHangRepository.save(gioHang);
    }

    public void delete(final UUID id) {
        gioHangRepository.deleteById(id);
    }

    private GioHangDTO mapToDTO(final GioHang gioHang, final GioHangDTO gioHangDTO) {
        gioHangDTO.setId(gioHang.getId());
        gioHangDTO.setMaPhienGioHang(gioHang.getMaPhienGioHang());
        gioHangDTO.setNgayTao(gioHang.getNgayTao());
        gioHangDTO.setNgayCapNhat(gioHang.getNgayCapNhat());
        gioHangDTO.setTrangThai(gioHang.getTrangThai());
        gioHangDTO.setKhachHang(gioHang.getKhachHang() == null ? null : gioHang.getKhachHang().getId());
        return gioHangDTO;
    }

    private GioHang mapToEntity(final GioHangDTO gioHangDTO, final GioHang gioHang) {
        gioHang.setMaPhienGioHang(gioHangDTO.getMaPhienGioHang());
        gioHang.setNgayTao(gioHangDTO.getNgayTao());
        gioHang.setNgayCapNhat(gioHangDTO.getNgayCapNhat());
        gioHang.setTrangThai(gioHangDTO.getTrangThai());
        final KhachHang khachHang = gioHangDTO.getKhachHang() == null ? null : khachHangRepository.findById(gioHangDTO.getKhachHang())
                .orElseThrow(() -> new NotFoundException("khachHang not found"));
        gioHang.setKhachHang(khachHang);
        return gioHang;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final GioHangChiTiet gioHangGioHangChiTiet = gioHangChiTietRepository.findFirstByGioHang(gioHang);
        if (gioHangGioHangChiTiet != null) {
            referencedWarning.setKey("gioHang.gioHangChiTiet.gioHang.referenced");
            referencedWarning.addParam(gioHangGioHangChiTiet.getId());
            return referencedWarning;
        }
        return null;
    }
}