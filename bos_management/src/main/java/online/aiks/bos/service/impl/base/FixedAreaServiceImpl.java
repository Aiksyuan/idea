package online.aiks.bos.service.impl.base;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aiks.bos.dao.base.FixedAreaRepository;
import online.aiks.bos.domain.base.FixedArea;
import online.aiks.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Override
	public void saveOrUpdate(FixedArea model) {
		//model.setOperatingTime(new Date());
		fixedAreaRepository.saveAndFlush(model);
	}

	@Override
	public Page<FixedArea> pagequery(Specification<FixedArea> spec, PageRequest pageable) {
		return fixedAreaRepository.findAll(spec, pageable);
	}
}