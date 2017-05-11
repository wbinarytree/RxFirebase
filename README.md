RxFirebase
==========

[![Maven Central](https://img.shields.io/badge/jcenter-v0.3.3-green.svg)](https://bintray.com/phoenixwyd/maven/RxFirebase)


A [RxJava 2.x ](https://github.com/ReactiveX/RxJava) wrap for Google's Firebase. 

usage
-----

#### Auth 

```
RxAuth.signInAnonymously(auth)
      .subscribe(authResult -> {/*Your authResult*/}); 
```

You can regisiter your listener and get your currentUser:

```
RxAuth.authState(auth)
      .subscribe(user -> {/*Your current FirebaseUser*/});
```

#### Database 


```
RxDatabase.query(dbRef.limitToFirst(10))
          .subscribe(dataSnapshot -> {/*Your DataSnapshot*/});
```

Or you can use build in Mapper

```
RxDatabase.query(dbRef.limitToFirst(10),YourClass.class)
          .subscribe(yourClass -> {/*Your data of YourClass*/});
                  
```

More usage see [Javadoc](https://wbinarytree.github.io/RxFirebase/javadoc/index)

download
--------

add jcenter in your build.gradle :

```
    repositories {
        jcenter()
    }
```

  Maven :
```
<dependency>
  <groupId>com.github.wbinarytree</groupId>
  <artifactId>rxfirebase</artifactId>
  <version>0.3.3</version>
  <type>pom</type>
</dependency>
```

  Gradle:
```
compile 'com.github.wbinarytree:rxfirebase:0.3.3'
```

License
-------
```
Copyright 2017 WBinaryTree

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



