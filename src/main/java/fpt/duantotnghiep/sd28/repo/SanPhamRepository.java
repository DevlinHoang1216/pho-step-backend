package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.DanhMuc;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.entity.ThuongHieu;
import fpt.duantotnghiep.sd28.dto.SanPhamDTO;
import fpt.duantotnghiep.sd28.entity.TrangThai; // Đảm bảo import TrangThai
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> { // Đổi Integer thành Long cho ID của SanPham

    SanPham findFirstByDanhMuc(DanhMuc danhMuc);

    SanPham findFirstByThuongHieu(ThuongHieu thuongHieu);
    SanPham findFirstByTrangThai(TrangThai trangThai); // Giữ nguyên, nhận đối tượng TrangThai

    // Cập nhật JPQL query để lấy id và tên của trạng thái (sp.trangThai.id, sp.trangThai.tenTrangThai)
    @Query("SELECT NEW fpt.duantotnghiep.sd28.dto.SanPhamDTO(" +
            "sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai.id, sp.trangThai.tenTrangThai, " + // Đã sửa đổi: thêm tenTrangThai
            "COALESCE(SUM(ctsp.soLuongTonKho), 0), sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id) " +
            "FROM SanPham sp LEFT JOIN sp.chiTietSanPhams ctsp " +
            "GROUP BY sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai.id, sp.trangThai.tenTrangThai, sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id") // Đã sửa đổi
    Page<SanPhamDTO> findAllWithQuantity(Pageable pageable);

    // Cập nhật JPQL query để lấy id và tên của trạng thái (sp.trangThai.id, sp.trangThai.tenTrangThai)
    @Query("SELECT NEW fpt.duantotnghiep.sd28.dto.SanPhamDTO(" +
            "sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai.id, sp.trangThai.tenTrangThai, " + // Đã sửa đổi: thêm tenTrangThai
            "COALESCE(SUM(ctsp.soLuongTonKho), 0), sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id) " +
            "FROM SanPham sp LEFT JOIN sp.chiTietSanPhams ctsp " +
            "WHERE sp.id = :id " +
            "GROUP BY sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai.id, sp.trangThai.tenTrangThai, sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id") // Đã sửa đổi
    Optional<SanPhamDTO> findByIdWithQuantity(Long id); // Đổi Integer thành Long

    // Cập nhật JPQL query để lấy id và tên của trạng thái (sp.trangThai.id, sp.trangThai.tenTrangThai)
    @Query("SELECT NEW fpt.duantotnghiep.sd28.dto.SanPhamDTO(" +
            "sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai.id, sp.trangThai.tenTrangThai, " + // Đã sửa đổi: thêm tenTrangThai
            "COALESCE(SUM(ctsp.soLuongTonKho), 0), sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id) " +
            "FROM SanPham sp LEFT JOIN sp.chiTietSanPhams ctsp " +
            "WHERE LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "GROUP BY sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai.id, sp.trangThai.tenTrangThai, sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id") // Đã sửa đổi
    Page<SanPhamDTO> findByTenSanPhamContaining(@Param("keyword") String keyword, Pageable pageable);

    // Phương thức mới để cập nhật trạng thái của SanPham (nhận đối tượng TrangThai)
    @Modifying
    @Transactional
    @Query("UPDATE SanPham s SET s.trangThai = :trangThai WHERE s.id = :id")
    void updateTrangThaiById(@Param("id") Long id, @Param("trangThai") TrangThai trangThai); // Đã sửa đổi kiểu tham số

    // Phương thức để lấy tất cả sản phẩm (không phân trang)
    List<SanPham> findAll();

    SanPham findByMaSanPham(String maSanPham); // Thêm phương thức này để kiểm tra trùng mã
    List<SanPham> searchByTenSanPham(String keyword); // Thêm phương thức này để tìm kiếm theo tên

    // NEW: Kiểm tra xem tên sản phẩm đã tồn tại chưa (không phân biệt chữ hoa/chữ thường)
    boolean existsByTenSanPhamIgnoreCase(String tenSanPham);
}
