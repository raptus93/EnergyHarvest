package backend;

/**
 * User: scarlet - Sergej Schefer
 * Date: 24.03.14
 * Time: 18:42
 */
public class DummyConnector {

    public static void main(String[] args) {

        /* login */
        System.out.println(Server.getInstance().login("sergej@admin.de","123456"));

        /* clancreation  */
        System.out.println("Clan creation " + Server.getInstance().createClan("bart"));

        /* questions from server */
        QuestionCatalog fragen = Server.getInstance().getRandomQuestions(10);
        for(Question q : fragen.getQuestionList()){
            System.out.println(q.text);
        }

        /* logout */
        Server.getInstance().logout();

        /* login */
        System.out.println(Server.getInstance().login("sergej@admin.de","123456"));

    }
}
