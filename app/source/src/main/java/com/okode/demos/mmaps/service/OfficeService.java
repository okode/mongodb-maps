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

package com.okode.demos.mmaps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.stereotype.Service;

import com.okode.demos.mmaps.model.Office;
import com.okode.demos.mmaps.repository.OfficeRepository;

@Service
public class OfficeService {

	@Autowired
	private OfficeRepository officeRepository;
	
	public List<Office> findByGeometryWithin(Box box) {
		return officeRepository.findByGeometryWithin(box);
	}
	
}
