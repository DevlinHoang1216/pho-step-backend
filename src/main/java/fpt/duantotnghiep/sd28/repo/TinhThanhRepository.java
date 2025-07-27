package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.TinhThanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TinhThanhRepository extends JpaRepository<TinhThanh, Long> {
}