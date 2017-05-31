package TreeModel;


public class BoardGame extends Model {
	
	public BoardGame(String shortName, String type, String fileName) {
		super(shortName, type, fileName);
	}
	
	
	
	
	
	
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		visitor.visitBoardgame(this, passAlongArgument);
	}

}
