package pl.edu.wat.map;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import pl.edu.wat.map.activities.LoginActivity;
import pl.edu.wat.map.activities.RegisterActivity;

/**
 * Created by marcel on 2016-01-07.
 */
public class RegisterButtonTest extends ActivityInstrumentationTestCase2<LoginActivity> {
	private LoginActivity loginActivity;
	private Solo solo;
	public RegisterButtonTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

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


    public void testRegisterButton() throws Exception{

        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solo.clickOnButton(2);
                solo.assertCurrentActivity("wrong activity", RegisterActivity.class);
                solo.goBack();
            }
        });
    }
}
