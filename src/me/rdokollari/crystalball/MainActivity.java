package me.rdokollari.crystalball;

import me.rdokollari.crystalball.ShakeDetector.OnShakeListener;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizartdokollari.crystal.ball.R;

public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getSimpleName();// MainActivity.class.getName();
	// Declare our View variables
	private TextView mTextViewAnswer;
	private ImageView mCrystallBallImage;
	private CrystalBall mCrystalBall;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCrystalBall = new CrystalBall();
		// Assign the view from the layout file
		mTextViewAnswer = (TextView) findViewById(R.id.textViewAnswer);
		mCrystallBallImage = (ImageView) findViewById(R.id.imageViewBackground);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		handleNewAnwer();
		mShakeDetector = new ShakeDetector(new OnShakeListener() {

			@Override
			public void onShake() {
				handleNewAnwer();
			}
		});

		// Toast.makeText(this, "Yay! This is freaking awesome!",
		// Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);

	}

	@Override
	public void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

	private void animateCrystalBall() {
		mCrystallBallImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystallBallImage
				.getDrawable();

		if (ballAnimation.isRunning()) {
			ballAnimation.stop();
		}
		ballAnimation.start();
	}

	private void animateAnswer() {
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
		fadeInAnimation.setDuration(1500);
		fadeInAnimation.setFillAfter(true);

		mTextViewAnswer.setAnimation(fadeInAnimation);

		/*
		 * Thread newThread = new Thread();
		 * 
		 * while (!mTextViewAnswer.getAnimation().hasEnded()) { try {
		 * newThread.sleep(500); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } AlphaAnimation
		 * fadeOutAnimation = new AlphaAnimation(1, 0);
		 * fadeOutAnimation.setDuration(2000);
		 * fadeOutAnimation.setFillAfter(false);
		 * mTextViewAnswer.setAnimation(fadeInAnimation);
		 */

	}

	private void playSound() {
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void handleNewAnwer() {
		// update mTextViewAnswer with our dynamic answer
		mTextViewAnswer.setText(mCrystalBall.getRandomAnswer());
		animateCrystalBall();
		animateAnswer();
		playSound();
	}

}
