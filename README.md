# SegmentedLoader

##Description

Small library allowing you to create a custom loader based on segments.

##Demo:
![SegmentedLoader](screenshots/loader.gif)


##Integration

**SegmentedLoader** (min API 14):  [ ![Download](https://api.bintray.com/packages/dbottillo/maven/segmented-library/images/download.svg) ](https://bintray.com/dbottillo/maven/segmented-library/_latestVersion)

All you have to do is add it on your gradle build:

```groovy
dependencies {
    compile 'com.github.dbottillo.segmentedloader:library:0.9.0'
}
```

##Usage

Use directly SegmentedLoader:

```xml
<com.danielebottillo.segmentedloader.SegmentedLoader
	android:id="@+id/loader"
	android:layout_width="100dp"
	android:layout_height="100dp"
	myapp:speed="5000"
	myapp:start_color="#45ef45"
	myapp:middle_color="#DF321D"
	myapp:end_color="#FFE714"
	myapp:reversed="true"
	myapp:fill_on_start="true"
/>
```

Then you need to specify the segments of your loader. <br/>
Every segments is composed by 4 coordinates: the starting left point, the starting right point and the end left/right points. 
To specify a segment you have two choice:

- integer values against a grid: so imagine you have a grid of 10x10 you can specify integer coordinates of the grid (by default grid size is 10)

```java
SegmentedLoader  loader = (SegmentedLoader) findViewById(R.id.loader);
loader.addSegment(new Segment().setStartLeftPoint(0, 0).setStartRightPoint(2, 0).setEndRightPoint(2, 10).setEndLeftPoint(0, 10));
```

you can also specify the grid size in the constructor:

```java
loader.addSegment(new Segment(10).setStartLeftPoint(0, 0).setStartRightPoint(2, 0).setEndRightPoint(2, 10).setEndLeftPoint(0, 10));
```

or use an handy constructor with all the information at the same time:

```java
loader.addSegment(new Segment(10, 2, 10, 2, 8, 10, 8, 10, 10));
```

- you can also specify float numbers using a grid of 1.0fx1.0f size, in this way you have a complete control of segment position:

```java
loader.addSegment(new Segment().setStartLeftPoint(.5249f, .419f).setStartRightPoint(.4798f, .5239f).setEndRightPoint(.0f, .5239f).setEndLeftPoint(.25f, .419f));
```

You can also specify:

- start/middle/end color
- if the animation is reversed
- the speed


##License

```
Copyright 2015 Daniele Bottillo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```