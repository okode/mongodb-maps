/**
 * Copyright 2016 Okode | www.okode.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.okode.demos.mmaps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okode.demos.mmaps.model.Office;
import com.okode.demos.mmaps.service.OfficeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/offices", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfficeController {

	@Autowired
	private OfficeService officeService;

	@ApiOperation("Find offices inside box")
	@RequestMapping(method = RequestMethod.GET)
	public List<Office> findByGeometryWithinBox(@RequestParam GeoJsonPoint first, @RequestParam GeoJsonPoint second) {
		List<Office> result = officeService.findByGeometryWithin(new Box(first, second));
		return result;
	}
	
	@ApiOperation("Find offices from region inside box")
	@RequestMapping(value = "/{region}", method = RequestMethod.GET)
	public List<Office> findByRegionAndGeometryWithinBox(@PathVariable String region, @RequestParam GeoJsonPoint first, @RequestParam GeoJsonPoint second) {
		return officeService.findByRegionAndGeometryWithin(region, new Box(first, second));
	}
	
}
