package online.aiks.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import online.aiks.bos.domain.base.Courier;
import online.aiks.bos.domain.base.Standard;

public interface CourierService {

	/**
	 * @param model
	 */
	void saveOrUpdate(Courier model);

	/**
	 * @param spec 
	 * @param pageable
	 * @return
	 */
	Page<Courier> findCourierListPage(Specification<Courier> spec, PageRequest pageable);

	/**删除
	 * @param ids
	 */
	void deleteBath(String ids);

}
