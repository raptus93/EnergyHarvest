package Quiz;

import backend.Question;
import backend.QuestionCatalog;
import backend.Server;

public class QuizLogic {
	//10 Fragen gleichzeitig holen
	//Antworten direkt nach Antwort auf Richtigkeit prüfen
	//LinkedList<Question> questions;
	private int currentQuestion = 0;
	private int correctAnswers = 0;
	private QuestionCatalog questions;
	
	
	private void getQuestions(){
		//Fragen aus der Fragenliste holen
		questions = Server.getInstance().getRandomQuestions(10);
        
		/*for(Question q : questions.getQuestionList()){
            System.out.println(q.text);
        }
		*/
		//questions.get(currentQuestion);
		currentQuestion++;
	}
	/**
	 * @param answer 1 is A, 2 is B, 3 is C, 4 is D
	 */
	public void saveAnswer(int answer){
		boolean isCorrect = true; //default = true (?)
		//Überprüfung auf Richtigkeit von answer beim Server um isCorrect zu ändern
		
		//Speichern der Anzahl der richtigen Antworten
		if (isCorrect)
		{
			correctAnswers++;
		}
		//Anzeige von grünem oder rotem Feld auf gegebener Antwort blinkend für 2-3 Sekunden
		//QuizGUI.showCorrectness(); -> wegen 3 Schicht Architektur hier trennen
		//Aufruf von nextQuestion()
	}
	
	private void nextQuestion(){
		//Anzeige der nächsten Frage durch Serveranfrage
		
	}
	
	private void sendNumberOfCorrectAnswers(){
		//Serveraufruf um die Anzahl der richtigen Antworten (correctAnswers) dort zu speichern
		
	}
	public void ping() {
		//hier die Antwort mit getAnswer() aus der GUI holen
	}
	
	
	
	/*
	private void createPlayerList(){
		//Liste anzeigen mit Checkboxen zur Auswahl der Mitspieler
		//addSpec() aufrufen
	}
	
	private void addSpec(){
		//Liste mit Specs
	}
	*/
}
