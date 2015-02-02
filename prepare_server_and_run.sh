echo "==> GIT"
sudo apt-get -y install git

echo "==> JAVA"
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get -y install oracle-java8-installer
sudo apt-get -y install oracle-java8-set-default

echo "==> MAVEN"
sudo apt-get -y install MAVEN

echo "====> JAVA-VERSION"
java -version

echo "==> Reload .bashrc"
source .bashrc

echo "==> Clone git repo"
git clone https://FlorianLoch@bitbucket.org/FlorianLoch/sa_viergewinnt_core.git

echo "==> Build with maven"
cd sa_viergewinnt_core
mvn compile
cd target/classes

echo "==> Run"
java de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.AlphaBeta
