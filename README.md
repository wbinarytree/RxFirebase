RxFirebase
==========

[![Maven Central](https://img.shields.io/badge/jcenter-v0.2.0-green.svg)](https://bintray.com/phoenixwyd/maven/RxFirebase)


A wrap for Firebase Realtime Database

usage
-----
```
RxDatabase.query(dbRef.limitToFirst(10))
          .subscribe(dataSnapshot -> {/*Your Subscriber*/});
```

Or you can use build in Mapper

```
RxDatabase.query(dbRef.limitToFirst(10),YourClass.class)
          .subscribe(yourClass -> {/**/});
                  
```

download
--------

  Maven :
```
<dependency>
  <groupId>com.github.wbinarytree</groupId>
  <artifactId>rxfirebase</artifactId>
  <version>0.2.0</version>
  <type>pom</type>
</dependency>
```

  Gradle:
```
compile 'com.github.wbinarytree:rxfirebase:0.2.0'
```




