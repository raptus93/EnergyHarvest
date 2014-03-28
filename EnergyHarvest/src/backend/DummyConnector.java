package backend;

/**
 * User: scarlet - Sergej Schefer
 * Date: 24.03.14
 * Time: 18:42
 */
public class DummyConnector {

    public static void main(String[] args) {

        /* login */
        System.out.println(Server.getInstance().login("sergej@admin.de", "123456"));

        /* clancreation  */


        /*
            1. After Clan creation -> Logout -> Log back in
            2. Refresh Users Clan
        */

        System.out.println("Clan creation " + Server.getInstance().createClan("kaffee12345"));

        /* questions from server */
        QuestionCatalog fragen = Server.getInstance().getRandomQuestions(10);
        for(Question q : fragen.getQuestionList()){
            System.out.println(q.text);
        }

        /* check answers */
        System.out.println("ANSWER IS : " + Server.getInstance().checkAnswer(1, Question.Answer.A));


        /* try to leave clan ! */
        System.out.println(Server.getInstance().leaveClan());


        /* logout */
        Server.getInstance().logout();

        /* register new user */
        System.out.println(Server.getInstance().register("hans", "email", "password"));
    }
}
