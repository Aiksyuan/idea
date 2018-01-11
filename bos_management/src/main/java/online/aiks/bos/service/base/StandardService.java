package online.aiks.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import online.aiks.bos.domain.base.Standard;

public interface StandardService {

	
	/**
	 * 保存
	 * @param model
	 */
	void saveStandard(Standard model);

	/**
	 * 分页
	 * @param pageable
	 * @return
	 */
	Page<Standard> findStandardListPage(PageRequest pageable);

	/**
	 * 保存更新
	 * @param model
	 */
	void saveOrUpdateStandard(Standard model);

	/**
	 * 查询list集合
	 * @return
	 */
	List<Standard> findAll();

}
