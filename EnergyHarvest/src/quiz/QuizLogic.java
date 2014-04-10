package quiz;

import backend.Question;
import backend.Question.Answer;
import backend.AndroidServerInterface;
import backend.QuestionCatalog;

public class QuizLogic {
	//10 Fragen gleichzeitig holen
	//Antworten direkt nach Antwort auf Richtigkeit prüfen
	//LinkedList<Question> questions;
	private int currentQuestion = 0;
	private int correctAnswers = 0;
	private QuestionCatalog questions;
	
	private QuizGUI gui;
	
	public void getQuestions(){
		//Fragen aus der Fragenliste holen
		////////////////////////////////////////////////////////////////////////////////////
		
		
		//Hier die Schnittstelle zum Server geändert.


		////////////////////////////////////////////////////////////////////////////////////
		questions = AndroidServerInterface.getRandomQuestions(10);
 
		currentQuestion++;
	}
	

	
	public Question nextQuestion(){
		currentQuestion++;
		return questions.popQuestion();
	}



	private void sendNumberOfCorrectAnswers(){
		//Serveraufruf um die Anzahl der richtigen Antworten (correctAnswers) dort zu speichern
		
	}
	
	/**
	 * @param chosenAnswer 1 is A, 2 is B, 3 is C, 4 is D
	 */
	public void checkAnswer(Answer chosenAnswer, QuizGUI gui) {
		boolean isCorrect;
		//Blockade der Buttons
		gui.blockButtons();
		
		//Auswertung der Antwort
		////////////////////////////////////////////////////////////////////////////////////
		
		
		//Hier die Schnittstelle zum Server geändert.


		////////////////////////////////////////////////////////////////////////////////////
		if(AndroidServerInterface.checkAnswer(questions.get(currentQuestion).id, chosenAnswer)){
			correctAnswers++;
			gui.highlight(true, chosenAnswer);
		}
		else{
			gui.highlight(false, chosenAnswer);
		}
		
		//Falls nicht die letzte, nächste Frage anzeigen
		if(currentQuestion<10){
			gui.showNextQuestion();
		}
		else{
			//beenden des Quizmodus
		}
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
