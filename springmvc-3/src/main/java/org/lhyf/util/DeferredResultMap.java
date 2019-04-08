package org.lhyf.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/****
 * @author YF
 * @date 2019-03-27 11:06
 * @desc DeferredResultMap
 *
 **/
@Component
public class DeferredResultMap {
    private Map<String, DeferredResult<Object>> map = new HashMap<>();

    public void put(String key, DeferredResult<Object> result) {
        map.put(key, result);
    }

    public DeferredResult<Object> get(String key) {
        return map.get(key);
    }

    public void remove(String key) {
        map.remove(key);
        System.out.println("map size : "+ map.size());
    }

    public int size(){
        return map.size();
    }
    public Map getMap(){
        return map;
    }
}
