package online.aiks.bos.service.impl.base;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aiks.bos.dao.base.AreaRepository;
import online.aiks.bos.domain.base.Area;
import online.aiks.bos.service.base.AreaService;
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void saveArea(ArrayList<Area> areaList) {
	areaRepository.save(areaList);
	}
}
