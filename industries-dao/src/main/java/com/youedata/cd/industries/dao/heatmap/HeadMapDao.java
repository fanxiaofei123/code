package com.youedata.cd.industries.dao.heatmap;

import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.heatmap.Point;
import com.youedata.cd.industries.pojo.heatmap.Version;
import com.youedata.cd.industries.pojo.street.Street;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cdyoue on 2016/6/8.
 */
public interface HeadMapDao {
    List<Version> searchIndex();
    List<Point> selectEnterprisePoint(List<String> list);
    List<EnterpriseBase> selectSourceId(Map param);
    List<Street> selectCountByStreet(List<String> list);
    List<String> selectVersion();
    List<Category> selectCountByCategory(Map param);
}
