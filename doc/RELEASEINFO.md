# How to release to maven central


* create gradle.properties
```groovy
signing.keyId=id
signing.password=password
signing.secretKeyRingFile=path to secring file

ossrhUsername=
ossrhPassword=

```


* to create signing key
```bash
gpg --gen-key
gpg --list-keys --keyid-format short  <--- the 8char long id is needed in gradle.properties
gpg --export-secret-keys -o secring.gpg   <--- path to this file is needed in gradle.properties
```


* upload to maven

```groovy
./gradlew uploadArchives
```
