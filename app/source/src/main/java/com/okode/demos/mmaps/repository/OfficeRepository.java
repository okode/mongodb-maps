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

package com.okode.demos.mmaps.repository;

import java.util.List;

import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.okode.demos.mmaps.model.Office;

public interface OfficeRepository extends MongoRepository<Office, String> {

	public static final String DEFAULT_FIELDS = "{ '_id' : 0 , 'geometry' : 1 , 'properties.name' : 1 , 'properties.url' : 1 , 'properties.pictures' : 1 }";
	
	@Query(value = "{ 'geometry' : { '$geoWithin' : { '$box' : ?0 } } }", fields = DEFAULT_FIELDS)
	List<Office> findByGeometryWithin(Box box);
	
	@Query(value = "{ 'properties.address.region' : ?0, 'geometry' : { '$geoWithin' : { '$box' : ?1 } } }", fields = DEFAULT_FIELDS)
	List<Office> findByRegionAndGeometryWithin(String region, Box box);
	
}
