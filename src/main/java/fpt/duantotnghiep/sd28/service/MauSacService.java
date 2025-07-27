package fpt.duantotnghiep.sd28.service;

import fpt.duantotnghiep.sd28.dto.MauSacDTO;
import fpt.duantotnghiep.sd28.entity.MauSac;
import fpt.duantotnghiep.sd28.repo.MauSacRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MauSacService {

    private final MauSacRepository mauSacRepository;

    public MauSacService(MauSacRepository mauSacRepository) {
        this.mauSacRepository = mauSacRepository;
    }

    // Phương thức tạo màu sắc mới
    public Long create(MauSacDTO mauSacDTO) {
        // Kiểm tra xem mã màu sắc đã tồn tại chưa
        if (mauSacRepository.existsByMaMauSac(mauSacDTO.getMaMauSac())) {
            throw new IllegalArgumentException("Mã màu sắc '" + mauSacDTO.getMaMauSac() + "' đã tồn tại.");
        }

        // Tạo một đối tượng MauSac từ MauSacDTO
        MauSac mauSac = new MauSac();
        mauSac.setTenMauSac(mauSacDTO.getTenMauSac());
        mauSac.setMaMauSac(mauSacDTO.getMaMauSac());
        mauSac.setHex(mauSacDTO.getHex()); // Lấy mã HEX từ DTO và lưu vào Entity

        // Đặt ngày tạo và ngày cập nhật tự động
        mauSac.setNgayTao(LocalDateTime.now());
        mauSac.setNgayCapNhat(LocalDateTime.now());

        // Lưu đối tượng MauSac vào database và trả về ID
        return mauSacRepository.save(mauSac).getId();
    }

    // Phương thức lấy tất cả màu sắc (để hiển thị danh sách)
    public List<MauSacDTO> findAll() {
        return mauSacRepository.findAllByOrderByTenMauSacAsc()
                .stream()
                .map(this::mapToDTO) // Ánh xạ từng Entity sang DTO
                .collect(Collectors.toList());
    }

    // Phương thức hỗ trợ ánh xạ Entity sang DTO
    private MauSacDTO mapToDTO(final MauSac mauSac) {
        return MauSacDTO.builder()
                .id(mauSac.getId())
                .tenMauSac(mauSac.getTenMauSac())
                .maMauSac(mauSac.getMaMauSac())
                .hex(mauSac.getHex()) // Chỉ ánh xạ HEX
                .build();
    }

    // Bạn có thể thêm các phương thức khác như update, delete, findById tùy theo yêu cầu
    // Ví dụ cho findById:
    public MauSacDTO findById(Long id) {
        return mauSacRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null); // Hoặc ném NotFoundException nếu không tìm thấy
    }

    // Ví dụ cho update:
    public void update(Long id, MauSacDTO mauSacDTO) {
        MauSac existingMauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Màu sắc với ID " + id + " không tồn tại."));

        // Kiểm tra trùng mã màu nếu mã mới khác mã cũ
        if (!existingMauSac.getMaMauSac().equals(mauSacDTO.getMaMauSac()) &&
                mauSacRepository.existsByMaMauSac(mauSacDTO.getMaMauSac())) {
            throw new IllegalArgumentException("Mã màu sắc '" + mauSacDTO.getMaMauSac() + "' đã tồn tại.");
        }

        existingMauSac.setTenMauSac(mauSacDTO.getTenMauSac());
        existingMauSac.setMaMauSac(mauSacDTO.getMaMauSac());
        existingMauSac.setHex(mauSacDTO.getHex());
        existingMauSac.setNgayCapNhat(LocalDateTime.now());
        mauSacRepository.save(existingMauSac);
    }

    // Ví dụ cho delete:
    public void delete(Long id) {
        if (!mauSacRepository.existsById(id)) {
            throw new IllegalArgumentException("Màu sắc với ID " + id + " không tồn tại.");
        }
        // TODO: Cần kiểm tra ràng buộc khóa ngoại trước khi xóa (ví dụ: màu sắc này có đang được sử dụng trong chi_tiet_san_pham không)
        mauSacRepository.deleteById(id);
    }
}