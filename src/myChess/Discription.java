package myChess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Discription {
	public static String version = "0.9.0";
	public static String discription = "myChess v." + version
			+ "\n������ 0.9.0:" + "\n\t- ��������� ������� ����"
			+ "\n������ 0.8.5:" + "\n\t- ��������� ��������� ��������� �����"
			+ "\n������ 0.8.4:" + "\n\t- ��������� ������������ ������ �����"
			+ "\n������ 0.8.3:" + "\n\t- ������������ ������� �����"
			+ "\n\t- ���������� ����������� �����" + "\n������ 0.8.0:"
			+ "\n\t- ��������� ������� ����" + "\n\t- ��������� ����� �����"
			+ "\n\t- ��������� ����������� �����" + "\n\t- ��������� ���������"
			+ "\n\t- ���������� ����������� ������ ������" + "\n������ 0.7.5:"
			+ "\n\t- �������" + "\n\t- ��������� �� �������";

	public static void write(String pathToFile) throws IOException {
		@SuppressWarnings("resource")
		FileWriter fileWriter = new FileWriter(new File(pathToFile));
		fileWriter.append(discription);
		fileWriter.flush();
	}
}