package com.tansha.library.bookshelf.service;

import java.util.List;

import com.tansha.library.bookshelf.model.Area;

public interface AreaService {
	List<Area> getAllAreas();
	Area getAreaById(int areaId);
    boolean addArea(Area area);
    void updateArea(Area area);
    void deleteArea(int areaId);
}
