echo "==> GIT"
sudo apt-get -y install git

echo "==> SCREEN"
sudo apt-get -y install screen

echo "==> XCLIP"
sudo apt-get -y install xclip

echo "==> VIM"
sudo apt-get -y install vim

echo "==> JAVA"
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get -y install oracle-java8-installer
sudo apt-get -y install oracle-java8-set-default

echo "==> MAVEN"
sudo apt-get -y install MAVEN

echo "====> JAVA-VERSION"
java -version

echo "==> Set correct Java encoding"
sed -e "\$aexport JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8" ~/.bashrc

echo "==> Reload .bashrc"
source .bashrc

echo "==> Clone git repo"
git clone https://FlorianLoch@bitbucket.org/FlorianLoch/sa_viergewinnt_core.git

echo "==> Build with maven"
cd sa_viergewinnt_core
mvn compile

echo "==> Run"
cd target/classes
java de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.AlphaBeta num_level
