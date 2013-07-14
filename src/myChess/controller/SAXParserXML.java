package myChess.controller;

import java.util.ArrayList;
import java.util.List;

import myChess.model.chessmens.Chessmen;
import myChess.model.chessmens.Horse;
import myChess.model.chessmens.King;
import myChess.model.chessmens.Officer;
import myChess.model.chessmens.Pawn;
import myChess.model.chessmens.Queen;
import myChess.model.chessmens.Rook;
import myChess.types.Cell;
import myChess.types.ColorChessmen;
import myChess.types.TypeChessmen;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserXML extends DefaultHandler {

	List<Chessmen> chessmens;
	TypeChessmen typeChessmen;
	ColorChessmen colorChessmen;
	Cell cell;
	int countMove;

	String thisElement = "";

	public void startDocument() throws SAXException {

	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		thisElement = qName;
		if (thisElement.equals("chessmens")) {
			chessmens = new ArrayList<Chessmen>();
		}
		if (thisElement.equals("chessmen")) {
			typeChessmen = TypeChessmen.parseTypeChessmen(atts
					.getValue("type"));
			colorChessmen = ColorChessmen.parseColorChessmen(atts
					.getValue("color"));
			cell = new Cell(Integer.parseInt(atts.getValue("x")),
					Integer.parseInt(atts.getValue("y")));
			countMove = Integer.parseInt(atts.getValue("countMove"));
		}
	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		thisElement = qName;
		if (thisElement.equals("chessmen")) {
			switch (typeChessmen) {
			case Pawn:
				chessmens.add(new Pawn(cell, colorChessmen, countMove));
				break;
			case Rook:
				chessmens.add(new Rook(cell, colorChessmen, countMove));
				break;
			case Horse:
				chessmens.add(new Horse(cell, colorChessmen, countMove));
				break;
			case Officer:
				chessmens.add(new Officer(cell, colorChessmen, countMove));
				break;
			case Queen:
				chessmens.add(new Queen(cell, colorChessmen, countMove));
				break;
			case King:
				chessmens.add(new King(cell, colorChessmen, countMove));
				break;
			}

		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
//		if (thisElement.equals("info")) {
//			node.add(new String(ch, start, length));
//		}
//		if (thisElement.equals("column")) {
//			columns.add(Integer.parseInt(new String(ch, start, length)));
//		}
//		if (thisElement.equals("name")) {
//			headerNames.add(new String(ch, start, length));
//		}
	}

	public void endDocument() {
		//hullTable = new HullTable(headerNames, unions);
	}

	public List<Chessmen> getChessmens() {
		return chessmens;
	}
}