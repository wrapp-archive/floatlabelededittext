Float Labeled EditText
==============

Simple implementation of a Float Labeled EditText: An Android ViewGroup which uses a child EditText and puts the hint on top of the EditText when it is populated with text.

iOS implementation by [@jverdi](http://www.twitter.com/jverdi): [JVFloatLabeledTextField](https://github.com/jverdi/JVFloatLabeledTextField) 

Credits for the concept to Matt D. Smith ([@mds](http://www.twitter.com/mds)).

![Android Version](http://i.imgur.com/ucRd1jm.gif)

http://dribbble.com/shots/1254439--GIF-Mobile-Form-Interaction?list=users

Notice
==============

The usage has significantly changed in version 0.0.5 inspired by [chrisbanes](https://github.com/chrisbanes) [implementation](https://gist.github.com/chrisbanes/11247418) which makes styling easier. If you want to use the old style, use version 0.0.4.


Usage
=====

Add the library project or grab to build.gradle:
```groovy
compile 'com.wrapp.floatlabelededittext:library:0.0.5'
```
or plain maven:
```maven
<dependency>
  <groupId>com.wrapp.floatlabelededittext</groupId>
  <artifactId>library</artifactId>
  <version>0.0.5</version>
  <type>aar</type>
</dependency>
```
and then insert the view in XML:

```xml
    <com.wrapp.floatlabelededittext.FloatLabeledEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="This is the hint" />
    </com.wrapp.floatlabelededittext.FloatLabeledEditText>

    <!-- add some padding -->
    <com.wrapp.floatlabelededittext.FloatLabeledEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        float:fletPadding="10dp">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Username" />
    </com.wrapp.floatlabelededittext.FloatLabeledEditText>

    <!-- password input -->
    <com.wrapp.floatlabelededittext.FloatLabeledEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        float:fletPaddingBottom="10dp">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Password"
            android:inputType="textPassword" />
    </com.wrapp.floatlabelededittext.FloatLabeledEditText>

    <!-- change color of hint text-->
    <com.wrapp.floatlabelededittext.FloatLabeledEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        float:fletPaddingBottom="10dp"
        float:fletTextAppearance="@style/floatlabelededittext">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Styled Hint"
            android:inputType="textPassword" />
    </com.wrapp.floatlabelededittext.FloatLabeledEditText>
```
Developed By
============

* Henrik Sandström [@heinrisch](https://twitter.com/Heinrisch)

Styled By
=========

* Marcus Gellemark [Dribbble](http://dribbble.com/Gellermark)

Additional Credit
=================

* Chris Banes [chrisbanes](https://github.com/chrisbanes)
* Quentin DOMMERC [dommerq](https://github.com/dommerq)
* Aaron Rietschlin [@AaronRietschlin](https://twitter.com/AaronRietschlin)



License
=======
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
