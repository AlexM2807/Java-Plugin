package preh.alexmaftei.editor.classs;

public class PortPin
{
	String name;
	String portPinDirection;
	String portPinId;
	String portPinInitialMode;
	String portPinLevelValue;
	String portPinDirectionChangeable;
	String portPinMode;
	String portPinModeChangeable;
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPortPinDirection() {
		return portPinDirection;
	}
	public void setPortPinDirection(String portPinDirection) {
		this.portPinDirection = portPinDirection;
	}
	public String getPortPinId() {
		return portPinId;
	}
	public void setPortPinId(String portPinId) {
		this.portPinId = portPinId;
	}
	public String getPortPinInitialMode() {
		return portPinInitialMode;
	}
	public void setPortPinInitialMode(String portPinInitialMode) {
		this.portPinInitialMode = portPinInitialMode;
	}
	public String getPortPinLevelValue() {
		return portPinLevelValue;
	}
	public void setPortPinLevelValue(String portPinLevelValue) {
		this.portPinLevelValue = portPinLevelValue;
	}
	public String getPortPinDirectionChangeable() {
		return portPinDirectionChangeable;
	}
	public void setPortPinDirectionChangeable(String portPinDirectionChangeable) {
		this.portPinDirectionChangeable = portPinDirectionChangeable;
	}
	public String getPortPinMode() {
		return portPinMode;
	}
	public void setPortPinMode(String portPinMode) {
		this.portPinMode = portPinMode;
	}
	public String getPortPinModeChangeable() {
		return portPinModeChangeable;
	}
	public void setPortPinModeChangeable(String portPinModeChangeable) {
		this.portPinModeChangeable = portPinModeChangeable;
	}
	@Override
	public String toString()
	{
		return "Pin [name=" + name + ", portPinDirection=" + portPinDirection + "]";
	}
	public PortPin(String name)
	{
		super();
		this.name= name;
	}
	public PortPin(String name, String portPinDirection, String portPinId, String portPinInitialMode, String portPinLevelValue, String portPinDirectionChangeable, String portPinMode, String portPinModeChangeable)
	{
		super();
		this.name = name;
		this.portPinDirection = portPinDirection;
		this.portPinId = portPinId;
		this.portPinInitialMode = portPinInitialMode;
		this.portPinLevelValue = portPinLevelValue;
		this.portPinDirectionChangeable = portPinDirectionChangeable;
		this.portPinMode = portPinMode;
		this.portPinModeChangeable = portPinModeChangeable;
	}
	public PortPin()
	{}
	
	public void show()
	{
		System.out.println("Pin Name: " + name + " portPinDirection : "+ portPinDirection);
	}
	
}
