package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.LichSuHoaDon;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, Long> { // Changed Integer to Long based on LichSuHoaDon entity

    LichSuHoaDon findFirstByHoaDon(HoaDon hoaDon);

    LichSuHoaDon findFirstByNhanVienThucHien(NhanVien nhanVien);

}