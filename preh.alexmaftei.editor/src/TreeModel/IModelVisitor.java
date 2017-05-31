package TreeModel;



public interface IModelVisitor {
	public void visitMovingBox(TreeElement box, Object passAlongArgument);
	public void visitBook(Element book, Object passAlongArgument);
	public void visitBoardgame(BoardGame boardgame, Object passAlongArgument);
}
