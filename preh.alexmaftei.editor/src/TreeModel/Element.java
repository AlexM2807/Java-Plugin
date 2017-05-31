package TreeModel;

import java.util.ArrayList;
import java.util.List;

public class Element extends Model {
	protected static List<Element> newBooks = buildBookList();
	protected static int cursor = 0;
	
	public Element(String shortName, String type, String fileName) {
		super(shortName, type, fileName);
	}
	
	
	
	
	public static Element newBook() {
		Element newBook = (Element)newBooks.get(cursor);
		cursor = ((cursor + 1) % newBooks.size());
		return newBook;
	}
	
	
	protected static List<Element> buildBookList() {
		newBooks = new ArrayList<Element>();
		Element[] books = new Element[] {

//			new Element("Java 2 Exam Cram", "William", "Brogden")
		};
		
		for (int i = 0; i < books.length; i++) {
			newBooks.add(books[i]);
			
		}
		return newBooks;
	}
	/*
	 * @see Model#accept(ModelVisitorI, Object)
	 */
	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		visitor.visitBook(this, passAlongArgument);
	}

}
