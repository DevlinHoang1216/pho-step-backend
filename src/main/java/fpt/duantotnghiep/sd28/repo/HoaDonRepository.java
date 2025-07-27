package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {

    HoaDon findFirstByKhachHang(KhachHang khachHang);

    HoaDon findFirstByNhanVienTao(NhanVien nhanVien);

    HoaDon findFirstByMaGiamGia(MaGiamGia maGiamGia);

    HoaDon findFirstByDiaChiGiaoHang(DiaChiKhachHang diaChiKhachHang);

}
