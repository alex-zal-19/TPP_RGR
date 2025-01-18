package COM.TPP.RGR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import COM.TPP.RGR.models.Literature;

@Repository
public interface LiteratureRepository extends JpaRepository<Literature, Integer> {

}
