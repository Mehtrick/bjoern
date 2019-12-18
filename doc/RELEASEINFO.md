# How to release to maven central


* create gradle.properties
```groovy
signing.keyId=id
signing.password=password
signing.secretKeyRingFile=
ossrhUsername=
ossrhPassword=
publishing=true
```

* to create signing key
```bash
gpg --gen-key
gpg --list-keys --keyid-format short  <--- the 8char long id is needed in gradle.properties. pub rsa3072/<id>
gpg --export-secret-keys -o secring.gpg   <--- path to this file is needed in gradle.properties
```

* publish gpg key
```bash
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys s 1D28A718

```

* upload to maven

```groovy
./gradlew uploadArchives
```
