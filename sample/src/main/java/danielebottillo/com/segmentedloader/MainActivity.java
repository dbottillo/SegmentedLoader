package danielebottillo.com.segmentedloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.danielebottillo.segmentedloader.Segment;
import com.danielebottillo.segmentedloader.SegmentedLoader;

import transferwise.com.testflaglibrary.R;

public class MainActivity extends Activity {

    SegmentedLoader loader, loaderInverted, loaderThreeColors, loaderThreeColorsInverted;
    boolean animationRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.toogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationRunning) {
                    loader.stopAnimation();
                    loaderInverted.stopAnimation();
                    loaderThreeColors.stopAnimation();
                    loaderThreeColorsInverted.stopAnimation();
                } else {
                    loader.startAnimation();
                    loaderInverted.startAnimation();
                    loaderThreeColors.startAnimation();
                    loaderThreeColorsInverted.startAnimation();
                }
                animationRunning = !animationRunning;
            }
        });

        loader = (SegmentedLoader) findViewById(R.id.loader);
        loader.addSegment(new Segment().setStartLeftPoint(0, 0).setStartRightPoint(2, 0).setEndRightPoint(2, 10).setEndLeftPoint(0, 10));
        loader.addSegment(new Segment(10, 2, 10, 2, 8, 10, 8, 10, 10));
        loader.addSegment(new Segment(10, 8, 8, 10, 8, 10, 0, 8, 0));
        loader.setSpeed(1500);

        loaderInverted = (SegmentedLoader) findViewById(R.id.loader_inverted);
        loaderInverted.addSegment(new Segment().setStartLeftPoint(0, 0).setStartRightPoint(2, 0).setEndRightPoint(2, 10).setEndLeftPoint(0, 10));
        loaderInverted.addSegment(new Segment(10, 2, 10, 2, 8, 10, 8, 10, 10));
        loaderInverted.addSegment(new Segment(10, 8, 8, 10, 8, 10, 0, 8, 0));
        loaderInverted.setSpeed(1500).setReversed();

        loaderThreeColors = (SegmentedLoader) findViewById(R.id.loader_three_colors);
        loaderThreeColors.addSegment(new Segment(10, 0, 10, 2, 10, 2, 0, 0, 0));
        loaderThreeColors.addSegment(new Segment(10, 2, 0, 2, 3, 5, 6, 5, 3));
        loaderThreeColors.addSegment(new Segment(10, 5, 3, 5, 6, 8, 3, 8, 0));
        loaderThreeColors.addSegment(new Segment(10, 8, 0, 10, 0, 10, 10, 8, 10));
        loaderThreeColors.setSpeed(3000);

        loaderThreeColorsInverted = (SegmentedLoader) findViewById(R.id.loader_three_colors_inverted);
        loaderThreeColorsInverted.addSegment(new Segment().setStartLeftPoint(.5249f, .419f).setStartRightPoint(.4798f, .5239f).setEndRightPoint(.0f, .5239f).setEndLeftPoint(.25f, .419f));
        loaderThreeColorsInverted.addSegment(new Segment().setStartLeftPoint(.2602f, .419f).setStartRightPoint(.0f, .5239f).setEndRightPoint(.2775f, .25f).setEndLeftPoint(.4204f, .2615f));
        loaderThreeColorsInverted.addSegment(new Segment().setStartLeftPoint(.4204f, .2641f).setStartRightPoint(.2775f, .2621f).setEndRightPoint(.1192f, .0f).setEndLeftPoint(.3268f, .0949f));
        loaderThreeColorsInverted.addSegment(new Segment().setStartLeftPoint(.3268f, .1049f).setStartRightPoint(.1192f, .0f).setEndRightPoint(.94f, .0f).setEndLeftPoint(.7734f, .1049f));
        loaderThreeColorsInverted.addSegment(new Segment().setStartLeftPoint(.7634f, .1049f).setStartRightPoint(.94f, .0f).setEndRightPoint(.5115f, 1.0f).setEndLeftPoint(.3801f, 1.0f));
    }

}
