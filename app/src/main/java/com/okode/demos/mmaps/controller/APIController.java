/**
 * Copyright 2015 Okode | www.okode.com
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.okode.demos.mmaps.model.GeoJSON;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api")
@Api(value = "", description = "Common API Operations")
public class APIController {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@ApiOperation("Find entities by filter and fields inside a view")
	@RequestMapping(value = "/findentities", method = RequestMethod.GET)
	@ResponseBody
	private GeoJSON findEntitiesInsideViewByFilterFields(
			@RequestParam(defaultValue = "24.25079") float lat1,
			@RequestParam(defaultValue = "-35.912262") float lng1,
			@RequestParam(defaultValue = "53.46439") float lat2,
			@RequestParam(defaultValue = "28.951021") float lng2,
			@RequestParam(defaultValue = "office") String collection,
			@RequestParam(defaultValue = "{\"properties.address.region\":\"MADRID\"}") String filter,
			@RequestParam(defaultValue = "{\"_id\":0,\"type\":1,\"geometry\":1,\"properties.name\":1,\"properties.url\":1,\"properties.pictures\":1}") String fields)
	{
		Criteria isInsideView = Criteria.where("geometry.coordinates").within(new Box(new Point(lng1, lat1), new Point(lng2, lat2)));
		Query query = new BasicQuery(filter, fields).addCriteria(isInsideView);
		return new GeoJSON(mongoTemplate.find(query, DBObject.class, collection));
	}
	
}
