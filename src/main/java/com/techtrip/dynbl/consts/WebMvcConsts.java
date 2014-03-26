/*
* © Copyright, Terry Trippany, TechTrip, 2014.
* 
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
* 
* See <http://www.gnu.org/licenses/>.
*/
package com.techtrip.dynbl.consts;

/**
 * The Enum WebMvcConsts.
 */
public enum WebMvcConsts {

	/** The message source. */
	MESSAGE_SOURCE("i18n/messages"),
	
	/** The resources mapping. */
	RESOURCES_MAPPING("/resources/**"),
	
	/** The resources location. */
	RESOURCES_LOCATION("/resources/");
	
	/** The value. */
	private final String value;

	/**
	 * Instantiates a new web mvc consts.
	 *
	 * @param value the value
	 */
	private WebMvcConsts(String value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the web mvc consts.
	 *
	 * @param constVal the const val
	 * @return the web mvc consts
	 */
	public static WebMvcConsts getWebMvcConsts(String constVal) {
		for (WebMvcConsts WebMvcConst: WebMvcConsts.values()){
			if (WebMvcConst.value.equals(constVal)){
				return WebMvcConst;
			}
		}
		return null;
	}
}

