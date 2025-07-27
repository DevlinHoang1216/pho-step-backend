package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.ChatLieu;
import fpt.duantotnghiep.sd28.entity.KichCo;
import fpt.duantotnghiep.sd28.entity.MauSac;
import fpt.duantotnghiep.sd28.entity.SanPham;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Import Optional
import java.util.UUID;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, UUID> {

    ChiTietSanPham findFirstByChatLieu(ChatLieu chatLieu);
    ChiTietSanPham findFirstByMauSac(MauSac mauSac);
    ChiTietSanPham findFirstByKichCo(KichCo kichCo);
    ChiTietSanPham findFirstBySanPham(SanPham sanPham);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findAllWithDetails();

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.sanPham.id = :sanPhamId AND ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findBySanPhamId(@Param("sanPhamId") Long sanPhamId);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE (ctsp.sanPham.tenSanPham LIKE %:keyword% OR ctsp.maCtsp LIKE %:keyword%) AND ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.chatLieu.id = :chatLieuId AND ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findByChatLieuId(@Param("chatLieuId") Long chatLieuId);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.mauSac.id = :mauSacId AND ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findByMauSacId(@Param("mauSacId") Long mauSacId);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.kichCo.id = :kichCoId AND ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findByKichCoId(@Param("kichCoId") Long kichCoId);

    // Cập nhật phương thức findByFilters để bao gồm thương hiệu và danh mục
    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE (:sanPhamId IS NULL OR sp.id = :sanPhamId) " +
            "AND (:thuongHieuId IS NULL OR sp.thuongHieu.id = :thuongHieuId) " + // Thêm lọc theo Thương hiệu
            "AND (:danhMucId IS NULL OR sp.danhMuc.id = :danhMucId) " +         // Thêm lọc theo Danh mục
            "AND (:chatLieuId IS NULL OR ctsp.chatLieu.id = :chatLieuId) " +
            "AND (:mauSacId IS NULL OR ctsp.mauSac.id = :mauSacId) " +
            "AND (:kichCoId IS NULL OR ctsp.kichCo.id = :kichCoId) " +
            "AND (:keyword IS NULL OR sp.tenSanPham LIKE %:keyword% OR ctsp.maCtsp LIKE %:keyword%) " +
            "AND ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findByFilters(
            @Param("sanPhamId") Long sanPhamId,
            @Param("thuongHieuId") Long thuongHieuId, // Thêm tham số
            @Param("danhMucId") Long danhMucId,     // Thêm tham số
            @Param("chatLieuId") Long chatLieuId,
            @Param("mauSacId") Long mauSacId,
            @Param("kichCoId") Long kichCoId,
            @Param("keyword") String keyword);

    @Modifying
    @Transactional
    @Query("UPDATE ChiTietSanPham c SET c.trangThaiSanPhamRieng = :trangThai WHERE c.id = :id")
    void updateTrangThaiSanPhamRiengById(@Param("id") UUID id, @Param("trangThai") String trangThai);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.sanPham = :sanPham AND ctsp.trangThaiSanPhamRieng IN ('dang_kinh_doanh', 'ngung_kinh_doanh', 'het_hang')")
    List<ChiTietSanPham> findBySanPham(@Param("sanPham") SanPham sanPham);

    // Thêm một truy vấn để lấy ChiTietSanPham theo ID cùng với ảnh của nó
    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " + // Thêm JOIN FETCH cho AnhSanPham
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.id = :id")
    Optional<ChiTietSanPham> findByIdWithDetailsAndImages(@Param("id") UUID id);
}