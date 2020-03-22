## webfiles-framework-java

[![Build status][Travis Develop image]][Travis Develop link]

[Travis Develop image]: https://img.shields.io/travis/sebastianmonzel/webfiles-framework-java/master.svg?style=flat-square
[Travis Develop link]: https://travis-ci.org/sebastianmonzel/webfiles-framework-java


### Remote datastore: First Steps
 - create a remote datastore via [https://github.com/sebastianmonzel/webfiles-framework-php/]
 - add maven dependency to your `pom.xml`
 - make sure all webfile definitions used in the the datastore are locally available
 - connect to your datastore
 - save and read data from your datastore
 
### Remote datastore: Basic Usecases
 
#### Read from RemoteDatastore
```java
MRemoteDatastore remoteDatastore = new MRemoteDatastore("http://www.sebastianmonzel.de/datastore/");
MWebfileStream webfilesAsStream = remoteDatastore.getWebfilesAsStream();

List<MWebfile> webfiles = webfilesAsStream.getWebfiles();

MSampleWebfile sampleWebfile = (MSampleWebfile) webfiles.get(0);
assertThat(sampleWebfile.getFirstname(), is("Sebastian"));
assertThat(sampleWebfile.getLastname(), is("Monzel"));
```
