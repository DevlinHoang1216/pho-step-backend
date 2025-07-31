package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.AnhSanPham;
import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.ChatLieu;
import fpt.duantotnghiep.sd28.entity.KichCo;
import fpt.duantotnghiep.sd28.entity.MauSac;
import fpt.duantotnghiep.sd28.entity.SanPham;
import fpt.duantotnghiep.sd28.entity.TrangThai;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, UUID> {

    ChiTietSanPham findFirstByChatLieu(ChatLieu chatLieu);
    ChiTietSanPham findFirstByMauSac(MauSac mauSac);
    ChiTietSanPham findFirstByKichCo(KichCo kichCo);
    ChiTietSanPham findFirstBySanPham(SanPham sanPham);
    ChiTietSanPham findFirstByTrangThaiRieng(TrangThai trangThai);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm ")
    List<ChiTietSanPham> findAllWithDetails();

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.sanPham.id = :sanPhamId")
    List<ChiTietSanPham> findBySanPhamId(@Param("sanPhamId") Long sanPhamId);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE (sp.tenSanPham LIKE %:keyword% OR ctsp.maCtsp LIKE %:keyword%)")
    List<ChiTietSanPham> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE (sp.tenSanPham LIKE %:keyword% OR ctsp.maCtsp LIKE %:keyword%)")
    List<ChiTietSanPham> searchByKeyword(@Param("keyword") String keyword);


    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.chatLieu.id = :chatLieuId")
    List<ChiTietSanPham> findByChatLieuId(@Param("chatLieuId") Long chatLieuId);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.mauSac.id = :mauSacId")
    List<ChiTietSanPham> findByMauSacId(@Param("mauSacId") Long mauSacId);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.kichCo.id = :kichCoId")
    List<ChiTietSanPham> findByKichCoId(@Param("kichCoId") Long kichCoId);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH sp.trangThai ts " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE (:sanPhamId IS NULL OR sp.id = :sanPhamId) " +
            "AND (:thuongHieuId IS NULL OR sp.thuongHieu.id = :thuongHieuId) " +
            "AND (:danhMucId IS NULL OR sp.danhMuc.id = :danhMucId) " +
            "AND (:chatLieuId IS NULL OR ctsp.chatLieu.id = :chatLieuId) " +
            "AND (:mauSacId IS NULL OR ctsp.mauSac.id = :mauSacId) " +
            "AND (:kichCoId IS NULL OR ctsp.kichCo.id = :kichCoId) " +
            "AND (:idTrangThaiRieng IS NULL OR ctsp.trangThaiRieng.id = :idTrangThaiRieng) " +
            "AND (:keyword IS NULL OR sp.tenSanPham LIKE %:keyword% OR ctsp.maCtsp LIKE %:keyword%)")
    List<ChiTietSanPham> findByFilters(
            @Param("sanPhamId") Long sanPhamId,
            @Param("thuongHieuId") Long thuongHieuId,
            @Param("danhMucId") Long danhMucId,
            @Param("chatLieuId") Long chatLieuId,
            @Param("mauSacId") Long mauSacId,
            @Param("kichCoId") Long kichCoId,
            @Param("idTrangThaiRieng") Long idTrangThaiRieng,
            @Param("keyword") String keyword);

    @Modifying
    @Transactional
    @Query("UPDATE ChiTietSanPham c SET c.trangThaiRieng = :trangThaiRieng WHERE c.id = :id")
    void updateTrangThaiSanPhamRiengById(@Param("id") UUID id, @Param("trangThaiRieng") TrangThai trangThaiRieng);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.sanPham = :sanPham")
    List<ChiTietSanPham> findBySanPham(@Param("sanPham") SanPham sanPham);

    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "JOIN FETCH ctsp.sanPham sp " +
            "JOIN FETCH ctsp.chatLieu cl " +
            "JOIN FETCH ctsp.mauSac ms " +
            "JOIN FETCH ctsp.kichCo kc " +
            "JOIN FETCH ctsp.trangThaiRieng ttr " + // Thêm JOIN FETCH cho trangThaiRieng
            "LEFT JOIN FETCH ctsp.anhSanPhams asp " +
            "JOIN FETCH sp.thuongHieu th " +
            "JOIN FETCH sp.danhMuc dm " +
            "WHERE ctsp.id = :id")
    Optional<ChiTietSanPham> findByIdWithDetailsAndImages(@Param("id") UUID id);

    ChiTietSanPham findByMaCtsp(String maCtsp);
}
