package myChess.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import myChess.model.chessmens.Chessmen;
import myChess.model.chessmens.Chessmens;
import myChess.model.history.*;
import myChess.model.status.Status;
import myChess.model.Chess;
import myChess.shell.FrameMain;
import myChess.types.Cell;
import myChess.types.ColorChessmen;

public class Controller {
	private FrameMain frameMain;
	private Chess chess;

	private Server server;
	private Client client;

	public Controller(Chess chess) {
		this.chess = chess;
		this.chess.start();
		server = new Server(this);
		client = new Client();
	}

	public void setFrame(FrameMain frameMain) {
		this.frameMain = frameMain;
		this.frameMain.setStatus(this.chess.getStatus());
	}

	public boolean isModeRead() {
		return chess.isModeRead();
	}

	public void newGame() {
		this.chess.load(new Chessmens(null), new Status(), new History());
		this.chess.start();
		this.frameMain.setStatus(this.chess.getStatus());
		updateStatus();
		frameMain.repaint();
	}

	public void action() {
		chess.action();
		if (chess.checkGameOver()) {
			this.chess.stop();
		}
		updateStatus();
		frameMain.repaint();
	}

	public Cell getCellActive() {
		return chess.getCellActive();
	}

	public void setCellActive(Cell cell) {
		chess.setCellActive(cell);
		frameMain.repaint();
	}

	public Chessmen getCellDanger() {
		return chess.getCellDanger();
	}

	public void setCellDanger(Chessmen chessmen) {
		chess.setChessmenDanger(chessmen);
		frameMain.repaint();
	}

	public Chessmen getChessmenActive() {
		return chess.getChessmenActive();
	}

	public ColorChessmen getWhoWalk() {
		return chess.whoWalk();
	}

	public Status getStatus() {
		return chess.getStatus();
	}

	public Chessmens getChessmens() {
		return chess.getChessmens();
	}

	public boolean checkFriendChessmen(Cell cell) {
		return chess.checkFriendChessmen(cell);
	}

	public boolean checkEnemyChessmen(Cell cell) {
		return chess.checkEnemyChessmen(cell);
	}

	public Chessmen getChessmen(Cell cell) {
		return chess.getChessmen(cell);
	}

	public void setChessmenDanger(Chessmen chessmen) {
		chess.setChessmenDanger(chessmen);
	}

	public void switchModeRead() {
		chess.switchModeRead();
		frameMain.repaint();
	}

	public void updateStatus() {
		frameMain.updateStatus();
	}

	public void createMultiplayer() throws IOException {
		Server.runServer();
	}

	public void connectMultiplayer() {
		client.connect("localhost");
	}

	public void disconnectMultiplayer() {
		try {
			server.stopServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// client = null;
	}

	public Object loadChess(Object query) {
		chess = (Chess) query;
		frameMain.repaint();
		updateStatus();
		return null;
	}

	public Chess getChess() {
		return this.chess;
	}

	public void loadGame() throws Exception {
		JFileChooser fileopen = new JFileChooser();
		fileopen.setCurrentDirectory(new File(".\\save"));
		if (fileopen.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			readXML(fileopen.getSelectedFile().getAbsolutePath());
			updateStatus();
		}
	}

	public void readXML(String pathToFile) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;

		try {
			parser = factory.newSAXParser();

			SAXParserXML saxParser = new SAXParserXML();
			File file = new File(pathToFile);

			parser.parse(file, saxParser);

			this.chess.load(new Chessmens(saxParser.getChessmens()),
					new Status(), new History());
		} catch (ParserConfigurationException | SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void undoHistory() {
		chess.undoHistory();
		updateStatus();
		frameMain.repaint();
	}

	public void redoHistory() {
		chess.redoHistory();
		updateStatus();
		frameMain.repaint();
	}

	public List<Cell> getAvailablePathsAtActiveChessmen() {
		return chess.getAvailablePathsAtActiveChessmen();
	}

	public Cell getDangerCell() {
		return chess.getDangerCell();
	}

	public boolean isGame() {
		return chess.isGame();
	}

	/*
	 * public void writeIntoXML(String path) throws IOException,
	 * TransformerException, ParserConfigurationException, SQLException {
	 * 
	 * List<List<String>> value = getData();
	 * 
	 * if (!path.equals("")) this.pathToFile = path;
	 * 
	 * DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	 * DocumentBuilder builder = f.newDocumentBuilder();
	 * 
	 * Document doc = (Document) builder.newDocument();
	 * 
	 * Element rootDoc = doc.createElement("table"); doc.appendChild(rootDoc);
	 * 
	 * Element header = doc.createElement("header");
	 * rootDoc.appendChild(header);
	 * 
	 * Element names = doc.createElement("names"); header.appendChild(names);
	 * for (int num = 0; num < hullTable.getHeaderNames().size(); num++) {
	 * Element name = doc.createElement("name");
	 * name.appendChild(doc.createTextNode(hullTable.getHeaderNames().get(
	 * num))); names.appendChild(name); } Element unions =
	 * doc.createElement("unions"); header.appendChild(unions); for (int num =
	 * 0; num < hullTable.getUnionCells().size(); num++) { Element union =
	 * doc.createElement("union");
	 * 
	 * union.setAttribute("first", String.valueOf((hullTable
	 * .getUnionCells().get(num).getBeginUnion()))); union.setAttribute("last",
	 * String.valueOf((hullTable .getUnionCells().get(num).getEndUnion())));
	 * union.setAttribute("union_name", String.valueOf((hullTable
	 * .getUnionCells().get(num).getColumnName()))); unions.appendChild(union);
	 * }
	 * 
	 * Element finds = doc.createElement("finds"); rootDoc.appendChild(finds);
	 * for (int num = 0; num < findArray.size(); num++) { Element find =
	 * doc.createElement("find"); find.setAttribute("label",
	 * findArray.get(num).getLabel()); find.setAttribute("type",
	 * findArray.get(num).getType());
	 * 
	 * List<Integer> columnArray = findArray.get(num).getColumns(); for (int
	 * num2 = 0; num2 < columnArray.size(); num2++) { Element column =
	 * doc.createElement("column"); column.appendChild(doc.createTextNode(String
	 * .valueOf(columnArray.get(num2)))); find.appendChild(column); }
	 * finds.appendChild(find); }
	 * 
	 * Element dataEl = doc.createElement("data"); rootDoc.appendChild(dataEl);
	 * for (int num = 0; num < value.size(); num++) { Element node =
	 * doc.createElement("node"); dataEl.appendChild(node);
	 * 
	 * for (int num2 = 0; num2 < value.get(num).size(); num2++) { Element info =
	 * doc.createElement("info"); node.appendChild(info);
	 * info.appendChild(doc.createTextNode(value.get(num).get(num2))); } }
	 * Transformer t = TransformerFactory.newInstance().newTransformer();
	 * t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(
	 * pathToFile))); }
	 */
}
