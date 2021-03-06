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

package com.okode.demos.mmaps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

	@Bean
	public Docket api() {
		return docket("api", "/api/**");
	}
	
	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(null, "none", "alpha", "schema", null, false, true);
	}
	
	private Docket docket(String groupName, String pathSelectors) {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.groupName(groupName)
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant(pathSelectors)).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("MongoDB Maps API")
				.description("MongoDB Maps API Documentation and Testing").version("1.0")
				.license("Copyright (C) 2016 Okode · www.okode.com")
				.licenseUrl("http://www.okode.com")
				.termsOfServiceUrl("http://www.okode.com").build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
