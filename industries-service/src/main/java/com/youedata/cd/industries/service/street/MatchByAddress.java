package com.youedata.cd.industries.service.street;

import com.youedata.cd.industries.dao.community.CommunityDao;
import com.youedata.cd.industries.dao.street.StreetDao;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.pojo.street.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenyongke on 2016/7/20.
 */
@Component
public class MatchByAddress {
    @Autowired
    private StreetDao streetDao;
    @Autowired
    private CommunityDao communityDao;

    public Map getStreetAndCommunityId(String address) {

        Map map = new HashMap();
        List<Street> streets = streetDao.selectAll();
        int streetIndex;
        int communityIndex;
        int matchIndex;
        String matchString;
        String name;
        for (Street street : streets) {
            name = street.getName();
            matchIndex = name.indexOf("街道");
            if (matchIndex == -1) {
                matchIndex = name.indexOf("地区");
            }
            if (matchIndex != -1) {
                matchString = name.substring(0, matchIndex);
            } else {
                matchString = name;
            }
            streetIndex = address.indexOf(matchString);
            if (streetIndex != -1) {
                map.put("streetId", street.getId());
                break;
            }
        }
        List<Community> communities = communityDao.selectAll();
        for (Community community : communities) {
            name = community.getName();
            matchIndex = name.indexOf("社区");
            if (matchIndex != -1) {
                matchString = name.substring(0, matchIndex);
            } else {
                matchString = name;
            }
            communityIndex = address.indexOf(matchString);
            if (communityIndex != -1) {
                map.put("communityId", community.getId());
                if (map.get("streetId") == null || map.get("streetId") == "") {
                    map.put("streetId", streetDao.selectByPrimaryKey(Integer.parseInt(community.getStreetId().toString())).getId());
                }
                break;
            }
        }
        return map;
    }
}
