package online.aiks.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import online.aiks.bos.domain.base.FixedArea;

public interface FixedAreaService {

	/**
	 * 保存或者更新
	 * @param model 
	 */
	void saveOrUpdate(FixedArea model);

	/**
	 * 分页查询
	 * @param specification
	 * @param pageRequest
	 * @return
	 */
	Page<FixedArea> pagequery(Specification<FixedArea> specification, PageRequest pageRequest);

}
