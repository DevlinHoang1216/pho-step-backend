package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.DiaChiKhachHang;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.PhuongXa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DiaChiKhachHangRepository extends JpaRepository<DiaChiKhachHang, Long> { // Changed Integer to Long based on DiaChiKhachHang entity

    DiaChiKhachHang findFirstByKhachHang(KhachHang khachHang);

    DiaChiKhachHang findFirstByPhuongXa(PhuongXa phuongXa);

}