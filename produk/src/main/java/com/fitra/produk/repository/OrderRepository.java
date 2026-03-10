package com.fitra.produk.repository;

import com.fitra.produk.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.id_pelanggan = :idPelanggan")
	List<Order> findByPelangganId(@Param("idPelanggan") Long idPelanggan);
}
