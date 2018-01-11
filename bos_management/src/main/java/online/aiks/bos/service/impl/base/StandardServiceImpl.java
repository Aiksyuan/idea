package online.aiks.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aiks.bos.dao.base.StandardRepository;
import online.aiks.bos.domain.base.Standard;
import online.aiks.bos.service.base.StandardService;
@Service
@Transactional
public class StandardServiceImpl implements StandardService {
	@Autowired
	private StandardRepository standardRepository;
	@Override
	public void saveStandard(Standard model) {
		standardRepository.save(model);

	}
	@Override
	public Page<Standard> findStandardListPage(PageRequest pageable) {
		return standardRepository.findAll(pageable);
	}
	@Override
	public void saveOrUpdateStandard(Standard model) {
		standardRepository.saveAndFlush(model);
		
	}
	@Override
	public List<Standard> findAll() {
		return 	standardRepository.findAll();
	}

}
