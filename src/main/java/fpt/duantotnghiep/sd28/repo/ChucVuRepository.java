package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Long> { // Changed Integer to Long based on ChucVu entity
}