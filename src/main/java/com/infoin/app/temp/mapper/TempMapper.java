package com.infoin.app.temp.mapper;

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface TempMapper {

	public String selectNow();

}
