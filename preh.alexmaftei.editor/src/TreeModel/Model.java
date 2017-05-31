package TreeModel;
public abstract class Model {
	protected TreeElement parent;
	protected String shortName;
	protected String type;
	protected String fileName;
	protected String definitionRef;
	protected String category;
	protected IDeltaListener listener = NullDeltaListener.getSoleInstance();
	
	protected void fireAdd(Object added) {
		listener.add(new DeltaEvent(added));
	}

	protected void fireRemove(Object removed) {
		listener.remove(new DeltaEvent(removed));
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public TreeElement getParent() {
		return parent;
	}
	
	/* The receiver should visit the toVisit object and
	 * pass along the argument. */
	public abstract void accept(IModelVisitor visitor, Object passAlongArgument);
	
	public String getShortName() {
		return shortName;
	}
	
	public void addListener(IDeltaListener listener) {
		this.listener = listener;
	}
	
	public Model(String shortName, String type, String fileName) {
		this.shortName = shortName;
		this.type = type;
		this.fileName = fileName;
	}
	
	public Model() {
	}	
	
	public void removeListener(IDeltaListener listener) {
		if(this.listener.equals(listener)) {
			this.listener = NullDeltaListener.getSoleInstance();
		}
	}

	public String getType() {
		return type;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDefinitionRef()
	{
		return definitionRef;
	}

	public void setDefinitionRef(String definitionRef)
	{
		this.definitionRef = definitionRef;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
	


	


}
