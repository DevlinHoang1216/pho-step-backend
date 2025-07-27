package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ChiTietThanhToan;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.PhuongThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChiTietThanhToanRepository extends JpaRepository<ChiTietThanhToan, UUID> {

    ChiTietThanhToan findFirstByHoaDon(HoaDon hoaDon);

    ChiTietThanhToan findFirstByPhuongThucThanhToan(PhuongThucThanhToan phuongThucThanhToan);

}