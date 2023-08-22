package com.infoin.app.temp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoin.app.common.CommonService;
import com.infoin.app.temp.mapper.TempMapper;
import com.infoin.app.temp.service.TempService;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2023. 2. 21. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
@Service
public class TempServiceImpl extends CommonService implements TempService {

	@Autowired
	private TempMapper tempMapper;

	/* (non-Javadoc)
	 * @see com.infoin.app.temp.service.TempService#selectNow()
	 */
	@Override
	public String selectNow() {
		return tempMapper.selectNow();
	}

}
