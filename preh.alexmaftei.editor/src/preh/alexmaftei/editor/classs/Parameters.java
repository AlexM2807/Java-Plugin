package preh.alexmaftei.editor.classs;

import java.util.List;

public class Parameters {
	String parametersType;
	String shortName;
	int min;
	int max;

	List<?> prametersValue;
	
	
	
	public String getParametersType() {
		return parametersType;
	}
	public void setParametersType(String parametersType) {
		this.parametersType = parametersType;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public List<?> getPrametersValue() {
		return prametersValue;
	}
	public void setPrametersValue(List<?> prametersValue) {
		this.prametersValue = prametersValue;
	}
	
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public Parameters(String parametersType, String shortName, List<?> prametersValue) {
		super();
		this.parametersType = parametersType;
		this.shortName = shortName;
		this.prametersValue = prametersValue;
	}
	public Parameters(String parametersType, String shortName) {
		super();
		this.parametersType = parametersType;
		this.shortName = shortName;
	}
	public Parameters(String parametersType, String shortName, int min, int max) {
		super();
		this.parametersType = parametersType;
		this.shortName = shortName;
		this.min = min;
		this.max = max;
	}
	
	

}
