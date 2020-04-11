rm -rf target/dependency
mkdir target/dependency
(cd target/dependency; jar -xf ../*.jar)
docker build