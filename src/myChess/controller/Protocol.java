package myChess.controller;

import myChess.model.Chess;

public class Protocol {
	private Controller controller;

	public Protocol(Controller controller) {
		this.controller = controller;
	}

	public Object execute(Object query) {
		 if(query instanceof Chess){
			return controller.loadChess(query);
		}
		// } else if(query instanceof QueryFind){
		// return controller.getQueryFind((QueryFind)query);
		// } else if(query instanceof QueryAdd){
		// controller.addInTable(((QueryAdd)query).getId(),
		// ((QueryAdd)query).getArrayData());
		// } else if(query instanceof QueryDelete){
		// controller.deleteTable(((QueryDelete)query).getId(),
		// ((QueryDelete)query).getListSelected());
		// } else if(query instanceof QueryClose){
		// controller.close(((QueryClose)query).getId());
		// } else if(query instanceof QueryCountRows){
		// return new
		// ResultCountRows(controller.getCountRows(((QueryCountRows)query).getId()));
		// } else if(query instanceof QueryFile){
		// return new ResultFile(controller.getFileDirectory());
		// }
		return null;
	}
}
