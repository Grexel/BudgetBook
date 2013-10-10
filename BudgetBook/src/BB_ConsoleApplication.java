import java.util.Scanner;


public class BB_ConsoleApplication {

	private Scanner scanner;
	
	public static void main(String[] args)
	{
		BB_ConsoleApplication mainProgram = new BB_ConsoleApplication();
	}
	
	public BB_ConsoleApplication()
	{
		scanner = new Scanner(System.in);
		System.out.println("Welcome to Budget Book");
		System.out.println(	scanner.nextLine());
	
	}
}
