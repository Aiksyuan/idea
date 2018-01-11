package online.aiks.bos.service.impl.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aiks.bos.dao.base.CourierRepository;
import online.aiks.bos.domain.base.Courier;
import online.aiks.bos.service.base.CourierService;
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	@Autowired
	private CourierRepository courierRepository;
	@Override
	public void saveOrUpdate(Courier model) {
		courierRepository.save(model);

	}

	@Override
	public Page<Courier> findCourierListPage(Specification<Courier> spec, PageRequest pageable) {
		return courierRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteBath(String ids) {
		String[] idArray = ids.split(",");
		if (idArray != null && idArray.length > 0) {
			for (String id : idArray) {
				courierRepository.updateDeltagById(Integer.parseInt(id), '1');
			}
		}

	}

}
