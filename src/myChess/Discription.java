package myChess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Discription {
	public static String version = "0.9.0";
	public static String discription = "myChess v." + version
			+ "\nВерсия 0.9.0:" + "\n\t- добавлено условие пада"
			+ "\nВерсия 0.8.5:" + "\n\t- добавлена подсветка возможных путей"
			+ "\nВерсия 0.8.4:" + "\n\t- добавлено перечисление цветов фигур"
			+ "\nВерсия 0.8.3:" + "\n\t- переработана история ходов"
			+ "\n\t- доработано отображение доски" + "\nВерсия 0.8.0:"
			+ "\n\t- добавлено условие мата" + "\n\t- добавлены стили доски"
			+ "\n\t- добавлено превращение пешки" + "\n\t- добавлена рокировка"
			+ "\n\t- доработана архитектура модели шахмат" + "\nВерсия 0.7.5:"
			+ "\n\t- история" + "\n\t- навигация по истории";

	public static void write(String pathToFile) throws IOException {
		@SuppressWarnings("resource")
		FileWriter fileWriter = new FileWriter(new File(pathToFile));
		fileWriter.append(discription);
		fileWriter.flush();
	}
}