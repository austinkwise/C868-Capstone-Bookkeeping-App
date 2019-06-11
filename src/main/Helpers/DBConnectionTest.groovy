package main.Helpers

//this goes after DBConnectionTest
//extends GroovyTestCase
class DBConnectionTest {
    private DBConnection myDb;

    boolean testLogin(String user, String pass){
        boolean result = DBConnection.signInUser(user, pass);
        return result;
    }

     void testValidEmail() {
        String user = "test@test.com";
        String password = "test";

         boolean actual;
         boolean expected = true;

         actual = testLogin(user, password);
         assertEquals(expected, actual);
    }

    void isValidUsername(){
        String user = "test";
        String password = "test";

        boolean actual;
        boolean expected = true;

        actual = testLogin(user, password);
        assertEquals(expected, actual);
    }

    void isInvalidEmail(){
        String user = "t@t.com";
        String password = "test";

        boolean actual;
        boolean expected = false;

        actual = testLogin(user, password);
        assertEquals(expected, actual);
    }

    void isInvalidUsername(){
        String user = "testtt";
        String password = "test";

        boolean actual;
        boolean expected = false;

        actual = testLogin(user, password);
        assertEquals(expected, actual);
    }

    void isInvalidUsernamePassword(){
        String user = "test";
        String password = "tasty";

        boolean actual;
        boolean expected = false;

        actual = testLogin(user, password);
        assertEquals(expected, actual);
    }

    void isInvalidEmailPassword(){
        String user = "test@test.com";
        String password = "tasty";

        boolean actual;
        boolean expected = false;

        actual = testLogin(user, password);
        assertEquals(expected, actual);
    }

}
