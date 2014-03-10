package com.techtrip.dynbl.consts;

public enum WebMvcConsts {

	MESSAGE_SOURCE("i18n/messages"),
	RESOURCES_MAPPING("/resources/**"),
	RESOURCES_LOCATION("/resources/");
	
	private final String value;

	private WebMvcConsts(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static WebMvcConsts getWebMvcConsts(String constVal) {
		for (WebMvcConsts WebMvcConst: WebMvcConsts.values()){
			if (WebMvcConst.value.equals(constVal)){
				return WebMvcConst;
			}
		}
		return null;
	}
}

