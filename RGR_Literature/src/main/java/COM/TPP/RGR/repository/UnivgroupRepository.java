package COM.TPP.RGR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import COM.TPP.RGR.models.Univgroup;

@Repository
public interface UnivgroupRepository extends JpaRepository<Univgroup, Integer> {

}
