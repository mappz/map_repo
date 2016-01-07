package pl.edu.wat.map;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import pl.edu.wat.map.activities.MainActivity;

/**
 * Created by marcel on 2016-01-07.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mainActivity;
	private Solo solo;
	public MainActivityTest() {
		super(MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		mainActivity = getActivity();
		solo = new Solo(getInstrumentation());
	}

	public void testActivityCreated() throws Exception {
		assertNotNull(mainActivity);
	}

	public void testGroups() throws Exception {

	}
}
