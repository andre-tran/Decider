package util;

import android.app.Application;

public class YelpSettings extends Application {
	private static YelpSettings instance = null;
	
	private String sortSetting;
	private String distanceSetting;
	private boolean american;
	private boolean breakfast;
	private boolean italian;
	private boolean japanese;
	private boolean korean;
	private boolean mexican;
	private boolean thai;
	private boolean vietnamese;
	
	
	private YelpSettings(){
		sortSetting = "Best matched";
		distanceSetting = "10 Miles";
		american = false;
		breakfast = false;
		italian = false;
		japanese = false;
		korean = false;
		mexican = false;
		thai = false;
		vietnamese = false;
	}
	
	public static YelpSettings getInstance(){
		if(instance == null){
			instance = new YelpSettings();
		}
		return instance;
	}
	
	public String getSortSetting(){
		return sortSetting;
	}
	
	public void setSortSetting(String sortSetting){
		this.sortSetting = sortSetting;
	}
	
	public String getDistanceSetting() {
		return distanceSetting;
	}

	public void setDistanceSetting(String distanceSetting) {
		this.distanceSetting = distanceSetting;
	}

	public boolean isAmerican() {
		return american;
	}

	public void setAmerican(boolean american) {
		this.american = american;
	}

	public boolean isBreakfast() {
		return breakfast;
	}

	public void setBreakfast(boolean breakfast) {
		this.breakfast = breakfast;
	}

	public boolean isItalian() {
		return italian;
	}

	public void setItalian(boolean italian) {
		this.italian = italian;
	}

	public boolean isJapanese() {
		return japanese;
	}

	public void setJapanese(boolean japanese) {
		this.japanese = japanese;
	}

	public boolean isKorean() {
		return korean;
	}

	public void setKorean(boolean korean) {
		this.korean = korean;
	}

	public boolean isMexican() {
		return mexican;
	}

	public void setMexican(boolean mexican) {
		this.mexican = mexican;
	}

	public boolean isThai() {
		return thai;
	}

	public void setThai(boolean thai) {
		this.thai = thai;
	}

	public boolean isVietnamese() {
		return vietnamese;
	}

	public void setVietnamese(boolean vietnamese) {
		this.vietnamese = vietnamese;
	}

}