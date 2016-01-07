package pl.edu.wat.map;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import pl.edu.wat.map.activities.LoginActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity loginActivity;
    private Solo solo;
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
      /*  try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear pl.edu.wat.map");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        loginActivity = getActivity();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginActivity.logout();
            }
        });
        solo = new Solo(getInstrumentation());
    }

    public void testActivityCreated() throws Exception {
      assertNotNull(loginActivity);
    }

    public void testLogin() throws Exception{
        setUp();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solo.clickOnButton(0);
                solo.waitForDialogToOpen();
                assertTrue(solo.waitForText("Error"));
                solo.goBack();
            }
        });
    }



  /*  public void testLoginAnonimously() throws Exception {
        setUp();
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginActivity.loginAnonymously();
                assertTrue(solo.waitForText("Zalogowano"));
                loginActivity.logout();
            }
        });
    }*/

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}