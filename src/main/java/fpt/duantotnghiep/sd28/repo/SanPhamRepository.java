package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.DanhMuc;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.entity.ThuongHieu;
import fpt.duantotnghiep.sd28.dto.SanPhamDTO;
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
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {

    SanPham findFirstByDanhMuc(DanhMuc danhMuc);

    SanPham findFirstByThuongHieu(ThuongHieu thuongHieu);

    @Query("SELECT NEW fpt.duantotnghiep.sd28.dto.SanPhamDTO(" +
            "sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, sp.trangThai, " +
            "COALESCE(SUM(ctsp.soLuongTonKho), 0), sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id) " +
            "FROM SanPham sp LEFT JOIN sp.chiTietSanPhams ctsp " +
            "WHERE sp.trangThai IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang') " +
            "AND (ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang') OR ctsp IS NULL) " +
            "GROUP BY sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai, sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id")
    Page<SanPhamDTO> findAllWithQuantity(Pageable pageable);

    @Query("SELECT NEW fpt.duantotnghiep.sd28.dto.SanPhamDTO(" +
            "sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, sp.trangThai, " +
            "COALESCE(SUM(ctsp.soLuongTonKho), 0), sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id) " +
            "FROM SanPham sp LEFT JOIN sp.chiTietSanPhams ctsp " +
            "WHERE sp.id = :id AND sp.trangThai IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang') " +
            "AND (ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang') OR ctsp IS NULL) " +
            "GROUP BY sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai, sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id")
    Optional<SanPhamDTO> findByIdWithQuantity(Long id);

    @Query("SELECT NEW fpt.duantotnghiep.sd28.dto.SanPhamDTO(" +
            "sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, sp.trangThai, " +
            "COALESCE(SUM(ctsp.soLuongTonKho), 0), sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id) " +
            "FROM SanPham sp LEFT JOIN sp.chiTietSanPhams ctsp " +
            "WHERE LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :keyword, '%')) AND sp.trangThai IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang') " +
            "AND (ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang') OR ctsp IS NULL) " +
            "GROUP BY sp.id, sp.tenSanPham, sp.maSanPham, sp.moTaSanPham, sp.urlAnhDaiDien, sp.quocGiaSanXuat, " +
            "sp.trangThai, sp.ngayTao, sp.ngayCapNhat, sp.danhMuc.id, sp.thuongHieu.id")
    Page<SanPhamDTO> findByTenSanPhamContaining(@Param("keyword") String keyword, Pageable pageable);

    // Phương thức mới để cập nhật trạng thái của SanPham
    @Modifying
    @Transactional
    @Query("UPDATE SanPham s SET s.trangThai = :trangThai WHERE s.id = :id")
    void updateTrangThaiById(@Param("id") Long id, @Param("trangThai") String trangThai);

    // Phương thức để lấy tất cả sản phẩm (không phân trang)
    List<SanPham> findAll();
}
