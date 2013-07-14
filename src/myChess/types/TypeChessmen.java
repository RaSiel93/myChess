package myChess.types;

public enum TypeChessmen {
	Pawn, Rook, Horse, Officer, Queen, King;

	public static TypeChessmen parseTypeChessmen(String value) {
		TypeChessmen typeChessmen = null;
		switch (value) {
		case "Pawn":
			typeChessmen = TypeChessmen.Pawn;
			break;
		case "Rook":
			typeChessmen = TypeChessmen.Rook;
			break;
		case "Horse":
			typeChessmen = TypeChessmen.Horse;
			break;
		case "Officer":
			typeChessmen = TypeChessmen.Officer;
			break;
		case "Queen":
			typeChessmen = TypeChessmen.Queen;
			break;
		case "King":
			typeChessmen = TypeChessmen.King;
			break;
		}
		return typeChessmen;
	}
}