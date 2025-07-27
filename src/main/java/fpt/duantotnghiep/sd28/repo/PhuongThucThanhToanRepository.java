package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.PhuongThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongThucThanhToanRepository extends JpaRepository<PhuongThucThanhToan, Long> { // Changed Integer to Long based on PhuongThucThanhToan entity
}