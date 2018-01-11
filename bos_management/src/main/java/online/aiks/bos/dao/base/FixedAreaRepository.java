package online.aiks.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.aiks.bos.domain.base.FixedArea;

public interface FixedAreaRepository extends JpaRepository<FixedArea, Integer>, JpaSpecificationExecutor<FixedArea> {

}
