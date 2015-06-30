package danielebottillo.com.segmentedloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.danielebottillo.segmentedloader.Segment;
import com.danielebottillo.segmentedloader.SegmentedLoader;

import transferwise.com.testflaglibrary.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SegmentedLoader loader = (SegmentedLoader) findViewById(R.id.loader);
        loader.addSegment(new Segment().setTopLeftPoint(0, 0).setTopRightPoint(2, 0).setBottomRightPoint(2, 10).setBottomLeftPoint(0, 10));
        loader.addSegment(new Segment().setTopLeftPoint(2, 10).setTopRightPoint(2, 8).setBottomRightPoint(10, 8).setBottomLeftPoint(10, 10));
        loader.addSegment(new Segment().setTopLeftPoint(8, 8).setTopRightPoint(10, 8).setBottomRightPoint(10, 0).setBottomLeftPoint(8, 0));
        // loader.addSegment(new Segment().setTopLeftPoint(8, 0).setTopRightPoint(8, 2).setBottomRightPoint(2, 2).setBottomLeftPoint(2, 0));
        loader.setReverted().setSegmentSpeed(333).startAnimation();

        SegmentedLoader segmentedLoader = (SegmentedLoader) findViewById(R.id.tw_loader);
        segmentedLoader.addSegment(new Segment().setTopLeftPoint(.5249f, .419f).setTopRightPoint(.4798f, .5239f).setBottomRightPoint(.0f, .5239f).setBottomLeftPoint(.25f, .419f));
        segmentedLoader.addSegment(new Segment().setTopLeftPoint(.2602f, .419f).setTopRightPoint(.0f, .5239f).setBottomRightPoint(.2775f, .25f).setBottomLeftPoint(.4204f, .2615f));
        segmentedLoader.addSegment(new Segment().setTopLeftPoint(.4204f, .2641f).setTopRightPoint(.2775f, .2621f).setBottomRightPoint(.1192f, .0f).setBottomLeftPoint(.3268f, .0949f));
        segmentedLoader.addSegment(new Segment().setTopLeftPoint(.3268f, .1049f).setTopRightPoint(.1192f, .0f).setBottomRightPoint(.94f, .0f).setBottomLeftPoint(.7734f, .1049f));
        segmentedLoader.addSegment(new Segment().setTopLeftPoint(.7634f, .1049f).setTopRightPoint(.94f, .0f).setBottomRightPoint(.5115f, 1.0f).setBottomLeftPoint(.3801f, 1.0f));
        segmentedLoader.setSegmentSpeed(600).startAnimation();

        SegmentedLoader segmentedLoaderInverted = (SegmentedLoader) findViewById(R.id.tw_loader_inverted);
        segmentedLoaderInverted.addSegment(new Segment().setTopLeftPoint(.5249f, .419f).setTopRightPoint(.4798f, .5239f).setBottomRightPoint(.0f, .5239f).setBottomLeftPoint(.25f, .419f));
        segmentedLoaderInverted.addSegment(new Segment().setTopLeftPoint(.2602f, .419f).setTopRightPoint(.0f, .5239f).setBottomRightPoint(.2775f, .25f).setBottomLeftPoint(.4204f, .2615f));
        segmentedLoaderInverted.addSegment(new Segment().setTopLeftPoint(.4204f, .2641f).setTopRightPoint(.2775f, .2621f).setBottomRightPoint(.1192f, .0f).setBottomLeftPoint(.3268f, .0949f));
        segmentedLoaderInverted.addSegment(new Segment().setTopLeftPoint(.3268f, .1049f).setTopRightPoint(.1192f, .0f).setBottomRightPoint(.94f, .0f).setBottomLeftPoint(.7734f, .1049f));
        segmentedLoaderInverted.addSegment(new Segment().setTopLeftPoint(.7634f, .1049f).setTopRightPoint(.94f, .0f).setBottomRightPoint(.5115f, 1.0f).setBottomLeftPoint(.3801f, 1.0f));
        segmentedLoaderInverted.setSpeed(3000).setReverted().startAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
