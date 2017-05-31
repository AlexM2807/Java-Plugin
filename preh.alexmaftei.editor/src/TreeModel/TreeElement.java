package TreeModel;

import java.util.ArrayList;
import java.util.List;

public class TreeElement extends Model {
	protected List<TreeElement> boxes;
	protected List<BoardGame> games;
	protected List<Element> books;
	
	private static IModelVisitor adder = new Adder();
	private static IModelVisitor remover = new Remover();
	
	public TreeElement() {
		boxes = new ArrayList<TreeElement>();
		games = new ArrayList<BoardGame>();
		books = new ArrayList<Element>();
	}
	
	private static class Adder implements IModelVisitor {

		/*
		 * @see ModelVisitorI#visitBoardgame(BoardGame)
		 */

		/*
		 * @see ModelVisitorI#visitBook(MovingBox)
		 */

		/*
		 * @see ModelVisitorI#visitMovingBox(MovingBox)
		 */

		/*
		 * @see ModelVisitorI#visitBoardgame(BoardGame, Object)
		 */
		public void visitBoardgame(BoardGame boardgame, Object argument) {
			((TreeElement) argument).addBoardGame(boardgame);
		}

		/*
		 * @see ModelVisitorI#visitBook(MovingBox, Object)
		 */
		public void visitBook(Element element, Object argument) {
			((TreeElement) argument).addElement(element);
		}

		/*
		 * @see ModelVisitorI#visitMovingBox(MovingBox, Object)
		 */
		public void visitMovingBox(TreeElement box, Object argument) {
			((TreeElement) argument).addBox(box);
		}

	}

	private static class Remover implements IModelVisitor {
		public void visitBoardgame(BoardGame boardgame, Object argument) {
			((TreeElement) argument).removeBoardGame(boardgame);
		}

		/*
		 * @see ModelVisitorI#visitBook(MovingBox, Object)
		 */
		public void visitBook(Element element, Object argument) {
			((TreeElement) argument).removeElement(element);
		}

		/*
		 * @see ModelVisitorI#visitMovingBox(MovingBox, Object)
		 */
		public void visitMovingBox(TreeElement box, Object argument) {
			((TreeElement) argument).removeBox(box);
			box.addListener(NullDeltaListener.getSoleInstance());
		}

	}
	
	public TreeElement(String name) {
		this();
		this.shortName = name;
	}
	
//	public TreeElement(String name, String type) {
//		this();
//		this.shortName = name;
//		this.type = type;
//	}
//	
//	public TreeElement(String shortName, String type, String file)
//	{
//		this();
//		this.shortName = shortName;
//		this.type = type;
//		this.fileName = file;
//	}
//	
//	public TreeElement(String shortName, String type, String file, String definitionRef)
//	{
//		this();
//		this.shortName = shortName;
//		this.type = type;
//		this.fileName = file;
//		this.definitionRef = definitionRef;
//	}
	
	public TreeElement(String shortName, String type, String file, String definitionRef , String category)
	{
		this();
		this.shortName = shortName;
		this.type = type;
		this.fileName = file;
		this.definitionRef = definitionRef;
		this.category = category;
	}


	public List<TreeElement> getBoxes() {
		return boxes;
	}
	
	public void addBox(TreeElement box) {
		boxes.add(box);
		box.parent = this;
		fireAdd(box);
	}
	
	public void addElement(Element element) {
		books.add(element);
		element.parent = this;
		fireAdd(element);
	}
	
	public void addBoardGame(BoardGame game) {
		games.add(game);
		game.parent = this;
		fireAdd(game);
	}		
	
	public List<Element> getBooks() {
		return books;
	}
	
	public void remove(Model toRemove) {
		toRemove.accept(remover, this);
	}
	
	public void removeBoardGame(BoardGame boardGame) {
		games.remove(boardGame);
		boardGame.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(boardGame);
	}
	
	public void removeElement(Element element) {
		books.remove(element);
		element.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(element);
	}
	
	public void removeBox(TreeElement box) {
		boxes.remove(box);
		box.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(box);	
	}

	public void add(Model toAdd) {
		toAdd.accept(adder, this);
	}
	
	public List<BoardGame> getGames() {
		return games;
	}
	
	/** Answer the total number of items the
	 * receiver contains. */
	public int size() {
		return getBooks().size() + getBoxes().size() + getGames().size();
	}
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		visitor.visitMovingBox(this, passAlongArgument);
	}

}
